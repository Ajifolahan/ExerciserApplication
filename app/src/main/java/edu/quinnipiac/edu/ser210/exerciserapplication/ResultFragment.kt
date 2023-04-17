package edu.quinnipiac.edu.ser210.exerciserapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    var selectedOption = ""
    var selectedOption2 = ""
    var selectedOption3 = ""

    // Retrieve the message passed from the previous fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("ResultFragment", "ResultFragment did not receive exercise id")
            return
        }
        selectedOption = ResultFragmentArgs.fromBundle(bundle).selection
        selectedOption2 = ResultFragmentArgs.fromBundle(bundle).selection2
        selectedOption3 = ResultFragmentArgs.fromBundle(bundle).selection3

    }

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the recycler view and adapter
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(requireContext(), Navigation.findNavController(view))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter

        val options = mutableMapOf<String, String>()
        addOptionToMap(selectedOption, "type", options)
        addOptionToMap(selectedOption2, "muscle", options)
        addOptionToMap(selectedOption3, "difficulty", options)

        val apiInterface = APiInterface.create().getExercises(options)
        if (apiInterface != null) {
            apiInterface.enqueue(object : Callback<ArrayList<ExerciseItem?>?>{
                override fun onResponse(
                    call: Call<ArrayList<ExerciseItem?>?>,
                    response: Response<ArrayList<ExerciseItem?>?>
                ) {
                    // Set the retrieved list of exercises to the adapter
                    if (response?.body() != null) {
                        recyclerAdapter.setExerciseListItems(response?.body() !! as ArrayList<ExerciseItem>)
                        if(recyclerAdapter.itemCount == 0) {
                            Toast.makeText(context, "There are no results for your search.\nPlease go back and try again", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<ExerciseItem?>?>, t: Throwable) {
                    // Handle the case where the API call fails
                    if (t != null)
                        t.message?.let {Log.d("onFailure", it) }
                }

            })
        }



    }

    private fun addOptionToMap(option: String?, key: String, options: MutableMap<String, String>) {
        if (option != null && option != "No selection") {
            options[key] = option
        }
    }

}