package com.example.swadseva

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.swadseva.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        //initialization of firebase auth and database
        auth = Firebase.auth
        database = Firebase.database.reference
        //initialize google
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        //login with email and password
        binding.loginButton.setOnClickListener {
            //get data from text field
            email = binding.emailAddress.text.toString().trim()
            password = binding.password.text.toString().trim()

            if(email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            }else {
                createUser()
            }
        }

        binding.noAccountButton.setOnClickListener {
            val intent=Intent(this,SignActivity::class.java)
            startActivity(intent)
        }

        //google signin
        binding.google.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener { task->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else {
            Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show()
        }
    }
    private fun createUser() {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful) {
                val user = auth.currentUser
                updateUi(user)
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "SignIn Failed\nno userid found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null) {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}