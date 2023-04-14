//@Authors: Camryn Keller and Momoreoluwa Ayinde

package edu.quinnipiac.edu.ser210.exerciserapplication

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class SplashScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash_screen,container,false)
        //variable for start button
        val startButton = view.findViewById<Button>(R.id.start_button)

        //onclick listener for the start button
        startButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_splashScreen_to_searchFragment)
        }

        return view
    }
}
