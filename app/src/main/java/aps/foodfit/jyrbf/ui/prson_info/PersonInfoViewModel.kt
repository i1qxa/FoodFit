package aps.foodfit.jyrbf.ui.prson_info

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import aps.foodfit.jyrbf.ui.welcome.FOOD_FIT_PREFS_NAME

const val WEIGHT_PREFS = "weight"
const val HEIGHT_PREFS = "height"
const val CALORIES_PREFS = "calories"
class PersonInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val context by lazy { application.baseContext }
    private val prefs by lazy { context.getSharedPreferences(FOOD_FIT_PREFS_NAME, Context.MODE_PRIVATE)}

    private var currentData = PersonInfo("0","0","0")
    val dataLD = MutableLiveData<PersonInfo>()
    private var currentState = PersonInfoState(false, false, false)
    val stateLD = MutableLiveData<PersonInfoState>(currentState)
    init {
        val initData = PersonInfo(
            prefs.getString(WEIGHT_PREFS, "0")?:"0",
            prefs.getString(HEIGHT_PREFS, "0")?:"0",
            prefs.getString(CALORIES_PREFS, "0")?:"0",
        )
        currentData = initData
        dataLD.value = currentData
    }

    fun updateData(prefsName:String, prefsValue:String){
        updatePrefs(prefsName, prefsValue)
        val newPersonInfo = when(prefsName){
            WEIGHT_PREFS ->{
                currentData.copy(weight = prefsValue)
            }
            HEIGHT_PREFS ->{
                currentData.copy(height = prefsValue)
            }
            else ->{
                currentData.copy(calories =  prefsValue)
            }
        }
        currentData = newPersonInfo
        dataLD.value = currentData
    }

    private fun updatePrefs(prefsName:String, prefsValue:String){
        prefs.edit().putString(prefsName, prefsValue).apply()
    }

    fun updateState(cardClicked:String){
        val newState = when(cardClicked){
            WEIGHT_PREFS -> {
                currentState.copy(weightState = !currentState.weightState, heightState = false, energyState = false)
            }
            HEIGHT_PREFS -> {
                currentState.copy(heightState = !currentState.heightState, weightState = false, energyState = false)
            }
            else ->{
                currentState.copy(energyState = !currentState.energyState, weightState = false, heightState = false)
            }
        }
        currentState = newState
        stateLD.value = currentState
    }

}