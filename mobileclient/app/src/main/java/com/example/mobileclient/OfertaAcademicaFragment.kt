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
import com.example.models.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.dialog_carrera.view.btn_close
import kotlinx.android.synthetic.main.dialog_carrera.view.btn_save
import kotlinx.android.synthetic.main.dialog_grupo.view.*
import kotlinx.android.synthetic.main.fragment_oferta_academica.*
import kotlinx.android.synthetic.main.fragment_oferta_academica.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class OfertaAcademicaFragment : Fragment() {

    lateinit var gruposList: ArrayList<Grupo>

    lateinit var lista: RecyclerView
    lateinit var adaptador: AdaptadorGrupos
    lateinit var grupo: Grupo
    var position: Int = 0

    lateinit var itemCarrera: Carrera
    lateinit var itemCiclo: Ciclo
    lateinit var itemCurso: CursosCarrera

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_oferta_academica, container, false)

        val ddlCarreras = view.ddl_carreras.editText
        val ddlCiclos = view.ddl_ciclos.editText
        val ddlCursos = view.ddl_cursos

        val selectCarreras = ddlCarreras as? AutoCompleteTextView
        val selectCiclos = ddlCiclos as? AutoCompleteTextView
        val selectCursos = ddlCursos.editText as? AutoCompleteTextView

        var items: ArrayList<Carrera>
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(com.example.services.Carreras::class.java).getCarreras().execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    items = response as ArrayList<Carrera>

                    val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
                    (ddlCarreras as? AutoCompleteTextView)?.setAdapter(adapter)
                } else
                    showToast("Not Data Found")
            }
        }

        var items2: ArrayList<Ciclo>
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(com.example.services.Ciclos::class.java).getCiclos().execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    items2 = response as ArrayList<Ciclo>

                    val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items2)
                    (ddlCiclos as? AutoCompleteTextView)?.setAdapter(adapter)
                } else
                    showToast("Not Data Found")
            }
        }

        selectCarreras?.setOnItemClickListener { parent, view, position, id ->
            itemCarrera = parent.getItemAtPosition(position) as Carrera

            clearSelection(ddlCursos)

            if (ddlCarreras.text.toString() == "" || ddlCiclos?.text.toString() == "") {
                ddlCursos.isEnabled = false
            } else {
                ddlCursos.isEnabled = true

                var items3: ArrayList<CursosCarrera>
                CoroutineScope(Dispatchers.IO).launch {
                    val requestBody = JSONObject()
                    requestBody.put("codigoCarrera", itemCarrera.codigo)
                    requestBody.put("annoCiclo", itemCiclo.anno)
                    requestBody.put("numeroCiclo", itemCiclo.numero)

                    val call = getRetrofit().create(com.example.services.CursosCarrera::class.java)
                        .getCursosCarrera(requestBody.toString()).execute()
                    val response = if (call.isSuccessful) call.body() else null

                    activity?.runOnUiThread {
                        if (!response.isNullOrEmpty()) {
                            items3 = response as ArrayList<CursosCarrera>

                            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items3)
                            (ddlCursos.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                        } else
                            showToast("Not Data Found")
                    }
                }
            }
        }

        selectCiclos?.setOnItemClickListener { parent, view, position, id ->
            itemCiclo = parent.getItemAtPosition(position) as Ciclo

            clearSelection(ddlCursos)

            if (ddlCarreras?.text.toString() == "" || ddlCiclos.text.toString() == "") {
                ddlCursos?.isEnabled = false
            } else {
                ddlCursos.isEnabled = true

                var items3: ArrayList<CursosCarrera>
                CoroutineScope(Dispatchers.IO).launch {
                    val requestBody = JSONObject()
                    requestBody.put("codigoCarrera", itemCarrera.codigo)
                    requestBody.put("annoCiclo", itemCiclo.anno)
                    requestBody.put("numeroCiclo", itemCiclo.numero)

                    val call = getRetrofit().create(com.example.services.CursosCarrera::class.java)
                        .getCursosCarrera(requestBody.toString()).execute()
                    val response = if (call.isSuccessful) call.body() else null

                    activity?.runOnUiThread {
                        if (!response.isNullOrEmpty()) {
                            items3 = response as ArrayList<CursosCarrera>

                            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items3)
                            (ddlCursos.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                        } else
                            showToast("Not Data Found")
                    }
                }
            }
        }

        lista = view.findViewById(R.id.item_list)
        lista.layoutManager = LinearLayoutManager(lista.context)
        lista.setHasFixedSize(true)

        selectCursos?.setOnItemClickListener { parent, view, position, id ->
            itemCurso = parent.getItemAtPosition(position) as CursosCarrera

            if (ddlCursos.editText?.text.toString() != "") {
                add.isEnabled = true

                val requestBody = JSONObject()
                requestBody.put("codigoCurso", itemCurso.codigoCurso)
                requestBody.put("annoCiclo", itemCiclo.anno)
                requestBody.put("numeroCiclo", itemCiclo.numero)

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

                openDialog(view, "Actualizar Grupo", grupo)

                gruposList.add(position, grupo)

                lista.adapter?.notifyItemInserted(position)

                adaptador = AdaptadorGrupos(gruposList)
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

        val add: FloatingActionButton = view.findViewById(R.id.add)
        add.setOnClickListener {
            openDialog(view, "Añadir Grupo")
        }

        return view
    }

    private fun clearSelection(ddlCursos: TextInputLayout) {
        ddlCursos.editText?.setText("")
        add.isEnabled = false

        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, arrayOf<CursosCarrera>())
        (ddlCursos.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        adaptador = AdaptadorGrupos(arrayListOf())
        lista.adapter = adaptador
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
            val call = getRetrofit().create(com.example.services.Grupos::class.java).getGrupos(requestBody.toString())
                .execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    gruposList = response as ArrayList<Grupo>

                    adaptador = AdaptadorGrupos(gruposList)
                    lista.adapter = adaptador
                } else
                    showToast("Not Data Found")
            }
        }
    }

    private fun handleInsert(requestBody: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(com.example.services.Grupos::class.java).insertGrupo(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (!call.isSuccessful)
                    showToast("Error deleting information!")
                else
                    getListOfItems(requestBody)
            }
        }
    }

    private fun handleUpdate(requestBody: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(com.example.services.Grupos::class.java).updateGrupo(requestBody.toString())
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
            it.numero == grupo.numero && it.codigoCurso == grupo.codigoCurso && it.annoCiclo == grupo.annoCiclo && it.numeroCiclo == grupo.numeroCiclo
        }

        return index
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

    private fun openDialog(view: View, title: String, grupo: Grupo? = null) {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_grupo, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(view.context)
            .setView(mDialogView)
            .setTitle(title)
        //Show dialog
        val mAlertDialog = mBuilder.show()

        //Load Data
        val numero = mDialogView.txt_number.editText
        val horario = mDialogView.txt_schedule.editText
        val cedulaProfesor = mDialogView.txt_id.editText

        if (grupo != null) {
            numero?.isEnabled = false

            numero?.setText(grupo.numero.toString())
            horario?.setText(grupo.horario)
            cedulaProfesor?.setText(grupo.cedulaProfesor)
        }

        mDialogView.btn_save.setOnClickListener {
            if (numero?.text.toString() != "" && horario?.text.toString() != "" && cedulaProfesor?.text.toString() != "") {
                val requestBody = JSONObject()
                requestBody.put("numero", numero?.text.toString())
                requestBody.put("horario", horario?.text.toString())
                requestBody.put("cedulaProfesor", cedulaProfesor?.text.toString())
                requestBody.put("codigoCurso", itemCurso.codigoCurso)
                requestBody.put("annoCiclo", itemCiclo.anno)
                requestBody.put("numeroCiclo", itemCiclo.numero)

                if (grupo != null) {
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