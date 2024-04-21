package aps.foodfit.jyrbf.ui.new_racion.recipe_search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.data.remote.RecipeItemShort
import aps.foodfit.jyrbf.databinding.FragmentRecipeSearchBinding
import aps.foodfit.jyrbf.databinding.InputWeightDialogBinding
import aps.foodfit.jyrbf.ui.new_racion.AddNewRacionViewModel
import aps.foodfit.jyrbf.ui.new_racion.recipe_search.rv.FoodRVAdapter

private const val VISIBLE = View.VISIBLE
private const val GONE = View.GONE
class RecipeSearchFragment : Fragment() {

    private val viewModel: RecipeSearchViewModel by viewModels()
    private val addViewModel: AddNewRacionViewModel by activityViewModels()
    private val binding by lazy { FragmentRecipeSearchBinding.inflate(layoutInflater) }
    private val rvAdapter = FoodRVAdapter()
    private val recipeRV by lazy { binding.rvRecipeList }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnSearchClickListener()
        setupRV()
        observeViewModel()
        setupBtnBack()
    }

    private fun setupBtnBack(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.recipeList.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                rvAdapter.submitList(it)
            }
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                STATE_SUCCESS -> changeVisibility(VISIBLE, GONE, GONE, GONE)
                STATE_ERROR -> changeVisibility(GONE, GONE, VISIBLE, GONE)
                STATE_LOADING -> changeVisibility(GONE, GONE, GONE, VISIBLE)
                else -> changeVisibility(GONE, VISIBLE, GONE, GONE)
            }
        }
    }

    private fun changeVisibility(
        rvVisibility: Int,
        emptyListVisibility: Int,
        errorVisibility: Int,
        progressVisibility: Int
    ) {
        with(binding) {
            recipeRV.visibility = rvVisibility
            tvEmptyList.visibility = emptyListVisibility
            tvErrorRequest.visibility = errorVisibility
            pbLoading.visibility = progressVisibility
        }
    }

    private fun setupRV() {
        setupRVAdapter()
        with(recipeRV) {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    private fun setupRVAdapter(){
        rvAdapter.onItemClickListener = { recipeItemShort ->
            showInputWeightDialog(recipeItemShort)
        }
    }

    private fun setupBtnSearchClickListener() {
        binding.btnSearch.setOnClickListener {
            val searchQuery = binding.etRecipeSearch.text.toString()
            if (searchQuery.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.recipe_name_is_empty),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.translateQuery(searchQuery)
            }
        }
    }

    private fun showInputWeightDialog(foodItem: RecipeItemShort) {
        val dialogBinding = InputWeightDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        dialog.show()
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {
            val weightStr = dialogBinding.etWeight.text.toString().ifEmpty { "100" }
            val weight = weightStr.toInt()
            addViewModel.addRecipeItem(foodItem, weight)
            Toast.makeText(requireContext(),getString(R.string.toast_new_recipe, foodItem.label),
                Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }
}