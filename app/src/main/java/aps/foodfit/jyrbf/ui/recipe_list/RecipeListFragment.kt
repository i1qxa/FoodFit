package aps.foodfit.jyrbf.ui.recipe_list

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentRecipeListBinding
import aps.foodfit.jyrbf.ui.racion.RacionFragment
import aps.foodfit.jyrbf.ui.recipe_list.recipe.RecipeFragment

class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()
    private val binding by lazy { FragmentRecipeListBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.someBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.foodConteiner, RacionFragment())
                commit()
            }
        }
    }
}