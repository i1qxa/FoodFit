package aps.foodfit.jyrbf.ui.new_racion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.foodfit.jyrbf.databinding.FragmentAddNewRacionBinding
import aps.foodfit.jyrbf.domain.launchNewFragment
import aps.foodfit.jyrbf.ui.new_racion.recipe_search.RecipeSearchFragment
import aps.foodfit.jyrbf.ui.new_racion.rv.FoodRVAdapter

class AddNewRacionFragment : Fragment() {

    private val viewModel: AddNewRacionViewModel by activityViewModels()
    private val binding by lazy { FragmentAddNewRacionBinding.inflate(layoutInflater) }
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
        setupBtnAddClickListener()
        setupRV()
        observeViewModel()
        setupBtnSaveClickListener()
    }

    private fun observeViewModel(){
        viewModel.recipeListLD.observe(viewLifecycleOwner){
            recipeRV.visibility = View.VISIBLE
            rvAdapter.submitList(it)
        }
    }

    private fun setupBtnAddClickListener(){
        TODO("Нужно сделать нормальное сохранение рецепта со всеми проверками")
        binding.btnAdd.setOnClickListener(){
            parentFragmentManager.launchNewFragment(RecipeSearchFragment())
        }
    }

    private fun setupRV(){
        with(recipeRV){
            adapter = rvAdapter
            layoutManager=LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    private fun setupBtnSaveClickListener(){
        binding.btnSave.setOnClickListener {
            viewModel.saveRacion(binding.etRacionName.text.toString())
        }
    }

}