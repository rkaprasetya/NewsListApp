package com.raka.newslisttest.presentation.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raka.newslisttest.R
import com.raka.newslisttest.data.model.compact.ArticleCompact
import kotlinx.android.synthetic.main.item_article.view.*
import java.util.*
import kotlin.collections.ArrayList

class ArticleAdapter:RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(), Filterable {
    var filterList : MutableList<ArticleCompact> = mutableListOf()
    var list : MutableList<ArticleCompact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(
            itemView
        )
    }
    override fun getItemCount(): Int = filterList.size
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindItem(filterList[position])
    }
    fun updateData(data:MutableList<ArticleCompact>){
        list.addAll(data)
        list = list.distinct() as MutableList<ArticleCompact>
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
                    val resultList = ArrayList<ArticleCompact>()
                    for (row in list) {
                        if (row.title!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(
                                Locale.ROOT))) {
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
                filterList = results?.values as ArrayList<ArticleCompact>
                notifyDataSetChanged()
            }
        }
    }
    class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItem(item: ArticleCompact){
            itemView.tv_article_title.text = item.title
            itemView.tv_article_date.text = item.publishedAt
            itemView.tv_article_description.text = item.description
            Glide.with(itemView).load(item.urlToImage).into(itemView.iv_article)
            val bundle = bundleOf("url" to item.url)
            itemView.setOnClickListener {
                it.findNavController().navigate(R.id.action_articleFragment_to_detailArticleFragment,
                    bundle)
            }
        }
    }
}