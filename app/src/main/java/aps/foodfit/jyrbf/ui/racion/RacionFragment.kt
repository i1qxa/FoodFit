package aps.foodfit.jyrbf.ui.racion

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentRacionBinding
import aps.foodfit.jyrbf.domain.launchNewFragment
import aps.foodfit.jyrbf.ui.new_racion.AddNewRacionFragment
import aps.foodfit.jyrbf.ui.new_racion.AddNewRacionViewModel
import aps.foodfit.jyrbf.ui.racion.rv.RacionRVAdapter
import aps.foodfit.jyrbf.ui.recipe_list.RecipeListFragment
import aps.foodfit.jyrbf.ui.recipe_list.recipe.RecipeFragment

class RacionFragment : Fragment() {

    private val viewModel: RacionViewModel by viewModels()
    private val addNewRacionVM:AddNewRacionViewModel by activityViewModels()
    private val binding by lazy { FragmentRacionBinding.inflate(layoutInflater) }
    private val rvAdapter = RacionRVAdapter()
    private val rv by lazy { binding.rvRacionList }

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
    }

    private fun observeViewModel(){
        viewModel.listOfRacion.observe(viewLifecycleOwner){
            rvAdapter.submitList(it)
        }
    }

    private fun setupBtnAddClickListener(){
        binding.btnAdd.setOnClickListener(){
            addNewRacionVM.resetState()
            parentFragmentManager.launchNewFragment(AddNewRacionFragment())
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

}