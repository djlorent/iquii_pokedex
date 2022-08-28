package it.djlorent.iquii.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import it.djlorent.iquii.pokedex.databinding.ActivityMainBinding
import it.djlorent.iquii.pokedex.delegates.BarVisibilityDelegate
import it.djlorent.iquii.pokedex.delegates.BarVisibilityDelegateImpl


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    BottomNavigationActivity,
    BarVisibilityDelegate by BarVisibilityDelegateImpl()  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override lateinit var navController: NavController

    val barIds = setOf(R.id.navigation_pokedex, R.id.navigation_favorites)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerBarVisibility(this, barIds)
        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        appBarConfiguration = AppBarConfiguration(barIds)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun setBottomNavigationVisibility(isVisible: Boolean) {
        binding.navView.isVisible = isVisible
    }
}