package com.example.foodapp.viewprimarypage

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.foodapp.R
import kotlinx.android.synthetic.main.fragment_youtube_web_view.*

class YouTubeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_youtube_web_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
        loadWebPageWithUrl(arguments?.getString(URL_BUNDLE_KEY) ?: "")
    }

    private fun loadWebPageWithUrl(url: String) {
        viewWebPage.loadUrl(url)
    }

    private fun initWebView() {
        with(viewWebPage) {
            settings.apply {
                loadsImagesAutomatically = true
                javaScriptEnabled = true
            }

            webChromeClient = WebChromeClient()

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    viewProgressBar.visibility = VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    viewProgressBar.visibility = GONE
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    view.loadUrl(request.url.toString())
                    return true
                }
            }
        }
    }

    companion object {
        const val TAG = "YouTubeFragment"
        private const val URL_BUNDLE_KEY = "URL_BUNDLE_KEY"

        fun newInstance(url: String): YouTubeFragment {
            val bundle = Bundle().apply {
                putString(URL_BUNDLE_KEY, url)
            }
            return YouTubeFragment().apply {
                arguments = bundle
            }
        }
    }
}