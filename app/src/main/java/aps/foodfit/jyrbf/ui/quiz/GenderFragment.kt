package aps.foodfit.jyrbf.ui.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentGenderBinding
import aps.foodfit.jyrbf.ui.welcome.FOOD_FIT_PREFS_NAME

class GenderFragment : Fragment() {

    private val viewModel:GenderViewModel by viewModels()
    private val binding by lazy { FragmentGenderBinding.inflate(layoutInflater) }
    private var selectedGender = 0
    private val prefs by lazy { requireContext().getSharedPreferences(FOOD_FIT_PREFS_NAME, Context.MODE_PRIVATE) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnClickListeners()
        observeViewModel()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeViewModel(){
        viewModel.selectedGender.observe(viewLifecycleOwner){
            selectedGender = it
            if (it == MALE){
                binding.tvMale.background = requireContext().getDrawable(R.drawable.btn_background)
                binding.tvFemale.background = requireContext().getDrawable(R.drawable.btn_inactive_background)
                binding.maleIcon.animate().apply {
                    duration = 300
                    scaleX(1.3F)
                    scaleY(1.3F)
                }
                binding.femaleIcon.animate().apply {
                    duration = 300
                    scaleX(1F)
                    scaleY(1F)
                }
            }else if (it == FEMALE){
                binding.tvFemale.background = requireContext().getDrawable(R.drawable.btn_background)
                binding.tvMale.background = requireContext().getDrawable(R.drawable.btn_inactive_background)
                binding.femaleIcon.animate().apply {
                    duration = 300
                    scaleX(1.3F)
                    scaleY(1.3F)
                }
                binding.maleIcon.animate().apply {
                    duration = 300
                    scaleX(1F)
                    scaleY(1F)
                }
            }
        }
    }

    private fun setupBtnClickListeners() {
        binding.tvMale.setOnClickListener {
            viewModel.selectGender(MALE)
        }
        binding.tvFemale.setOnClickListener {
            viewModel.selectGender(FEMALE)
        }
        binding.btnForward.setOnClickListener {
            if (selectedGender == 0){
                Toast.makeText(requireContext(), getString(R.string.first_choose_gender), Toast.LENGTH_SHORT).show()
            }else{
                prefs.edit().putInt("gender", selectedGender).apply()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.welcomeConteiner, WeightFragment())
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}