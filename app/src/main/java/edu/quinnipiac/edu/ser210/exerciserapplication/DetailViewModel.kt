//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: WorkoutDao): ViewModel() {

     //Inserts the new Item into database.
    fun addFavorite(
        titleName: String, typeName: String,imageURL: String, muscleName: String, equipmentName: String,
        difficultyName: String, instructionsName: String) {
        val newItem = getNewItemEntry(titleName,imageURL, typeName, muscleName, equipmentName, difficultyName, instructionsName)
        insertItem(newItem)
    }

     //Launching a new coroutine to insert an item in a non-blocking way
    private fun insertItem(item: Workout) {
        viewModelScope.launch {
            dao.insert(item)
        }
    }

    //Gets an item from the database
    private fun getNewItemEntry(titleName: String, typeName: String,imageURL:String, muscleName: String, equipmentName: String,
                                difficultyName: String, instructionsName: String): Workout {
        return Workout(
            title = titleName,
            type = typeName,
            imageURL = imageURL,
            muscle = muscleName,
            equipment = equipmentName,
            difficulty = difficultyName,
            instructions = instructionsName
        )
    }

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