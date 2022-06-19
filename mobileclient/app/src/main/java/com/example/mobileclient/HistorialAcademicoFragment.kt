package com.example.mobileclient

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.models.EstudiantesGrupo
import com.example.models.GruposEstudiante
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class HistorialAcademicoFragment : Fragment() {

    lateinit var gruposList: ArrayList<GruposEstudiante>

    lateinit var lista: RecyclerView
    lateinit var adaptador: AdaptadorHistorialAcademico

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historial_academico, container, false)

        val usuario = JSONObject(arguments?.getSerializable("usuario").toString())

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

        val requestBody = JSONObject()
        requestBody.put("cedulaEstudiante", usuario.getString("cedula"))

        getListOfItems(requestBody)

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
                .getHistorialEstudiante(requestBody.toString()).execute()
            val response = if (call.isSuccessful) call.body() else null

            activity?.runOnUiThread {
                if (!response.isNullOrEmpty()) {
                    gruposList = response as ArrayList<GruposEstudiante>

                    adaptador = AdaptadorHistorialAcademico(gruposList)
                    lista.adapter = adaptador
                } else
                    showToast("Not Data Found")
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}