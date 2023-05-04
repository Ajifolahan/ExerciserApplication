//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var imageURL: String?,
    var type: String,
    var muscle: String,
    var equipment: String,
    var difficulty: String,
    var instructions: String
)