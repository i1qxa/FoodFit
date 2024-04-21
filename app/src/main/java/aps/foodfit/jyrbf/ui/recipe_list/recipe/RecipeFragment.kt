package aps.foodfit.jyrbf.ui.recipe_list.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import aps.foodfit.jyrbf.databinding.FragmentRecipeBinding
import aps.foodfit.jyrbf.ui.racion.racion_item.RECIPE_URI

class RecipeFragment : Fragment() {


    private val viewModel: RecipeViewModel by viewModels()
    private val binding by lazy { FragmentRecipeBinding.inflate(layoutInflater) }
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
        viewModel.recipeItem.observe(viewLifecycleOwner){
            val c = it
            val b =c
        }
    }
}