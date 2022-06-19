package com.example.mobileclient

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.models.Carrera
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.dialog_carrera.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class CarrerasFragment : Fragment() {

    lateinit var carrerasList: ArrayList<Carrera>

    lateinit var lista: RecyclerView
    lateinit var adaptador: AdaptadorCarreras
    lateinit var carrera: Carrera
    var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carreras, container, false)

        val searchIcon = view.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)

        val textView = view.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)

        lista = view.findViewById(R.id.item_list)
        lista.layoutManager = LinearLayoutManager(lista.context)
        lista.setHasFixedSize(true)

        view.findViewById<SearchView>(R.id.input_search)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adaptador.filter.filter(newText)
                    return false
                }
            })

        getListOfItems()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition

                Collections.swap(carrerasList, fromPosition, toPosition)

                lista.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    val index = getIndex(position)

                    handleDelete()

                    lista.adapter?.notifyItemRemoved(position)

                    Snackbar.make(
                        lista,
                        "${carrera.codigo}: ${carrera.nombre} se eliminaría...",
                        Snackbar.LENGTH_LONG
                    ).setAction("Undo") {
                        val requestBody = JSONObject()
                        requestBody.put("codigo", carrera.codigo)
                        requestBody.put("nombre", carrera.nombre)
                        requestBody.put("titulo", carrera.titulo)
                        handleInsert(requestBody)
                    }.show()
                    adaptador = AdaptadorCarreras(carrerasList)
                    lista.adapter = adaptador

                } else {
                    val index = getIndex(position)

                    carrerasList.removeAt(index)
                    lista.adapter?.notifyItemRemoved(position)

                    openDialog(view, "Actualizar Carrera", carrera)

                    carrerasList.add(position, carrera)

                    lista.adapter?.notifyItemInserted(position)

                    adaptador = AdaptadorCarreras(carrerasList)
                    lista.adapter = adaptador
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    view.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(view.context, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(lista)

        val add: FloatingActionButton = view.findViewById(R.id.add)
        add.setOnClickListener {
            openDialog(view, "Añadir Carrera")
        }

        return view
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/gestion-academica/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getListOfItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(com.example.services.Carreras::class.java).getCarreras().execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    carrerasList = response as ArrayList<Carrera>

                    adaptador = AdaptadorCarreras(carrerasList)
                    lista.adapter = adaptador
                } else
                    showToast("Not Data Found")
            }
        }
    }

    private fun handleInsert(requestBody: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(com.example.services.Carreras::class.java).insertCarrera(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (!call.isSuccessful)
                    showToast("Error deleting information!")
                else
                    getListOfItems()
            }
        }
    }

    private fun handleUpdate(requestBody: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(com.example.services.Carreras::class.java).updateCarrera(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (!call.isSuccessful)
                    showToast("Error deleting information!")
                else
                    getListOfItems()
            }
        }
    }

    private fun handleDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            val requestBody = JSONObject()
            requestBody.put("codigo", carrera.codigo)

            val call =
                getRetrofit().create(com.example.services.Carreras::class.java).deleteCarrera(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (call.isSuccessful)
                    getListOfItems()
                else
                    showToast("Error deleting information!")
            }
        }
    }

    private fun getIndex(i: Int): Int {
        var index = i
        val adapterItems = adaptador.itemsList
        val listaCarreras = carrerasList

        carrera = adapterItems?.get(index)!!

        index = listaCarreras.indexOfFirst {
            it.codigo == carrera.codigo
        }

        return index
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    private fun openDialog(view: View, title: String, carrera: Carrera? = null) {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_carrera, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(view.context)
            .setView(mDialogView)
            .setTitle(title)
        //Show dialog
        val mAlertDialog = mBuilder.show()

        //Load Data
        val codigo = mDialogView.txt_code.editText
        val nombre = mDialogView.txt_name.editText
        val titulo = mDialogView.txt_degree.editText

        if (carrera != null)
            codigo?.isEnabled = false

        codigo?.setText(carrera?.codigo)
        nombre?.setText(carrera?.nombre)
        titulo?.setText(carrera?.titulo)

        mDialogView.btn_save.setOnClickListener {
            if (codigo?.text.toString() != "" && nombre?.text.toString() != "" && titulo?.text.toString() != "") {
                val requestBody = JSONObject()
                requestBody.put("codigo", codigo?.text.toString())
                requestBody.put("nombre", nombre?.text.toString())
                requestBody.put("titulo", titulo?.text.toString())

                if (carrera != null) {
                    handleUpdate(requestBody)
                } else {
                    handleInsert(requestBody)
                }

                mAlertDialog.dismiss()
            } else {
                Toast.makeText(activity, "Por favor llene todos los campos!", Toast.LENGTH_SHORT).show()
            }
        }

        //Close button click dialog
        mDialogView.btn_close.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}