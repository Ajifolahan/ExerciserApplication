package edu.quinnipiac.edu.ser210.exerciserapplication

import android.app.Application


class DetailsApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: WorkoutDatabase by lazy { WorkoutDatabase.getInstance(this) }
}