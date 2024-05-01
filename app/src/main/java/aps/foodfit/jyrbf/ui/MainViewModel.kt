package aps.foodfit.jyrbf.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import aps.foodfit.jyrbf.domain.InternetConnectionStatus

class MainViewModel:ViewModel() {

    val internetConnectionStatusLD = MutableLiveData<InternetConnectionStatus>()

    fun updateInternetStatus(status:Boolean){
        val internetConnectionStatus = when(status){
            true -> InternetConnectionStatus.CONNECTED
            false -> InternetConnectionStatus.DISCONNECTED
        }
        internetConnectionStatusLD.value = internetConnectionStatus
    }

}