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
import com.example.models.Alumno

class AdaptadorAlumnos(private var items: ArrayList<Alumno>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var itemsList: ArrayList<Alumno>? = null

    lateinit var mcontext: Context

    class AlumnoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val alumnosListView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_template_3fields, parent, false)
        val sch = AlumnoHolder(alumnosListView)
        mcontext = parent.context
        return sch
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.findViewById<ImageView>(R.id.iv_Image).setImageResource(R.drawable.profile_picture)

        holder.itemView.findViewById<TextView>(R.id.tv_field1)?.text = "Cédula: ${item?.cedula}"
        holder.itemView.findViewById<TextView>(R.id.tv_field2)?.text = item?.nombre
        holder.itemView.findViewById<TextView>(R.id.tv_field3)?.text = item?.nombreCarrera

        holder.itemView.setOnClickListener {
            Log.d("Selected:", itemsList?.get(position)?.nombre.toString())
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
                    val resultList = ArrayList<Alumno>()
                    for (row in items) {
                        if (row.cedula.lowercase().contains(charSearch.lowercase())
                            || row.nombre.lowercase().contains(charSearch.lowercase())
                            || row.nombreCarrera.lowercase().contains(charSearch.lowercase())

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
                itemsList = results?.values as ArrayList<Alumno>
                notifyDataSetChanged()
            }
        }
    }
}