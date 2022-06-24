package com.example.mobileclient

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.models.Alumno
import com.example.models.Ciclo
import com.example.models.GruposCarrera
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_matricula.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class MatriculaFragment : Fragment() {

    lateinit var gruposList: ArrayList<GruposCarrera>

    lateinit var lista: RecyclerView
    lateinit var adaptador: AdaptadorGruposCarrera
    lateinit var grupo: GruposCarrera
    var position: Int = 0

    lateinit var itemCiclo: Ciclo
    lateinit var estudiante: Alumno

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matricula, container, false)

        val txtCedulaEstudiante = view.txt_cedulaEstudiante.editText?.text
        val ddlCiclos = view.ddl_ciclos.editText

        val selectCiclos = ddlCiclos as? AutoCompleteTextView

        view.btn_search.setOnClickListener {
            if (txtCedulaEstudiante.toString() == "") {
                showToast("Por favor ingrese la cédula del estudiante a matrícular!")
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val requestBody = JSONObject()
                requestBody.put("cedula", txtCedulaEstudiante.toString())

                val call =
                    getRetrofit().create(com.example.services.Alumnos::class.java).getAlumno(requestBody.toString())
                        .execute()
                val response = if (call.isSuccessful) call.body() else null

                activity?.runOnUiThread {
                    if (response != null) {
                        estudiante = response
                        view.txt_nombreEstudiante.editText?.setText(estudiante.nombre)
                    } else
                        showToast("Not Data Found")
                }
            }
        }

        var items: ArrayList<Ciclo>
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(com.example.services.Ciclos::class.java).getCiclos().execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    items = response as ArrayList<Ciclo>

                    val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
                    (ddlCiclos as? AutoCompleteTextView)?.setAdapter(adapter)
                } else
                    showToast("Not Data Found")
            }
        }

        selectCiclos?.setOnItemClickListener { parent, view, position, id ->
            itemCiclo = parent.getItemAtPosition(position) as Ciclo

            if (estudiante.cedula != "" && ddlCiclos.text.toString() != "") {
                val requestBody = JSONObject()
                requestBody.put("codigoCarrera", estudiante.codigoCarrera)
                requestBody.put("cedulaEstudiante", estudiante.cedula)
                requestBody.put("annoCiclo", itemCiclo.anno)
                requestBody.put("numeroCiclo", itemCiclo.numero)

                getListOfItems(requestBody)
            }
        }

        lista = view.findViewById(R.id.item_list)
        lista.layoutManager = LinearLayoutManager(lista.context)
        lista.setHasFixedSize(true)

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

                if (grupo.estadoMatricula == "Matriculado") {
                    handleDesmatricula(grupo)
                } else {
                    handleMatricula(grupo)
                }

                gruposList.add(position, grupo)

                lista.adapter?.notifyItemInserted(position)

                adaptador = AdaptadorGruposCarrera(gruposList)
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(view.context, R.color.custom_blue))
                    .addSwipeLeftActionIcon(R.drawable.ic_add_black_material_icon)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(view.context, R.color.custom_blue))
                    .addSwipeRightActionIcon(R.drawable.ic_add_black_material_icon)
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
            val call =
                getRetrofit().create(com.example.services.Grupos::class.java).getGruposCarrera(requestBody.toString())
                    .execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    gruposList = response as ArrayList<GruposCarrera>

                    adaptador = AdaptadorGruposCarrera(gruposList)
                    lista.adapter = adaptador
                } else
                    showToast("Not Data Found")
            }
        }
    }

    private fun handleMatricula(grupo: GruposCarrera) {
        CoroutineScope(Dispatchers.IO).launch {
            val requestBody = JSONObject()
            requestBody.put("cedulaEstudiante", estudiante.cedula)
            requestBody.put("codigoCarrera", estudiante.codigoCarrera)
            requestBody.put("numeroGrupo", grupo.numeroGrupo)
            requestBody.put("codigoCurso", grupo.codigoCurso)
            requestBody.put("annoCiclo", grupo.annoCiclo)
            requestBody.put("numeroCiclo", grupo.numeroCiclo)

            val call =
                getRetrofit().create(com.example.services.EstudiantesGrupos::class.java)
                    .matriculaEstudiante(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (!call.isSuccessful)
                    showToast("No se pudo realizar la matrícula!")
                else {
                    getListOfItems(requestBody)
                    showToast("El estudiante ha sido matriculado correctamente")
                }
            }
        }
    }

    private fun handleDesmatricula(grupo: GruposCarrera) {
        CoroutineScope(Dispatchers.IO).launch {
            val requestBody = JSONObject()
            requestBody.put("cedulaEstudiante", estudiante.cedula)
            requestBody.put("codigoCarrera", estudiante.codigoCarrera)
            requestBody.put("numeroGrupo", grupo.numeroGrupo)
            requestBody.put("codigoCurso", grupo.codigoCurso)
            requestBody.put("annoCiclo", grupo.annoCiclo)
            requestBody.put("numeroCiclo", grupo.numeroCiclo)

            val call =
                getRetrofit().create(com.example.services.EstudiantesGrupos::class.java)
                    .desmatriculaEstudiante(requestBody.toString())
                    .execute()

            activity?.runOnUiThread {
                if (!call.isSuccessful)
                    showToast("No se pudo desmatricular!")
                else {
                    getListOfItems(requestBody)
                    showToast("El estudiante ha sido desmatriculado")
                }
            }
        }
    }

    private fun getIndex(i: Int): Int {
        var index = i
        val adapterItems = adaptador.itemsList
        val listaGrupos = gruposList

        grupo = adapterItems?.get(index)!!

        index = listaGrupos.indexOfFirst {
            it.numeroGrupo == grupo.numeroGrupo && it.codigoCurso == grupo.codigoCurso && it.annoCiclo == grupo.annoCiclo && it.numeroCiclo == grupo.numeroCiclo
        }

        return index
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}