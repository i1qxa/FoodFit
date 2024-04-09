package aps.foodfit.jyrbf.ui.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val MALE = 1
const val FEMALE = 2
class GenderViewModel:ViewModel() {

    val selectedGender = MutableLiveData<Int>(0)

    fun selectGender(gender:Int){
        selectedGender.value = gender
    }

}
