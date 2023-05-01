//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

//All the items contained within one exercise
data class ExerciseItem(
    val difficulty: String,
    val equipment: String,
    val instructions: String,
    val muscle: String,
    val name: String,
    val type: String
)