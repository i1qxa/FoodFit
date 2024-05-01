package aps.foodfit.jyrbf.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentRecipeListBinding
import aps.foodfit.jyrbf.ui.racion.racion_item.RECIPE_URI
import aps.foodfit.jyrbf.ui.racion.racion_item.rv.RacionItemRVAdapter
import aps.foodfit.jyrbf.ui.recipe_list.recipe.RecipeFragment

class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()
    private val binding by lazy { FragmentRecipeListBinding.inflate(layoutInflater) }
    private val rvAdapter = RacionItemRVAdapter()
    private val rv by lazy { binding.rvRecipeList }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        observeViewModel()
        setupTextWatcher()
    }


    private fun setupTextWatcher(){
        binding.btnClearInput.setOnClickListener {
            binding.etRecipeSearch.setText("")
        }
        binding.etRecipeSearch.doOnTextChanged{ text, _, _, _ ->
            viewModel.setupTextForSearch(text.toString())
        }
    }

    private fun observeViewModel(){
        viewModel.recipeList.observe(viewLifecycleOwner){
            rvAdapter.submitList(it)
        }
    }

    private fun setupAdapter(){
        rvAdapter.onItemClickListener ={ uri ->
            val recipeArgs = Bundle().apply {
                putString(RECIPE_URI, uri)
            }
            val recipeFragment = RecipeFragment()
            recipeFragment.arguments = recipeArgs
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.foodConteiner, recipeFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun setupRV(){
        setupAdapter()
        rv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }
}