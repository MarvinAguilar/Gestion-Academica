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
import com.example.models.GruposCarrera

class AdaptadorGruposCarrera(items: ArrayList<GruposCarrera>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemsList: ArrayList<GruposCarrera>? = null

    lateinit var mcontext: Context

    class gruposCarreraHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val gruposCarreraListView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_template_6fields, parent, false)
        val sch = gruposCarreraHolder(gruposCarreraListView)
        mcontext = parent.context
        return sch
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.findViewById<ImageView>(R.id.iv_Image).setImageResource(R.drawable.study_picture)

        holder.itemView.findViewById<TextView>(R.id.tv_field1)?.text = "Curso: ${item?.nombreCurso}"
        holder.itemView.findViewById<TextView>(R.id.tv_field2)?.text = "Grupo: ${item?.numeroGrupo}"
        holder.itemView.findViewById<TextView>(R.id.tv_field3)?.text = "Profesor: ${item?.nombreProfesor}"
        holder.itemView.findViewById<TextView>(R.id.tv_field4)?.text = "Curso: ${item?.creditos}"
        holder.itemView.findViewById<TextView>(R.id.tv_field5)?.text = "Grupo: ${item?.horario}"
        holder.itemView.findViewById<TextView>(R.id.tv_field6)?.text = "Nota: ${item?.horasSemanales}"

        holder.itemView.setOnClickListener {
            Log.d("Selected:", itemsList?.get(position)?.numeroGrupo.toString())
        }
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }
}