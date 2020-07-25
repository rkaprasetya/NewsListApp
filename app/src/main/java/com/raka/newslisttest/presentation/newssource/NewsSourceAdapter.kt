package com.raka.newslisttest.presentation.newssource

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.raka.newslisttest.R
import com.raka.newslisttest.data.model.compact.NewsSourceCompact
import kotlinx.android.synthetic.main.item_category.view.*
import java.util.*
import kotlin.collections.ArrayList

class NewsSourceAdapter:RecyclerView.Adapter<NewsSourceAdapter.NewsSourceViewHolder>(),Filterable {
    var filterList : MutableList<NewsSourceCompact> = mutableListOf()
    var list : MutableList<NewsSourceCompact> = mutableListOf()
    var category:String = "general"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_category, parent, false)
        return NewsSourceViewHolder(
            itemView,category
        )
    }
    override fun onBindViewHolder(holder: NewsSourceViewHolder, position: Int) {
        holder.bindItem(filterList[position])
    }
    override fun getItemCount(): Int = filterList.size
    fun updateList(data:MutableList<NewsSourceCompact>,category:String){
        this.category = category
        list.addAll(data)
        list = list.distinct() as MutableList<NewsSourceCompact>
        filterList = list
        notifyDataSetChanged()
    }
    fun getListSize():Int = list.size
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = list
                } else {
                    val resultList = ArrayList<NewsSourceCompact>()
                    for (row in list) {
                        if (row.name!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<NewsSourceCompact>
                notifyDataSetChanged()
            }

        }
    }
    class NewsSourceViewHolder(itemView: View,private val category: String):RecyclerView.ViewHolder(itemView){
        fun bindItem(item:NewsSourceCompact){
            itemView.tv_category_title.text = item.name
            val bundle = bundleOf("source" to item.name,"category" to category,"idsource" to item.id)
            itemView.setOnClickListener {
                it.findNavController().navigate(R.id.action_newsSourceFragment_to_articleFragment,
                    bundle)
            }
        }
    }
}