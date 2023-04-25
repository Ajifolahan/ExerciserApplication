package edu.quinnipiac.edu.ser210.exerciserapplication

import androidx.lifecycle.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoriteViewModel(val dao: WorkoutDao): ViewModel() {

    // Cache all items form the database using LiveData.
    val allItems: LiveData<List<Workout>> = dao.getAll().asLiveData()

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(item: Workout) {
        viewModelScope.launch {
            dao.insert(item)
        }
    }

    private fun getNewItemEntry(titleName: String, imageURL: String,typeName: String,muscleName: String, equipmentName: String,
                                difficultyName: String, instructionsName: String): Workout {
        return Workout(
            title = titleName,
            imageURL = imageURL,
            type = typeName,
            muscle = muscleName,
            equipment = equipmentName,
            difficulty = difficultyName,
            instructions = instructionsName
        )
    }

    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
    fun deleteItem(item: Workout) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }

    /**
     * Retrieve an item from the repository.
     */
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

