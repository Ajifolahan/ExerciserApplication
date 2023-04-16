//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// Create an empty ArrayList to hold recipe items
var exerciseList : ArrayList<ExerciseItem> = ArrayList()

class RecyclerAdapter(val context: Context, var navController: NavController) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    // Declare a RequestQueue object to handle HTTP requests using Volley library
    lateinit var requestQueue: RequestQueue

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new RequestQueue instance using the application's cache and network
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(context.cacheDir, 10* 1024 * 1024) // 10MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }

        // Inflate the layout for the view holder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view, context)
    }

    // This function returns the size of the recipe list
    override fun getItemCount(): Int {
        return exerciseList.size
    }

    // This function binds the data to the view holder's UI elements
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    // This function sets the exercise list to the given list and updates the UI
    fun setExerciseListItems(recipe: ArrayList<ExerciseItem>) {
        exerciseList = recipe
        notifyDataSetChanged()
    }

    // Define the view holder class
    inner class MyViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        // Declare the UI elements used in this view holder
        private val title: TextView = itemView!!.findViewById(R.id.item_title)
        val image: ImageView = itemView!!.findViewById(R.id.item_image)
        private var pos: Int = 0

        // Set an on-click listener to navigate to the DetailsFragment
        init {
            itemView.setOnClickListener {
                val action = ResultFragmentDirections.actionResultFragmentToDetailsFragment(pos)
                navController.navigate(action)
            }
        }

        // Bind the data at the given position to the UI elements
        fun bind(position: Int) {
            pos = position
            val currExercise = exerciseList.get(position)
            title.text = currExercise.name

            // Convert the title into a query string by replacing spaces with '+'
            val words = title.text.split("\\s+")
            val result = words.joinToString("+")

            // Fetch the image for the recipe using Google Custom Search API
            //fetchData(result)
        }

        // This function fetches the image for the given query string using Google Custom Search API
        /*fun fetchData(input: String) {
            // Construct the URL for the API request
            val url = "https://www.googleapis.com/customsearch/v1?q=$input+recipe&cx=222f6e80dbc7642dc&imgSize=medium&searchType=image&key=${BuildConfig.api_key2}"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    if (response.getJSONArray("items").length() > 0) {
                        val imageURL = response.getJSONArray("items").getJSONObject(0).getString("link")
                        Glide.with(context).load(imageURL)
                            .apply(RequestOptions().centerCrop())
                            .into(image)
                    }
                },
                { error ->
                    Log.d("API error", error.toString())
                }
            )

            requestQueue.add(jsonObjectRequest)
        }*/
    }
}