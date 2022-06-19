package com.example.mobileclient

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.models.Ciclo
import com.example.models.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.annotations.SerializedName
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.dialog_ciclo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class CiclosFragment : Fragment() {

    lateinit var ciclosList: ArrayList<Ciclo>

    lateinit var lista: RecyclerView
    lateinit var adaptador: AdaptadorCiclos
    lateinit var ciclo: Ciclo
    var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ciclos, container, false)

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

                Collections.swap(ciclosList, fromPosition, toPosition)

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
                        "$ciclo se eliminaría...",
                        Snackbar.LENGTH_LONG
                    ).setAction("Undo") {
                        val requestBody = JSONObject()
                        requestBody.put("anno", ciclo.anno)
                        requestBody.put("numero", ciclo.numero)
                        requestBody.put("fechaInicio", ciclo.fechaInicio)
                        requestBody.put("fechaFinal", ciclo.fechaFinal)
                        requestBody.put("estado", ciclo.estado)
                        handleInsert(requestBody)
                    }.show()

                    adaptador = AdaptadorCiclos(ciclosList)
                    lista.adapter = adaptador

                } else {
                    val index = getIndex(position)

                    ciclosList.removeAt(index)
                    lista.adapter?.notifyItemRemoved(position)

                    openDialog(view, "Actualizar Ciclo", ciclo)

                    ciclosList.add(position, ciclo)
                    lista.adapter?.notifyItemInserted(position)

                    adaptador = AdaptadorCiclos(ciclosList)
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
            openDialog(view, "Agregar Ciclo")
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
            val call = getRetrofit().create(com.example.services.Ciclos::class.java).getCiclos().execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    ciclosList = response as ArrayList<Ciclo>

                    adaptador = AdaptadorCiclos(ciclosList)
                    lista.adapter = adaptador
                } else
                    showToast("Not Data Found")
            }
        }
    }

    private fun handleInsert(requestBody: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(com.example.services.Ciclos::class.java).insertCiclo(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (!call.isSuccessful)
                    showToast("Error inserting information!")
                else
                    getListOfItems()
            }
        }
    }

    private fun handleUpdate(requestBody: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(com.example.services.Ciclos::class.java).updateCiclo(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (!call.isSuccessful)
                    showToast("Error updating information!")
                else
                    getListOfItems()
            }
        }
    }

    private fun handleDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            val requestBody = JSONObject()
            requestBody.put("anno", ciclo.anno)
            requestBody.put("numero", ciclo.numero)

            val call =
                getRetrofit().create(com.example.services.Ciclos::class.java).deleteCiclo(requestBody.toString())
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
        val listaCiclos = ciclosList

        ciclo = adapterItems?.get(index)!!

        index = listaCiclos.indexOfFirst {
            it.anno == ciclo.anno && it.numero == ciclo.numero
        }

        return index
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    private fun openDialog(view: View, title: String, ciclo: Ciclo? = null) {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_ciclo, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(view.context)
            .setView(mDialogView)
            .setTitle(title)
        //Show dialog
        val mAlertDialog = mBuilder.show()

        //Load Data
        val anno = mDialogView.txt_year.editText
        val numero = mDialogView.ddl_number.editText
        val fechaInicio = mDialogView.txt_startDay.editText
        val fechaFinal = mDialogView.txt_endDay.editText
        val estado = mDialogView.ddl_state

        if (ciclo != null) {
            anno?.isEnabled = false
            numero?.isEnabled = false
            estado.isVisible = true

            anno?.setText(ciclo.anno.toString())
            numero?.setText(ciclo.numero.toString())
            fechaInicio?.setText(ciclo.fechaInicio)
            fechaFinal?.setText(ciclo.fechaFinal)
            if (ciclo.estado == "A") estado.editText?.setText("Activo") else estado.editText?.setText("Inactivo")
        }

        val itemsNumber = ArrayList<Int>()
        itemsNumber.add(1)
        itemsNumber.add(2)

        val adapter1 = ArrayAdapter(requireContext(), R.layout.dropdown_item, itemsNumber)
        (numero as? AutoCompleteTextView)?.setAdapter(adapter1)

        val itemsState = ArrayList<String>()
        itemsState.add("Activo")
        itemsState.add("Inactivo")

        val adapter2 = ArrayAdapter(requireContext(), R.layout.dropdown_item, itemsState)
        (estado.editText as? AutoCompleteTextView)?.setAdapter(adapter2)

        mDialogView.btn_save.setOnClickListener {
            if (anno?.text.toString() != "" && numero?.text.toString() != "" && fechaInicio?.text.toString() != "" && fechaFinal?.text.toString() != "") {
                val requestBody = JSONObject()
                requestBody.put("anno", anno?.text.toString())
                requestBody.put("numero", numero?.text.toString())
                requestBody.put("fechaInicio", fechaInicio?.text.toString())
                requestBody.put("fechaFinal", fechaFinal?.text.toString())
                requestBody.put("estado", if (estado.editText?.text.toString() == "Activo") "A" else "I")

                if (ciclo != null) {
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