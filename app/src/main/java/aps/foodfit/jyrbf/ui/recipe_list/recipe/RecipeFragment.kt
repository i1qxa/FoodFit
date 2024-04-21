package aps.foodfit.jyrbf.ui.recipe_list.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentRecipeBinding
import aps.foodfit.jyrbf.ui.new_racion.recipe_search.STATE_ERROR
import aps.foodfit.jyrbf.ui.new_racion.recipe_search.STATE_LOADING
import aps.foodfit.jyrbf.ui.racion.racion_item.RECIPE_URI
import aps.foodfit.jyrbf.ui.recipe_list.recipe.rv.RicipeItemRVAdapter
import coil.load
import coil.transform.RoundedCornersTransformation

class RecipeFragment : Fragment() {


    private val viewModel: RecipeViewModel by viewModels()
    private val binding by lazy { FragmentRecipeBinding.inflate(layoutInflater) }
    private val rvAdapter = RicipeItemRVAdapter()
    private val rv by lazy { binding.rvIngredients }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            arguments?.getString(RECIPE_URI).let {
                it?.let { uri ->
                    viewModel.getRecipeList(uri)
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        setupRV()
        observeViewModel()
        setupBtnBackClickListener()
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupRV(){
        rv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }
    private fun observeViewModel(){
        viewModel.recipeItemLD.observe(viewLifecycleOwner){ recipeItem ->
            if (recipeItem!=null){
                with(binding){
                    tvRecipeName.text = recipeItem.label
                    tvKcal.text=getString(R.string.kcal_and_value, recipeItem.calories?.toInt())
                    tvCarb.text= getString (R.string.carb_and_value, recipeItem.carbPercent)
                    tvFat.text= getString(R.string.fat_and_value, recipeItem.fatPercent)
                    tvProtein.text= getString(R.string.protein_and_value, recipeItem.proteinPercent)
                    chartProtein.progress=recipeItem.proteinPercent
                    chartFat.progress=recipeItem.fatPercent
                    chartCarb.progress=recipeItem.carbPercent
                    ivRecipeLogo.load(recipeItem.image) {
                        transformations(RoundedCornersTransformation(20.0f))
                    }
                }
                rvAdapter.submitList(recipeItem.ingredients)
            }

        }
    }
    private fun observeState(){
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                STATE_LOADING -> {
                    changeDataVisibility(false)
                    binding.progressLoading.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                STATE_ERROR ->{
                    changeDataVisibility(false)
                    binding.progressLoading.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                }
                else ->{
                    changeDataVisibility(true)
                    binding.progressLoading.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
            }
        }
    }

    private fun changeDataVisibility(isVisible:Boolean){
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        with(binding){
            tvRecipeName.visibility = visibility
            tvCarb.visibility=visibility
            tvFat.visibility=visibility
            tvKcal.visibility=visibility
            tvProtein.visibility=visibility
            ivRecipeLogo.visibility=visibility
            chartProtein.visibility=visibility
            chartFat.visibility=visibility
            chartCarb.visibility=visibility
            rvIngredients.visibility=visibility
        }
    }

}