package aps.foodfit.jyrbf.ui.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentHeightBinding
import aps.foodfit.jyrbf.ui.welcome.FOOD_FIT_PREFS_NAME

class HeightFragment : Fragment() {
    private val binding by lazy { FragmentHeightBinding.inflate(layoutInflater) }
    private val prefs by lazy { requireContext().getSharedPreferences(FOOD_FIT_PREFS_NAME, Context.MODE_PRIVATE) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnForwardClickListener()
    }

    private fun setupBtnForwardClickListener(){
        binding.btnForward.setOnClickListener {
            val weight = binding.etHeight.text.toString()
            if (weight.isEmpty()){
                Toast.makeText(requireContext(),
                    getString(R.string.need_enter_height), Toast.LENGTH_SHORT).show()
            }else{
                prefs.edit().putString("height", weight).apply()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.welcomeConteiner, CaloriesFragment())
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}