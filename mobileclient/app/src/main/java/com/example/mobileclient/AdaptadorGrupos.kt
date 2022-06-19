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
import com.example.models.*

class AdaptadorGrupos(items: ArrayList<Grupo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemsList: ArrayList<Grupo>? = null

    lateinit var mcontext: Context

    class GrupoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val gruposListView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_template_5fields, parent, false)
        val sch = GrupoHolder(gruposListView)
        mcontext = parent.context
        return sch
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.findViewById<ImageView>(R.id.iv_Image).setImageResource(R.drawable.study_picture)

        holder.itemView.findViewById<TextView>(R.id.tv_field1)?.text = "Código: ${item?.numero}"
        holder.itemView.findViewById<TextView>(R.id.tv_field2)?.text = "Horario: ${item?.horario}"
        holder.itemView.findViewById<TextView>(R.id.tv_field3)?.text = "Profesor: ${item?.nombreProfesor}"
        holder.itemView.findViewById<TextView>(R.id.tv_field4)?.text = "Curso: ${item?.nombreCurso}"
        holder.itemView.findViewById<TextView>(R.id.tv_field5)?.text = "Ciclo: ${item?.numero}-Ciclo ${item?.annoCiclo}"

        holder.itemView.setOnClickListener {
            Log.d("Selected:", itemsList?.get(position)?.numero.toString())
        }
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }
}