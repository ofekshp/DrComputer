package com.example.drcomputer.activities

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.drcomputer.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNav) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.actionMasked

        return super.onTouchEvent(event)
    }
}
