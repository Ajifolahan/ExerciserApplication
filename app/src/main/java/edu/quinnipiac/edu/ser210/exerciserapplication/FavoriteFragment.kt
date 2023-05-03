//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.quinnipiac.edu.ser210.exerciserapplication.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    //Creates connection to the DetailViewModel
    private val viewModel: FavoriteViewModel by activityViewModels {
        FavoriteViewModelFactory(
            (activity?.application as DetailsApplication).database.workoutDao()
        )
    }

    //Viewbinding
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Creates a variable for the adapter
        //Gives FavoriteAdapter onItemClicked variable
        val adapter = FavoriteAdapter {
            val action =
                FavoriteFragmentDirections.actionFavoriteFragmentToFavDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerview.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

    }
}