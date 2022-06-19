package com.example.mobileclient

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.models.EstudiantesGrupo

class AdaptadorRegistroNotas(items: ArrayList<EstudiantesGrupo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemsList: ArrayList<EstudiantesGrupo>? = null

    lateinit var mcontext: Context

    class EstudiantesGrupoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val EstudiantesGrupoListView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_template_6fields, parent, false)
        val sch = EstudiantesGrupoHolder(EstudiantesGrupoListView)
        mcontext = parent.context
        return sch
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.findViewById<ImageView>(R.id.iv_Image).setImageResource(R.drawable.study_picture)

        holder.itemView.findViewById<TextView>(R.id.tv_field1)?.text = "Cédula: ${item?.cedulaEstudiante}"
        holder.itemView.findViewById<TextView>(R.id.tv_field2)?.text = "Nombre: ${item?.nombreEstudiante}"
        holder.itemView.findViewById<TextView>(R.id.tv_field3)?.text = "Carrera: ${item?.carreraEstudiante}"
        holder.itemView.findViewById<TextView>(R.id.tv_field4)?.text = "Curso: ${item?.nombreCurso}"
        holder.itemView.findViewById<TextView>(R.id.tv_field5)?.text = "Grupo: ${item?.numeroGrupo}"
        holder.itemView.findViewById<TextView>(R.id.tv_field6)?.text = "Nota: ${item?.nota}"

        holder.itemView.setOnClickListener {
            Log.d("Selected:", itemsList?.get(position)?.numeroGrupo.toString())
        }
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }
}