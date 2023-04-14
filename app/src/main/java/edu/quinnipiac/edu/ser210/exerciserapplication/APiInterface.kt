//@Authors: Camryn Keller and Momoreoluwa Ayinde

package edu.quinnipiac.edu.ser210.exerciserapplication

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface APiInterface {

    // GET endpoint for retrieving recipes
    @Headers(BuildConfig.api_key, "X-RapidAPI-Host:exercises-by-api-ninjas.p.rapidapi.com")
    @GET("/v1/exercises")
    fun getExercises(
        @QueryMap options: Map<String, String>) : Call<ArrayList<ExerciseItem?>?>?

    companion object {

        // base URL for the REST API
        var BASE_URL = "https://exercises-by-api-ninjas.p.rapidapi.com"

        // method for creating instances of the interface using Retrofit
        fun create() : APiInterface {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            // Retrofit instance with the Gson converter factory and the OkHttpClient
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .build()

            // Returns an instance of the interface using the Retrofit instance
            return retrofit.create(APiInterface::class.java)

        }
    }
}