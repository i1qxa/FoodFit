package aps.foodfit.jyrbf.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.FragmentWelcomeBinding
import aps.foodfit.jyrbf.ui.RacionActivity
import aps.foodfit.jyrbf.ui.quiz.GenderFragment

const val FOOD_FIT_PREFS_NAME = "food_fit_prefs"
const val IS_FIRST_LAUNCH = "is_first_launch"
class WelcomeFragment : Fragment() {

    private val viewModel:WelcomeViewModel by viewModels()
    private val binding by lazy { FragmentWelcomeBinding.inflate(layoutInflater) }
    private val prefs by lazy { requireContext().getSharedPreferences(FOOD_FIT_PREFS_NAME, Context.MODE_PRIVATE) }
    private val isFirstLaunch by lazy { prefs.getBoolean(IS_FIRST_LAUNCH, true) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoadingProgress()
        setupBtnForwardBtnClickListener()
    }

    private fun setupBtnForwardBtnClickListener() {
        binding.btnForward.setOnClickListener {
            if (isFirstLaunch){
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.welcomeConteiner, GenderFragment())
                    commit()
                }
            }else{
                val intent = Intent(requireContext(), RacionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun observeLoadingProgress(){
        viewModel.loadingFinish.observe(viewLifecycleOwner){
            if (it){
                binding.pbLoading.visibility = View.GONE
                binding.btnForward.visibility = View.VISIBLE
            }
        }
    }

}