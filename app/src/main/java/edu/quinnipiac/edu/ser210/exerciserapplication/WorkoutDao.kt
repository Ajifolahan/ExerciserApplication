//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM Workout ORDER BY title ASC")
    fun getAll(): Flow<List<Workout>>

    @Query("SELECT * from workout WHERE id = :id")
    fun getItem(id: Int): Flow<Workout>

    @Insert
    suspend fun insert(workout:Workout)

    @Delete
    suspend fun delete(workout:Workout)



}