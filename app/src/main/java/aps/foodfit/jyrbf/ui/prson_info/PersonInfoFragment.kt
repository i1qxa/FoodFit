package aps.foodfit.jyrbf.ui.prson_info

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentPersonInfoBinding

class PersonInfoFragment : Fragment() {

    private val viewModel: PersonInfoViewModel by viewModels()
    private val binding by lazy { FragmentPersonInfoBinding.inflate(layoutInflater) }
    private val imm by lazy { requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.dataLD.observe(viewLifecycleOwner){
            with(binding){
                tvWeightValue.text = getString(R.string.weight_kg, it.weight)
                tvHeightValue.text = getString(R.string.height_sm, it.height)
                tvEnergyValue.text = getString(R.string.energy_kcal, it.calories)
                etWeightValue.setText(it.weight)
                etHeightValue.setText(it.height)
                etEnergyValue.setText(it.calories)
            }
        }
        viewModel.stateLD.observe(viewLifecycleOwner){ state ->
            if (state.weightState){
                binding.tvWeightValue.visibility = View.GONE
                binding.etWeightValue.visibility = View.VISIBLE
                setFocus(binding.etWeightValue)
                binding.btnEditWeight.setImageResource(R.drawable.save)
                binding.btnEditWeight.setOnClickListener {
                    viewModel.updateData(WEIGHT_PREFS, binding.etWeightValue.text.toString().ifEmpty { "0" })
                    hideKeyboard(binding.etWeightValue)
                    viewModel.updateState(WEIGHT_PREFS)
                }
            }else{
                binding.tvWeightValue.visibility = View.VISIBLE
                binding.etWeightValue.visibility = View.GONE
                binding.btnEditWeight.setImageResource(R.drawable.edit)
                binding.btnEditWeight.setOnClickListener {
                    viewModel.updateState(WEIGHT_PREFS)
                }
            }

            if (state.heightState){
                binding.tvHeightValue.visibility = View.GONE
                binding.etHeightValue.visibility = View.VISIBLE
                setFocus(binding.etHeightValue)
                binding.btnEditHeight.setImageResource(R.drawable.save)
                binding.btnEditHeight.setOnClickListener {
                    viewModel.updateData(HEIGHT_PREFS, binding.etHeightValue.text.toString().ifEmpty { "0" })
                    hideKeyboard(binding.etHeightValue)
                    viewModel.updateState(HEIGHT_PREFS)
                }
            }else{
                binding.tvHeightValue.visibility = View.VISIBLE
                binding.etHeightValue.visibility = View.GONE
                binding.btnEditHeight.setImageResource(R.drawable.edit)
                binding.btnEditHeight.setOnClickListener {
                    viewModel.updateState(HEIGHT_PREFS)
                }
            }

            if (state.energyState){
                binding.tvEnergyValue.visibility = View.GONE
                binding.etEnergyValue.visibility = View.VISIBLE
                setFocus(binding.etEnergyValue)
                binding.btnEditEnergy.setImageResource(R.drawable.save)
                binding.btnEditEnergy.setOnClickListener {
                    viewModel.updateData(CALORIES_PREFS, binding.etEnergyValue.text.toString().ifEmpty { "0" })
                    hideKeyboard(binding.etEnergyValue)
                    viewModel.updateState(CALORIES_PREFS)
                }
            }else{
                binding.tvEnergyValue.visibility = View.VISIBLE
                binding.etEnergyValue.visibility = View.GONE
                binding.btnEditEnergy.setImageResource(R.drawable.edit)
                binding.btnEditEnergy.setOnClickListener {
                    viewModel.updateState(CALORIES_PREFS)
                }
            }
        }
    }

    private fun setFocus(view:EditText){
        view.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard(view: EditText){
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}