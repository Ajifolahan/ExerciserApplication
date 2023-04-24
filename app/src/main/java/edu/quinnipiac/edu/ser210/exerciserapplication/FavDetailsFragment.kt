package edu.quinnipiac.edu.ser210.exerciserapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.quinnipiac.edu.ser210.exerciserapplication.databinding.FragmentFavDetailsBinding

class FavDetailsFragment : Fragment() {
    private val navigationArgs: FavDetailsFragmentArgs by navArgs()
    lateinit var workout: Workout

    private val viewModel: FavoriteViewModel by activityViewModels {
        FavoriteViewModelFactory(
            (activity?.application as DetailsApplication).database.workoutDao()
        )
    }

    private var _binding: FragmentFavDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in item data.
     */
    private fun bind(workout: Workout) {
        binding.apply {
            deleteButton.setOnClickListener { showConfirmationDialog()}
            name.text = workout.title
            type.text = workout.type
            muscle.text = workout.muscle
            equipment.text = workout.equipment
            difficulty.text = workout.difficulty
            instruction.text = workout.instructions
        }
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete?")
            .setMessage("Do you want to delete this exercise from your favorites?")
            .setCancelable(false)
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YEs") { _, _ ->
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
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
            bind(workout)
        }
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

