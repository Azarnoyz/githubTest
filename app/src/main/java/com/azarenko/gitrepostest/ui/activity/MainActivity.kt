package com.azarenko.gitrepostest.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.azarenko.gitrepostest.databinding.ActivityMainBinding
import com.azarenko.gitrepostest.ui.activity.home.HomeActivity
import com.azarenko.gitrepostest.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth

    private val provider = OAuthProvider.newBuilder(Constants.GITHUB_URL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initClick()

    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()
    }

    private fun initClick() = with(binding) {
        loginButton.setOnClickListener {
            signInWithGithub()
        }
        skipButton.setOnClickListener {
            goToHomeActivity()
        }
    }

    private fun signInWithGithub() {

        mAuth.startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener {
                goToHomeActivity()
            }.addOnFailureListener {
                Toast.makeText(this, "Error : $it", Toast.LENGTH_LONG).show()
            }
    }

    private fun goToHomeActivity() = startActivity(Intent(this, HomeActivity::class.java))

    override fun onStart() {
        super.onStart()

        checkUserLogged()
    }

    private fun checkUserLogged() {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            goToHomeActivity()
        }
    }
}