package aps.foodfit.jyrbf.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val APP_ID = "691af888"
const val APP_KEY = "d10b738bb995f60e7a8bbec9fd83d020"
private const val BASE_URL = "https://api.edamam.com/api/recipes/v2/"
interface RetrofitService {

    @GET("?")
    suspend fun getRecipeResponse(
        @Query("type") type:String,
        @Query("q") food:String,
        @Query("app_id") appId:String,
        @Query("app_key") appKey:String)
    : Response<RecipeResponseBody>

    companion object {

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(createLoggingInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        }

        private fun createLoggingInterceptor(): Interceptor {
            return HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        }


        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(createOkHttpClient())
                    .addConverterFactory(Json{
                        ignoreUnknownKeys = true
                        allowSpecialFloatingPointValues = true
                    }.asConverterFactory("application/json".toMediaType()))
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
