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
import com.example.models.Carrera

class AdaptadorCarreras(private var items: ArrayList<Carrera>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var itemsList: ArrayList<Carrera>? = null

    lateinit var mcontext: Context

    class CarreraHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val carrerasListView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_template_3fields, parent, false)
        val sch = CarreraHolder(carrerasListView)
        mcontext = parent.context
        return sch
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.findViewById<ImageView>(R.id.iv_Image).setImageResource(R.drawable.study_picture)

        holder.itemView.findViewById<TextView>(R.id.tv_field1)?.text = "Código: ${item?.codigo}"
        holder.itemView.findViewById<TextView>(R.id.tv_field2)?.text = item?.nombre
        holder.itemView.findViewById<TextView>(R.id.tv_field3)?.text = item?.titulo

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
                    val resultList = ArrayList<Carrera>()
                    for (row in items) {
                        if (row.codigo.lowercase().contains(charSearch.lowercase())
                            || row.nombre.lowercase().contains(charSearch.lowercase())
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
                itemsList = results?.values as ArrayList<Carrera>
                notifyDataSetChanged()
            }
        }
    }
}