//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import androidx.databinding.Bindable
import androidx.lifecycle.*
import edu.quinnipiac.edu.ser210.exerciserapplication.data.Workout
import edu.quinnipiac.edu.ser210.exerciserapplication.data.WorkoutDao
import kotlinx.coroutines.launch

class FavoriteViewModel(val dao: WorkoutDao): ViewModel() {

    // Cache all items form the database using LiveData.
     val allItems: LiveData<List<Workout>> = dao.getAll().asLiveData()


    // Launching a new coroutine to delete an item in a non-blocking way
    fun deleteItem(item: Workout) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }

     //Retrieve an item from the repository.
    fun retrieveItem(id: Int): LiveData<Workout> {
        return dao.getItem(id).asLiveData()
    }

}

class FavoriteViewModelFactory(private val dao: WorkoutDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

