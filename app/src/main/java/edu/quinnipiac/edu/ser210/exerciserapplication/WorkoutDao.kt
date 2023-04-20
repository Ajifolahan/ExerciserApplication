package edu.quinnipiac.edu.ser210.exerciserapplication

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insert(workout:Workout)

    @Delete
    suspend fun delete(workout:Workout)

    @Query("SELECT * FROM Workout ORDER BY title ASC")
    fun getAll(): Flow<List<Workout>>
}