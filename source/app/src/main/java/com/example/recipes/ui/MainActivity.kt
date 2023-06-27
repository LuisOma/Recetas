package com.example.recipes.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.recipes.R
import com.example.recipes.base.BaseActivity
import com.example.recipes.data.local.Prefs
import com.example.recipes.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initComponents() {
        configureNavController()
        Prefs.init(applicationContext)
    }

    private fun configureNavController() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        binding.toolbar.setupWithNavController(
            navController, AppBarConfiguration(setOf(R.id.navigation_home))
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
        }
    }
}