package com.example.drcomputer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentNavigation) as NavHostFragment
        navController = navHostFragment.navController

//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            if (destination.id == R.id.loginFragment) {
//                // The code here will run when the login fragment is displayed
//                Log.d("Navigation", "Login fragment is displayed")
//            }
//        }

    }

}