package com.raka.newslisttest.presentation.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raka.newslisttest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        recyclerView = view.findViewById(R.id.rv_category)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObserver()
    }
    private fun setupObserver() {
          viewModel.categoryList.observe(viewLifecycleOwner, Observer {
              setData(it)
          })
    }
    private fun setupAdapter(){
        adapter = CategoryAdapter()
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(activity,layoutManager.orientation))
        recyclerView.adapter = adapter
    }
    private fun setData(data:List<String>) {
        adapter.updateData(data as MutableList<String>)
    }
}