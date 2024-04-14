package aps.foodfit.jyrbf.ui.racion

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentRacionBinding
import aps.foodfit.jyrbf.domain.launchNewFragment
import aps.foodfit.jyrbf.ui.new_racion.AddNewRacionFragment
import aps.foodfit.jyrbf.ui.recipe_list.RecipeListFragment
import aps.foodfit.jyrbf.ui.recipe_list.recipe.RecipeFragment

class RacionFragment : Fragment() {

    private val viewModel: RacionViewModel by viewModels()
    private val binding by lazy { FragmentRacionBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBtnAddClickListener()
    }

    private fun setupBtnAddClickListener(){
        binding.btnAdd.setOnClickListener(){
            parentFragmentManager.launchNewFragment(AddNewRacionFragment())
        }
    }

}