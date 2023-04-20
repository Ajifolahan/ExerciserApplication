package edu.quinnipiac.edu.ser210.exerciserapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


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
        val favoriteButton = view.findViewById<Button>(R.id.favorites)

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        setupSpinner(spinner) { option -> selectedOption = option }

        val spinner2 = view.findViewById<Spinner>(R.id.spinner2)
        setupSpinner(spinner2) { option -> selectedOption2 = option }

        val spinner3 = view.findViewById<Spinner>(R.id.spinner3)
        setupSpinner(spinner3) { option -> selectedOption3 = option }

        //onClick listener for the search button
        favoriteButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_searchFragment_to_favoriteFragment)
        }

        //onclick listener for the search button
        searchButton.setOnClickListener {
            if (selectedOption == "No selection" && selectedOption2 == "No selection" && selectedOption3 == "No selection") {
                //if there are no selections, show a toast
                Toast.makeText(
                    context,
                    "Please enter at least one option to proceed",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val selection = selectedOption.toString()
                val selection2 = selectedOption2.toString()
                val selection3 = selectedOption3.toString()

                Toast.makeText(context, "Getting Response", Toast.LENGTH_SHORT).show()
                val action = SearchFragmentDirections.actionSearchFragmentToResultFragment(
                    selection,
                    selection2,
                    selection3
                )

                view.findNavController().navigate(action)
            }
        }
        return view
    }

    private fun setupSpinner(spinner: Spinner, onItemSelected: (option: String?) -> Unit) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val option = parent?.getItemAtPosition(position).toString()
                onItemSelected(option)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    override fun onDestroyView(){
        super.onDestroyView()
    }
}
