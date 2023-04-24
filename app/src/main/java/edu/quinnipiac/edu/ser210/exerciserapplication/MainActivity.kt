package edu.quinnipiac.edu.ser210.exerciserapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Get the NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
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

        return when(id){
            // Handle the "Settings" menu item selection
            R.id.optionsFragment -> {
                NavigationUI.onNavDestinationSelected(item!!, navController)
                        || super.onOptionsItemSelected(item)
                true
            }

            R.id.favoriteFragment -> {
                NavigationUI.onNavDestinationSelected(item!!, navController)
                        || super.onOptionsItemSelected(item)
                true
            }

            // Handle the "Share" menu item selection
            R.id.action_share -> {
                // Get the ShareActionProvider for the menu item
                val shareActionProvider : ShareActionProvider? = MenuItemCompat.getActionProvider(item) as ShareActionProvider?

                // Create an intent for sharing text
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you")
                if(shareActionProvider != null){
                    shareActionProvider.setShareIntent(intent)
                }
                true
            }

            // Handle the navigation menu item selection
            else -> NavigationUI.onNavDestinationSelected(item!!, navController)
                    || super.onOptionsItemSelected(item)
            //it currently navigates to a new screen so just make the screen tell us about the app and AI
            //for the help options
        }
    }
}