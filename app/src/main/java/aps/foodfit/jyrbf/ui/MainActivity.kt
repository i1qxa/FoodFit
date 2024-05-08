package aps.foodfit.jyrbf.ui

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.ActivityMainBinding
import aps.foodfit.jyrbf.domain.DS_FOOD_FIT_AF_STATUS
import aps.foodfit.jyrbf.domain.DS_FOOD_FIT_CAMPAIGN
import aps.foodfit.jyrbf.domain.DS_FOOD_FIT_SAVEDLINK
import aps.foodfit.jyrbf.domain.InternetConnectionStatus
import aps.foodfit.jyrbf.domain.dataStore
import aps.foodfit.jyrbf.ui.remote.RepoImplFoodFit
import aps.foodfit.jyrbf.ui.remote.WVClientFoodFit
import aps.foodfit.jyrbf.ui.welcome.FOOD_FIT_PREFS_NAME
import aps.foodfit.jyrbf.ui.welcome.MY_DELAY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

const val SHOULD_REQUEST_NOTIFICATION_PERMISSIONS = "should_request_perms"
const val VIEW_FOOD_FIT_BUNDLE = "view_food_fit_bundle"

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val prefs by lazy { getSharedPreferences(FOOD_FIT_PREFS_NAME, Context.MODE_PRIVATE) }
    private val viewFoodFit by lazy { WebView(this) }
    private var startTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        startTime = android.icu.util.Calendar.getInstance().timeInMillis
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        checkInternetConnection()
        observeInternetStatus()
        observeAppsDataFoodFit()
    }

    private fun observeAppsDataFoodFit(){
        val repo = RepoImplFoodFit(this@MainActivity)
        lifecycleScope.launch {

            this@MainActivity.dataStore.data.collect{
                val afStatus = it[DS_FOOD_FIT_AF_STATUS]?:""
                val adress = it[DS_FOOD_FIT_SAVEDLINK]?:""
                if (adress.isEmpty() && afStatus.isNotEmpty()){

                    repo.getLinkFoodFit()
                }
            }
        }
    }

    private fun launchViewFoodFit() {
        startTime = android.icu.util.Calendar.getInstance().timeInMillis
        CoroutineScope(Dispatchers.IO).launch {
            applicationContext.dataStore.data.collect {
                val adressFoodFit = it[DS_FOOD_FIT_SAVEDLINK] ?: ""
                if (adressFoodFit.isNotEmpty()) {
                    val currentTime = android.icu.util.Calendar.getInstance().timeInMillis
                    if (currentTime - startTime <= MY_DELAY) {
                        withContext(Dispatchers.Main){
                            setupKotikView(null, adressFoodFit)
                            binding.main.removeView(binding.welcomeConteiner)
                            try {
                                binding.main.addView(viewFoodFit)
                                viewFoodFit.layoutParams.apply {
                                    height = ConstraintLayout.LayoutParams.MATCH_PARENT
                                    width = ConstraintLayout.LayoutParams.MATCH_PARENT
                                }
                            } catch (e: Exception) {
                            }
                        }

                    }
                }
            }
        }
    }

    private fun setupKotikView(state: Bundle?, adressFoodFit: String?) {
        CookieManager.getInstance().acceptCookie()
        val bundle = state?.getBundle(VIEW_FOOD_FIT_BUNDLE)
        if (bundle != null) {
            viewFoodFit.restoreState(bundle)
        } else {
            if (adressFoodFit?.isNotEmpty() == true) {
                viewFoodFit.loadUrl(adressFoodFit)
                viewFoodFit.settings.domStorageEnabled = true
                viewFoodFit.settings.javaScriptEnabled = true
                viewFoodFit.webViewClient = WVClientFoodFit(applicationContext)
                viewFoodFit.settings.setSupportZoom(false)
                viewFoodFit.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            }
            onBackPressedDispatcher.addCallback(backBehavior)
        }

    }

    private val backBehavior = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (viewFoodFit.canGoBack()) viewFoodFit.goBack()
            else this@MainActivity.finishAffinity()
        }
    }

    private fun checkInternetConnection() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork

        val activeNetwork = connectivityManager.getNetworkCapabilities(network)

        if (network == null || activeNetwork == null) {
            viewModel.updateInternetStatus(false)
        } else {
            val status = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> true

                else -> false
            }
            viewModel.updateInternetStatus(status)
        }
    }

    private fun observeInternetStatus() {
        viewModel.internetConnectionStatusLD.observe(this) { internetConnectionStatus ->
            when (internetConnectionStatus) {
                InternetConnectionStatus.CONNECTED -> {
                    checkNotificationPermissions()
                    launchViewFoodFit()
                }

                InternetConnectionStatus.DISCONNECTED -> {
                    showCheckInternetDialog()
                }
            }
        }
    }

    private fun showCheckInternetDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        with(dialogBuilder) {
            setTitle(getString(R.string.dialog_title))
            setMessage(getString(R.string.dialog_text))
            setPositiveButton(getString(R.string.btn_check_internet)) { dialog, id ->
                checkInternetConnection()
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun checkNotificationPermissions() {
        if (prefs.getBoolean(
                SHOULD_REQUEST_NOTIFICATION_PERMISSIONS,
                true
            )
        ) askNotificationPermission()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        prefs.edit().putBoolean(SHOULD_REQUEST_NOTIFICATION_PERMISSIONS, true).apply()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                showRequestPermissionDialog()
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showRequestPermissionDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        with(dialogBuilder) {
            setTitle(getString(R.string.request_perms_title))
            setMessage(getString(R.string.request_dialog_text))
            setPositiveButton(getString(R.string.allow)) { dialog, id ->
                prefs.edit().putBoolean(SHOULD_REQUEST_NOTIFICATION_PERMISSIONS, false).apply()
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.dis_allow)) { dialog, id ->
                prefs.edit().putBoolean(SHOULD_REQUEST_NOTIFICATION_PERMISSIONS, false).apply()
                dialog.cancel()
            }
            create()
            show()
        }
    }

}