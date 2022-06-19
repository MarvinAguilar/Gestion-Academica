package com.example.mobileclient

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.models.GruposEstudiante

class AdaptadorHistorialAcademico(private var items: ArrayList<GruposEstudiante>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var itemsList: ArrayList<GruposEstudiante>? = null

    lateinit var mcontext: Context

    class gruposHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val gruposListView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_template_5fields, parent, false)
        val sch = gruposHolder(gruposListView)
        mcontext = parent.context
        return sch
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.findViewById<ImageView>(R.id.iv_Image).setImageResource(R.drawable.study_picture)

        holder.itemView.findViewById<TextView>(R.id.tv_field1)?.text = "Código Curso: ${item?.codigoCurso}"
        holder.itemView.findViewById<TextView>(R.id.tv_field2)?.text = "Nombre Curso: ${item?.nombreCurso}"
        holder.itemView.findViewById<TextView>(R.id.tv_field3)?.text =
            "Ciclo Lectivo: ${item?.numeroCiclo}-Ciclo ${item?.annoCiclo}"
        holder.itemView.findViewById<TextView>(R.id.tv_field4)?.text = "Créditos: ${item?.creditosCurso}"
        holder.itemView.findViewById<TextView>(R.id.tv_field5)?.text = "Nota: ${item?.nota}"

        holder.itemView.setOnClickListener {
            Log.d("Selected:", itemsList?.get(position)?.codigoCurso.toString())
        }
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<GruposEstudiante>()
                    for (row in items) {
                        if (row.codigoCurso.lowercase().contains(charSearch.lowercase())
                            || row.nombreCurso.lowercase().contains(charSearch.lowercase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    itemsList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsList = results?.values as ArrayList<GruposEstudiante>
                notifyDataSetChanged()
            }
        }
    }
}