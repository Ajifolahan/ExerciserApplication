package edu.quinnipiac.edu.ser210.exerciserapplication

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: WorkoutDao): ViewModel() {

    // Cache all items form the database using LiveData.
   val allItems: LiveData<List<Workout>> = dao.getAll().asLiveData()

    /**
     * Inserts the new Item into database.
     */
    fun addFavorite(
        titleName: String, typeName: String, muscleName: String, equipmentName: String,
        difficultyName: String, instructionsName: String) {
        val newItem = getNewItemEntry(titleName, typeName, muscleName, equipmentName, difficultyName, instructionsName)
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(item: Workout) {
        viewModelScope.launch {
            dao.insert(item)
        }
    }

    private fun getNewItemEntry(titleName: String, typeName: String,muscleName: String, equipmentName: String,
                                difficultyName: String, instructionsName: String): Workout {
        return Workout(
            title = titleName,
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

//    fun viewItem(item: Workout) {
//        viewModelScope.launch {
//            val workoutList = allItems
//            workoutList.collect {
//                Log.d(
//                    "title",
//                    "title: ${item.title}"
//                )
//            }
//        }
//    }
}






class DetailViewModelFactory(private val dao: WorkoutDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}