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
import com.example.models.Usuario

class AdaptadorSeguridad(private var items: ArrayList<Usuario>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var itemsList: ArrayList<Usuario>? = null

    lateinit var mcontext: Context

    class MatriculadorHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val matriculadorListView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_template_2fields, parent, false)
        val sch = MatriculadorHolder(matriculadorListView)
        mcontext = parent.context
        return sch
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.findViewById<ImageView>(R.id.iv_Image).setImageResource(R.drawable.profile_picture)

        holder.itemView.findViewById<TextView>(R.id.tv_field1)?.text = "Cédula: ${item?.cedula}"
        holder.itemView.findViewById<TextView>(R.id.tv_field2)?.text = if (item?.perfil == 1) "Administrador" else "Matriculador"

        holder.itemView.setOnClickListener {
            Log.d("Selected:", itemsList?.get(position)?.cedula.toString())
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
                    val resultList = ArrayList<Usuario>()
                    for (row in items) {
                        if (row.cedula.lowercase().contains(charSearch.lowercase())

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
                itemsList = results?.values as ArrayList<Usuario>
                notifyDataSetChanged()
            }
        }
    }
}