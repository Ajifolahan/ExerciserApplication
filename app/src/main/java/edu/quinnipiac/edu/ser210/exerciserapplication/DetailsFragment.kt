//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import edu.quinnipiac.edu.ser210.exerciserapplication.data.Workout
import edu.quinnipiac.edu.ser210.exerciserapplication.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    //Creates connection to the DetailViewModel
    private val viewModel: DetailViewModel by activityViewModels {
        DetailViewModelFactory(
            (activity?.application as DetailsApplication).database.workoutDao()
        )
    }

    lateinit var item: Workout
    var imageURL: String? = null

    lateinit var requestQueue: RequestQueue
    var exercise_id: Int = 0

    private var _binding:FragmentDetailsBinding? = null
    private val binding get() = _binding !!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a Volley request queue
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(requireContext().cacheDir, 5* 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }

        // Get exercise ID from arguments bundle
        val bundle = arguments
        if (bundle == null) {
            Log.e("DetailFragment", "DetailFragment did not receive hero id")
            return
        }
        exercise_id = DetailsFragmentArgs.fromBundle(bundle).exerciseId
    }

    //adds item to database
    private fun addNewItem() {
        if (imageURL == null) {
            viewModel.addFavorite(
                binding.name.text.toString(),
                null,
                binding.type.text.toString(),
                binding.muscle.text.toString(),
                binding.equipment.text.toString(),
                binding.difficulty.text.toString(),
                binding.instruction.text.toString()
            )
        } else {
            viewModel.addFavorite(
                binding.name.text.toString(),
                imageURL!!,
                binding.type.text.toString(),
                binding.muscle.text.toString(),
                binding.equipment.text.toString(),
                binding.difficulty.text.toString(),
                binding.instruction.text.toString()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // use viewbinding to get data from the layout
        binding.name.text = exerciseList.get(exercise_id).name
        binding.type.text = exerciseList.get(exercise_id).type
        binding.muscle.text = exerciseList.get(exercise_id).muscle
        binding.equipment.text = exerciseList.get(exercise_id).equipment
        binding.difficulty.text = exerciseList.get(exercise_id).difficulty
        binding.instruction.text = exerciseList.get(exercise_id).instructions

        // Fetch an image of the recipe from the Google Custom Search API using Volley
       fetchData(exerciseList.get(exercise_id).name)

        //on click update the database
        binding.saveButton.setOnClickListener {
            Toast.makeText(context, "Added to Favorites List", Toast.LENGTH_SHORT).show()
            addNewItem()
        }
    }


    // Fetch an image of the recipe from the Google Custom Search API using Volley
    fun fetchData(input: String) {
        val url = "https://www.googleapis.com/customsearch/v1?q=$input+exercise&cx=222f6e80dbc7642dc&imgSize=medium&searchType=image&key=${BuildConfig.api_key2}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                if (response.getJSONArray("items").length() > 0) {
                    // Load the image into the ImageView using Glide
                   imageURL = response.getJSONArray("items").getJSONObject(0).getString("link")
                    Glide.with(requireContext()).load(imageURL)
                        .apply(RequestOptions().centerCrop())
                        .into(binding.itemImage)
                }
            },
            { error ->
                Log.d("vol", error.toString())
            }
        )

        // Add the Volley request to the queue
        requestQueue.add(jsonObjectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

}