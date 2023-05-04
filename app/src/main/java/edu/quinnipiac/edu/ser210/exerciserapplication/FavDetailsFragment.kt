//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.quinnipiac.edu.ser210.exerciserapplication.data.Workout
import edu.quinnipiac.edu.ser210.exerciserapplication.databinding.FragmentFavDetailsBinding

class FavDetailsFragment : Fragment() {
    private val navigationArgs: FavDetailsFragmentArgs by navArgs()
    lateinit var workout: Workout

    //creates connection to the FavoriteViewModel
    private val viewModel: FavoriteViewModel by activityViewModels {
        FavoriteViewModelFactory(
            (activity?.application as DetailsApplication).database.workoutDao()
        )
    }

    //sets up viewbinding
    private var _binding: FragmentFavDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_fav_details, container, false
        )
        return binding.root
    }


    //Binds views with the passed in item data.
    private fun bind(workout: Workout) {
        binding.apply {
            deleteButton.setOnClickListener { showConfirmationDialog()}
            name.text
            Glide.with(requireContext()).load(workout.imageURL)
                .apply(RequestOptions().centerCrop())
                .into(itemImage)
            type.text
            muscle.text
            equipment.text
            difficulty.text
            instruction.text
        }
    }


    //Displays an alert dialog to get the user's confirmation before deleting the item.
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete?")
            .setMessage("Do you want to delete this exercise from your favorites?")
            .setCancelable(false)
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                deleteItem()
            }
            .show()
    }


    //Deletes the current item and navigates to the list fragment.
    private fun deleteItem() {
        viewModel.deleteItem(workout)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.workoutId
        // Retrieve the item details using the itemId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            workout = selectedItem
            binding.workout = workout
            Glide.with(requireContext()).load(workout.imageURL)
                .apply(RequestOptions().centerCrop())
                .into(binding.itemImage)
            bind(workout)
        }
    }


    //Called when fragment is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

