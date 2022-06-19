package com.example.mobileclient

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.models.EstudiantesGrupo
import com.example.models.Grupo
import com.example.models.GruposProfesor
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.dialog_ingresar_nota.view.*
import kotlinx.android.synthetic.main.fragment_registro_notas.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class RegistroNotasFragment : Fragment() {

    lateinit var gruposList: ArrayList<EstudiantesGrupo>

    lateinit var lista: RecyclerView
    lateinit var adaptador: AdaptadorRegistroNotas
    lateinit var grupo: EstudiantesGrupo
    var position: Int = 0

    lateinit var itemGrupo: GruposProfesor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registro_notas, container, false)

        val usuario = JSONObject(arguments?.getSerializable("usuario").toString())

        val ddlGrupos = view.ddl_grupos.editText

        val selectGrupos = ddlGrupos as? AutoCompleteTextView

        var items: ArrayList<GruposProfesor>
        CoroutineScope(Dispatchers.IO).launch {
            val requestBody = JSONObject()
            requestBody.put("cedulaProfesor", usuario.getString("cedula"))

            val call =
                getRetrofit().create(com.example.services.Grupos::class.java).getGruposProfesor(requestBody.toString())
                    .execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    items = response as ArrayList<GruposProfesor>

                    val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
                    (ddlGrupos as? AutoCompleteTextView)?.setAdapter(adapter)
                } else
                    showToast("Not Data Found")
            }
        }

        lista = view.findViewById(R.id.item_list)
        lista.layoutManager = LinearLayoutManager(lista.context)
        lista.setHasFixedSize(true)

        selectGrupos?.setOnItemClickListener { parent, view, position, id ->
            itemGrupo = parent.getItemAtPosition(position) as GruposProfesor

            adaptador = AdaptadorRegistroNotas(arrayListOf())
            lista.adapter = adaptador

            if (ddlGrupos.text.toString() != "") {
                val requestBody = JSONObject()
                requestBody.put("numeroGrupo", itemGrupo.numeroGrupo)
                requestBody.put("codigoCurso", itemGrupo.codigoCurso)

                getListOfItems(requestBody)
            }
        }

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

                Collections.swap(gruposList, fromPosition, toPosition)

                lista.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                val index = getIndex(position)

                gruposList.removeAt(index)

                lista.adapter?.notifyItemRemoved(position)

                openDialog(view, "Ingresar Nota", grupo)

                gruposList.add(position, grupo)

                lista.adapter?.notifyItemInserted(position)

                adaptador = AdaptadorRegistroNotas(gruposList)
                lista.adapter = adaptador
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(view.context, R.color.green))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(view.context, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(lista)

        return view
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/gestion-academica/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getListOfItems(requestBody: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(com.example.services.EstudiantesGrupos::class.java)
                .getEstudiantesGrupo(requestBody.toString())
                .execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    gruposList = response as ArrayList<EstudiantesGrupo>

                    adaptador = AdaptadorRegistroNotas(gruposList)
                    lista.adapter = adaptador
                } else
                    showToast("Not Data Found")
            }
        }
    }

    private fun handleUpdate(requestBody: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(com.example.services.EstudiantesGrupos::class.java).ingresarNota(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (!call.isSuccessful)
                    showToast("Error deleting information!")
                else
                    getListOfItems(requestBody)
            }
        }
    }

    private fun getIndex(i: Int): Int {
        var index = i
        val adapterItems = adaptador.itemsList
        val listaGrupos = gruposList

        grupo = adapterItems?.get(index)!!

        index = listaGrupos.indexOfFirst {
            it.numeroGrupo == grupo.numeroGrupo && it.cedulaEstudiante == grupo.cedulaEstudiante
        }

        return index
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    private fun openDialog(view: View, title: String, grupo: EstudiantesGrupo? = null) {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_ingresar_nota, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(view.context)
            .setView(mDialogView)
            .setTitle(title)
        //Show dialog
        val mAlertDialog = mBuilder.show()

        //Load Data
        val cedulaEstudiante = mDialogView.txt_id.editText
        val nota = mDialogView.txt_score.editText

        if (grupo != null) {
            cedulaEstudiante?.isEnabled = false

            cedulaEstudiante?.setText(grupo.cedulaEstudiante)
            nota?.setText(grupo.nota.toString())
        }

        mDialogView.btn_save.setOnClickListener {
            if (cedulaEstudiante?.text.toString() != "" && nota?.text.toString() != "") {
                val requestBody = JSONObject()
                requestBody.put("cedulaEstudiante", grupo?.cedulaEstudiante)
                requestBody.put("numeroGrupo", itemGrupo.numeroGrupo)
                requestBody.put("codigoCurso", itemGrupo.codigoCurso)
                requestBody.put("nota", nota?.text.toString())

                if (grupo != null) {
                    handleUpdate(requestBody)
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