package aps.foodfit.jyrbf.ui.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val MY_DELAY:Long = 3000
class WelcomeViewModel:ViewModel() {

    val loadingFinish = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            delay(MY_DELAY)
            loadingFinish.postValue(true)
        }
    }

}