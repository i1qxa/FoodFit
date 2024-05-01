package aps.foodfit.jyrbf.ui

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.ActivityMainBinding
import aps.foodfit.jyrbf.domain.InternetConnectionStatus
import aps.foodfit.jyrbf.ui.welcome.FOOD_FIT_PREFS_NAME

const val SHOULD_REQUEST_NOTIFICATION_PERMISSIONS = "should_request_perms"

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val prefs by lazy { getSharedPreferences(FOOD_FIT_PREFS_NAME, Context.MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        checkInternetConnection()
        observeInternetStatus()
    }

    private fun checkInternetConnection() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork

        val activeNetwork = connectivityManager.getNetworkCapabilities(network)

        if (network == null || activeNetwork ==null){
            viewModel.updateInternetStatus(false)
        }else{
            val status = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> true

                else -> false
            }
            viewModel.updateInternetStatus(status)
        }
    }

    private fun observeInternetStatus(){
        viewModel.internetConnectionStatusLD.observe(this){ internetConnectionStatus ->
            when(internetConnectionStatus){
                InternetConnectionStatus.CONNECTED ->{
                    checkNotificationPermissions()
                }
                InternetConnectionStatus.DISCONNECTED ->{
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
        if (prefs.getBoolean(SHOULD_REQUEST_NOTIFICATION_PERMISSIONS, true)) askNotificationPermission()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        prefs.edit().putBoolean(SHOULD_REQUEST_NOTIFICATION_PERMISSIONS, true).apply()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                showRequestPermissionDialog()
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                prefs.edit().putBoolean(SHOULD_REQUEST_NOTIFICATION_PERMISSIONS, true).apply()
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
//                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
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