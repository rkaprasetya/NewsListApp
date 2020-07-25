package com.raka.newslisttest.presentation.detailarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.raka.newslisttest.R
import com.raka.newslisttest.utils.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_article.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class DetailArticleFragment : Fragment() {
    private lateinit var url:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = DetailArticleFragmentArgs.fromBundle(requireArguments()).url
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_article, container, false)
        setAppBarClickListener(view)
        checkInternet(view)
        return view
    }

    private fun setAppBarClickListener(mView: View?) {
        mView!!.ab_detail.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun checkInternet(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val isInternetAvailable = withContext(Dispatchers.Default) {
                Util.isInternetAvailable()
            }
            if (isInternetAvailable) {
                setWebview(view)
            } else {
                showToast("Please turn on your internet connection")
                setRetryButton(view)
            }
        }
    }
    private fun showToast(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }
    private fun setRetryButton(view: View){
        view.btn_detail_retry.visibility = View.VISIBLE
        view.btn_detail_retry.setOnClickListener {
            checkInternet(view)
            it.btn_detail_retry.visibility = View.GONE
        }
    }

    private fun setWebview(view:View) {
        view.wv_detail.visibility = View.VISIBLE
        view.wv_detail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        view.wv_detail.loadUrl(url)
    }
}