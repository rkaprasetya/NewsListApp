package com.raka.newslisttest.presentation.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.raka.newslisttest.R
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter:RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    var list:MutableList<String> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(
            itemView
        )
    }
    override fun getItemCount(): Int  = list.size
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindItems(list[position])
    }
    fun updateData(data:MutableList<String>){
        this.list = data
        notifyDataSetChanged()
    }
    class CategoryViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindItems(item:String){
            itemView.tv_category_title.text = item
            val bundle = bundleOf("category" to item.toLowerCase())
            itemView.setOnClickListener {
                it.findNavController().navigate(R.id.action_categoryFragment_to_newsSourceFragment,
                    bundle)
            }
        }
    }
}