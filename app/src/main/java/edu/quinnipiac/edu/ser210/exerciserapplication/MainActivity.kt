//@Authors: Camryn Keller and Momoreoluwa Ayinde
package edu.quinnipiac.edu.ser210.exerciserapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import edu.quinnipiac.edu.ser210.exerciserapplication.data.Workout

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Get the NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Get the NavController
        navController = navHostFragment.navController

        // Set up the AppBarConfiguration for the NavController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()

        // Set up the toolbar with the NavController and the AppBarConfiguration
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu_toolbar layout for the menu
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Get the ID of the selected menu item
        val id = item.itemId
        val navController = findNavController(R.id.nav_host_fragment)

        return item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)
                || when (id){
            // Handle the "Settings" menu item selection
            R.id.optionsFragment -> {
                NavigationUI.onNavDestinationSelected(item, navController)
                        || super.onOptionsItemSelected(item)
                true
            }

            R.id.favoriteFragment -> {
                NavigationUI.onNavDestinationSelected(item, navController)
                        || super.onOptionsItemSelected(item)
                true
            }

            // Handle the "Share" menu item selection
            R.id.action_share -> {
                // share logic (reused code)
                //https://developer.android.com/training/sharing/send and https://stackoverflow.com/questions/50689206/how-i-can-retrieve-current-fragment-in-navhostfragment
                // Get the ShareActionProvider for the menu item
                //val shareActionProvider : ShareActionProvider? = MenuItemCompat.getActionProvider(item) as ShareActionProvider?

                // Create an intent for sharing text
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val stringBuilder = StringBuilder()

                val navHostFragment =
                    supportFragmentManager.primaryNavigationFragment as NavHostFragment?

                val detailsFragment =
                    navHostFragment?.childFragmentManager?.primaryNavigationFragment as? DetailsFragment

                val favDetailFragment =
                    navHostFragment?.childFragmentManager?.primaryNavigationFragment as? FavDetailsFragment

                if(detailsFragment != null) {
                    val detail = detailsFragment?.let { exerciseList.getOrNull(it.exercise_id) }
                    stringBuilder.append("Sharing exercise from The Exerciser with you\n")
                    stringBuilder.append("Name: ${detail?.name}\n")
                    stringBuilder.append("Type: ${detail?.type}\n")
                    stringBuilder.append("Muscle: ${detail?.muscle}\n")
                    stringBuilder.append("Equipment: ${detail?.equipment}\n")
                    stringBuilder.append("Difficulty: ${detail?.difficulty}\n")
                    stringBuilder.append("Instructions: ${detail?.instructions}\n")
                }

                shareIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())
                startActivity(Intent.createChooser(shareIntent, "Share via"))

                true
            }

            // Handle the navigation menu item selection
            else -> NavigationUI.onNavDestinationSelected(item!!, navController)
                    || super.onOptionsItemSelected(item)
        }
    }
}
