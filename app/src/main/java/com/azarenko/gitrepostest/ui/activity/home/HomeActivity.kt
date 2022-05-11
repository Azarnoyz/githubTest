package com.azarenko.gitrepostest.ui.activity.home


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.azarenko.gitrepostest.R
import com.azarenko.gitrepostest.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationView()

    }

    private fun initNavigationView() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }
}