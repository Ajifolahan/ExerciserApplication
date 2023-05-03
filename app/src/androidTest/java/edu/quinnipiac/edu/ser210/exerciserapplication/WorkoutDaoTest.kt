package edu.quinnipiac.edu.ser210.exerciserapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.quinnipiac.edu.ser210.exerciserapplication.data.Workout
import edu.quinnipiac.edu.ser210.exerciserapplication.data.WorkoutDao
import edu.quinnipiac.edu.ser210.exerciserapplication.data.WorkoutDatabase
import junit.framework.Assert.assertEquals
import org.junit.runner.RunWith
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class WorkoutDaoTest {

    private lateinit var workoutDao: WorkoutDao
    private lateinit var workoutDatabase: WorkoutDatabase
    private var item1 = Workout(1,
        "Running",
        "test" ,
        "running",
        "legs",
        "none",
        "beginner",
    "move legs fast")
    private var item2 = Workout(2,
        "Swim",
        "test",
        "swim",
        "fullbody",
        "none",
        "beginner",
        "do not drown")


    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        workoutDatabase = Room.inMemoryDatabaseBuilder(context, WorkoutDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        workoutDao = workoutDatabase.workoutDao()
    }

    private suspend fun addOneItemToDb() {
        workoutDao.insert(item1)
    }

    private suspend fun addMultItemToDb() {
        workoutDao.insert(item1)
        workoutDao.insert(item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val items = workoutDao.getItem(1).first()
        assertEquals(items,item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addMultItemToDb()
        val allItems = workoutDao.getAll().first()
        Assert.assertEquals(allItems[0], item1)
        Assert.assertEquals(allItems[1], item2)
    }
}