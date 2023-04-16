package edu.quinnipiac.edu.ser210.exerciserapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    var selectedOption: String? = null
    var selectedOption2: String? = null
    var selectedOption3: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val searchButton = view.findViewById<Button>(R.id.search)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        setupSpinner(spinner) { option -> selectedOption = option }

        val spinner2 = view.findViewById<Spinner>(R.id.spinner2)
        setupSpinner(spinner2) { option -> selectedOption2 = option }

        val spinner3 = view.findViewById<Spinner>(R.id.spinner3)
        setupSpinner(spinner3) { option -> selectedOption3 = option }

        //onclick listener for the search button
        searchButton.setOnClickListener {
            //         CAMRYN---UNCOMMENT THE BELOW WHEN YOU'RE TRYING TO NAVIGATE FROM SEARCH TO RESULT
            //ALSO PLEASE TRY TO MAKE AN TOAST OR MESSAGE TO SHOW WHEN THERES NO RESULT FOR USER'S OPTIONS IN THE RESULT FRAGMENT

            val navController = view.findNavController()

            if (selectedOption == "No selection" && selectedOption2 == "No selection" && selectedOption3 == "No selection") {
                //if there are no selections, show a toast
                Toast.makeText(context, "Please enter at least one option to proceed", Toast.LENGTH_LONG).show()
            } else {
                val options = mutableMapOf<String, String>()
                addOptionToMap(selectedOption, "type", options)
                addOptionToMap(selectedOption2, "muscle", options)
                addOptionToMap(selectedOption3, "equipment", options)
                val apiInterface = options?.let { it1 -> APiInterface.create().getExercises(it1) }

                Toast.makeText(context, "Getting Response", Toast.LENGTH_SHORT).show()
                //somehow add options here
                val action = SearchFragmentDirections.actionSearchFragmentToResultFragment()
                view.findNavController().navigate(action)
            }
        }
        return view
    }

    private fun setupSpinner(spinner: Spinner, onItemSelected: (option: String?) -> Unit) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val option = parent?.getItemAtPosition(position).toString()
                onItemSelected(option)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun addOptionToMap(option: String?, key: String, options: MutableMap<String, String>) {
        if (option != null && option != "No selection") {
            options[key] = option
        }
    }
}
