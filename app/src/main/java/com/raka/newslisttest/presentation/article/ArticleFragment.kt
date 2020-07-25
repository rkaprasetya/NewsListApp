package com.raka.newslisttest.presentation.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raka.newslisttest.R
import com.raka.newslisttest.data.model.compact.ArticleCompact
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_article.view.*

@AndroidEntryPoint
class ArticleFragment : Fragment() {
    private lateinit var category:String
    private lateinit var source:String
    private lateinit var idSource:String
    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var adapter: ArticleAdapter
    private var page = 1
    private var isLoading = false
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = ArticleFragmentArgs.fromBundle(requireArguments()).category
        source = ArticleFragmentArgs.fromBundle(requireArguments()).source
        idSource = ArticleFragmentArgs.fromBundle(requireArguments()).idsource
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        recyclerView = view.findViewById(R.id.rv_article)
        setAppBarClickListener(view)
        setRetryClickListener(view)
        setSearchViewListener(view)
        setAppBarTitle(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObserver()
        viewModel.getArticles(category,idSource,page.toString())
    }
    private fun setAppBarClickListener(view:View){
        view.ab_article.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
    private fun setAppBarTitle(view:View){
        view.tv_appbar_article.text = resources.getString(R.string.appbar_article_title,source)
    }
    private fun setRetryClickListener(view:View){
        view.btn_article_retry.setOnClickListener {
            viewModel.getArticles(category,idSource,page.toString())
            it.btn_article_retry.visibility = View.GONE
        }
    }
    private fun setupObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            isLoading = it
            if (it){
                showLoadingBar()
            }else{
                hideLoadingBar()
            }
        })
        viewModel.listArticle.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                setData(it)
            }else{
                showNoData()
            }
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                showToast(it)
                showNoData()
            }
        })
    }
    private fun showNoData(){
        rv_article.visibility = View.GONE
        btn_article_retry.visibility = View.VISIBLE
        hideLoadingBar()
    }
    private fun showToast(message:String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }
    private fun setData(data:List<ArticleCompact>){
        adapter.updateData(data as MutableList<ArticleCompact>)
    }
    private fun showLoadingBar(){
        pb_article.visibility = View.VISIBLE
        rv_article.visibility = View.INVISIBLE
    }
    private fun hideLoadingBar(){
        pb_article.visibility = View.GONE
        rv_article.visibility = View.VISIBLE
    }
    private fun setupAdapter(){
        adapter = ArticleAdapter()
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(activity,layoutManager.orientation))
        recyclerView.adapter = adapter
        setOnScrollListener(layoutManager)
    }
    private fun setOnScrollListener(layoutManager: LinearLayoutManager){
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val countItem = layoutManager.itemCount
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = adapter.getListSize()-1 == lastVisiblePosition
                if (!isLoading && isLastPosition){
                    page++
                    viewModel.getArticles(category,idSource,page.toString())
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setupAdapter()
        page = 1
        viewModel.getArticles(category,source,page.toString())
    }
    private fun setSearchViewListener(mView: View?) {
        mView!!.sv_article.queryHint = "Search Article"
        mView.sv_article.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(text: String?): Boolean {
                adapter.filter.filter(text)
                return true
            }
        })
    }
}