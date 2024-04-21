package aps.foodfit.jyrbf.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentOnAttachListener
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.ActivityRacionBinding
import aps.foodfit.jyrbf.domain.launchNewFragment
import aps.foodfit.jyrbf.ui.racion.RacionFragment
import aps.foodfit.jyrbf.ui.recipe_list.RecipeListFragment
import aps.foodfit.jyrbf.ui.recipe_list.recipe.RecipeFragment

class RacionActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRacionBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportFragmentManager.addFragmentOnAttachListener(fragmentChangeListener)
        setupBottomNavClickListeners()
    }

    private fun setupBottomNavClickListeners(){
        binding.racionIcon.setOnClickListener {
            supportFragmentManager.launchNewFragment(RacionFragment())
        }
        binding.recipeIcon.setOnClickListener {
            supportFragmentManager.launchNewFragment(RecipeListFragment())
        }
    }

    private val fragmentChangeListener = FragmentOnAttachListener { fragmentManager, fragment ->
        if (fragment is RecipeListFragment || fragment is RecipeFragment) {
            binding.tvRacion.visibility = View.INVISIBLE
            binding.recipeIcon.animate().apply {
                duration = 300
                scaleX(1.2F)
                scaleY(1.2F)
                withEndAction {
                    binding.tvRecipe.visibility = View.VISIBLE
                }
            }
            binding.racionIcon.animate().apply {
                duration = 300
                scaleX(1F)
                scaleY(1F)
            }
        } else {
            binding.tvRecipe.visibility = View.INVISIBLE
            binding.racionIcon.animate().apply {
                duration = 300
                scaleX(1.2F)
                scaleY(1.2F)
                withEndAction {
                    binding.tvRacion.visibility = View.VISIBLE
                }
            }
            binding.recipeIcon.animate().apply {
                duration = 300
                scaleX(1F)
                scaleY(1F)
            }
        }
    }
}