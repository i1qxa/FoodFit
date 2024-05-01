package aps.foodfit.jyrbf.data.local.recipe

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import aps.foodfit.jyrbf.domain.Racion

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListOfRecipeItems(listOfRecipeItems:List<RecipeDB>)

    @Query("SELECT racionName as name, count(*) as recipeAmount, imgRemote, imgLocal, SUM(totalTimeInMinutes) as totalTime," +
            "SUM(weightInGrams) as totalWeight, SUM(kcalPerGram*weightInGrams) as totalKcal," +
            "SUM(proteinPerGram*weightInGrams) as totalProtein," +
            "SUM(fatPerGram*weightInGrams) as totalFat," +
            "SUM(carbPerGram*weightInGrams) as totalCarb, isPrePopulate FROM recipedb GROUP BY racionName")
    fun getListOfRacion():LiveData<List<Racion>>

    @Query("SELECT count(*) FROM RECIPEDB")
    suspend fun getAmountOfRecipes():Int

    @Query("SELECT * FROM RecipeDB WHERE racionName=:racionName")
    fun getRacion(racionName:String):LiveData<List<RecipeDB>>

    @Query("SELECT * FROM RecipeDB WHERE name LIKE :recipeName")
    fun getRecipeListByName(recipeName:String):LiveData<List<RecipeDB>>

    @Query("SELECT * FROM RecipeDB")
    fun getAllRecipes():LiveData<List<RecipeDB>>


    @Query("SELECT * FROM RecipeDB WHERE id=:recipeId")
    fun getRecipeItem(recipeId:Int):LiveData<RecipeDB>

}