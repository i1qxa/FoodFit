package aps.foodfit.jyrbf.ui.remote

import android.content.Context
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import aps.foodfit.jyrbf.domain.updateSavedLink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WVClientFoodFit(private val contextFoodFit:Context):WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.visibility = View.VISIBLE
        CookieManager.getInstance().flush()
    }

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url?.contains("app_link_save") == true) checkPath(url)
        return false
    }

    private fun checkPath(kotikHouse: String) {
        val regex = Regex("""(app_link_save=https://[a-z\d./-]*)""")
        val newFoodFitSavedLink = regex.find(kotikHouse)?.value
        if (newFoodFitSavedLink != null) {
            val clearNewLink = newFoodFitSavedLink.replace("app_link_save=", "")
            CoroutineScope(Dispatchers.IO).launch {
                updateSavedLink(contextFoodFit, clearNewLink)
            }
        }
    }

}