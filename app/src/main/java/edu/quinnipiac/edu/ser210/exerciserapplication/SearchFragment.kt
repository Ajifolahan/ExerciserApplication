package edu.quinnipiac.edu.ser210.exerciserapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val searchButton = view.findViewById<Button>(R.id.search)

        //onclick listener for the start button
        searchButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_searchFragment_to_resultFragment)
        }
        return view
    }
}