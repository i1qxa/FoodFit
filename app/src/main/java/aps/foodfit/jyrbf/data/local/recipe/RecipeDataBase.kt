package aps.foodfit.jyrbf.data.local.recipe

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [
        RecipeDB::class
    ],
    exportSchema = false,
    version = 1
)
abstract class RecipeDataBase:RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    companion object {
        private var INSTANCE: RecipeDataBase? = null
        private val LOCK = Any()
        private const val DB_NAME = "recipe_db"

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(application: Application): RecipeDataBase {
            INSTANCE?.let {
                return it
            }
            kotlinx.coroutines.internal.synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    RecipeDataBase::class.java,
                    DB_NAME
                )
//                    .createFromAsset("fitness_up.db")
                    .build()
                INSTANCE = db
                return db
            }
        }
    }


}