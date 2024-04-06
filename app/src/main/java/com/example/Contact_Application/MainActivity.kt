package com.example.Contact_Application

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var password:EditText

    lateinit var verify:Button
    lateinit var movetologin:Button



    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movetologin = findViewById(R.id.movetologin)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        verify = findViewById(R.id.button)

        auth = FirebaseAuth.getInstance()
        movetologin.setOnClickListener {
            val intent = Intent(this,LogIn::class.java)
            startActivity(intent)
            finish()
        }
        verify.setOnClickListener {

            performSignUp()
        }





    }

    private fun performSignUp() {
        val email = email.text.toString()
        val password = password.text.toString()


        if (email.isEmpty() || password.isEmpty() ) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()

        }
        else {


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        Toast.makeText(baseContext, "Successfully signed up.", Toast.LENGTH_SHORT)
                            .show()

                        startActivity(Intent(this,Home::class.java))
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

    }

}