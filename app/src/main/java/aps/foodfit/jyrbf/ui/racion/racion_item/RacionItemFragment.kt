package aps.foodfit.jyrbf.ui.racion.racion_item

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentRacionItemBinding
import aps.foodfit.jyrbf.ui.racion.RACION_NAME
import aps.foodfit.jyrbf.ui.racion.racion_item.rv.RacionItemRVAdapter
import aps.foodfit.jyrbf.ui.recipe_list.recipe.RecipeFragment

const val RECIPE_URI = "recipe_uri"
class RacionItemFragment : Fragment() {

    private val viewModel: RacionItemViewModel by viewModels()
    private val binding by lazy { FragmentRacionItemBinding.inflate(layoutInflater) }
    private val rvAdapter = RacionItemRVAdapter()
    private val rv by lazy { binding.rvRacionItems }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            val racionName = bundle.getString(RACION_NAME)
            racionName?.let { racionName -> viewModel.setupRacionName(racionName) }
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
        setupRV()
        observeViewModel()
        setupBtnBackClickListener()
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
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