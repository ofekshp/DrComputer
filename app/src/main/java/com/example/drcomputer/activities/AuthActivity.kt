package com.example.drcomputer.activities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.drcomputer.R
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null)
        {
            setContentView(R.layout.activity_main)
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.mainNav) as NavHostFragment
            navController = navHostFragment.navController
        }
        else {
            setContentView(R.layout.activity_auth)
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.authNav) as NavHostFragment
            navController = navHostFragment.navController

        }
    }

}