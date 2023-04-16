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

    // Retrieve the message passed from the previous fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("ResultFragment", "ResultFragment did not receive exercise id")
            return
        }
        //set variable for option mutable app
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

        // Call the API to retrieve the list of recipes based on the user's search input
        //val apiInterface = options?.let { it1 -> APiInterface.create().getExercises(it1) }
        /*if (apiInterface != null) {
            apiInterface.enqueue(object : Callback<ArrayList<ExerciseItem?>?>{
                override fun onResponse(
                    call: Call<ArrayList<ExerciseItem?>?>,
                    response: Response<ArrayList<ExerciseItem?>?>
                ) {
                    // Set the retrieved list of recipes to the adapter
                    if (response?.body() != null) {
                        recyclerAdapter.setExerciseListItems(response?.body() !! as ArrayList<ExerciseItem>)
                    }
                }

                override fun onFailure(call: Call<ArrayList<ExerciseItem?>?>, t: Throwable) {
                    // Handle the case where the API call fails
                    if (t != null)
                        t.message?.let {Log.d("onFailure", it) }
                }

            })
        }*/

    }

}