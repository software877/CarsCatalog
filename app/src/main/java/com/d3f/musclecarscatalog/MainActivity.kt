package com.d3f.musclecarscatalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity(), MainActivityView {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun goToDetails() {
        navController?.navigate(R.id.action_to_car_details_fragment)
    }

    override fun goToCatalog() {
        navController?.navigate(R.id.action_to_catalog_cars)
    }

}