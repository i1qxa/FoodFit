package aps.foodfit.jyrbf.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentOnAttachListener
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.databinding.ActivityRacionBinding
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
        supportFragmentManager.addFragmentOnAttachListener(fragmentChangeListener)
    }
    private val fragmentChangeListener = FragmentOnAttachListener{ fragmentManager, fragment ->
        if (fragment is RacionFragment){
            binding.tvRecipe.visibility = View.INVISIBLE
            binding.racionIcon.animate().apply {
                duration = 300
                scaleX(1.2F)
                scaleY(1.2F)
                withEndAction{
                    binding.tvRacion.visibility = View.VISIBLE
                }
            }
            binding.recipeIcon.animate().apply {
                duration = 300
                scaleX(1F)
                scaleY(1F)
            }
        }else if (fragment is RecipeListFragment){
            binding.tvRacion.visibility = View.INVISIBLE
            binding.recipeIcon.animate().apply {
                duration = 300
                scaleX(1.2F)
                scaleY(1.2F)
                withEndAction{
                    binding.tvRecipe.visibility = View.VISIBLE
                }
            }
            binding.racionIcon.animate().apply {
                duration = 300
                scaleX(1F)
                scaleY(1F)
            }
        }
    }
}