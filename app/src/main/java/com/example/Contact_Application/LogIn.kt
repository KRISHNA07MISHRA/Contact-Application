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

class LogIn : AppCompatActivity() {
    lateinit var loginemail: EditText
    lateinit var loginpassword: EditText
    lateinit var verifypassword: EditText
    lateinit var loginbutton: Button
    lateinit var loginsignup:Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        loginemail = findViewById(R.id.emaillogin)
        loginpassword = findViewById(R.id.firstpassword)
        verifypassword = findViewById(R.id.secondpassword)
        loginbutton = findViewById(R.id.loginbutton)
        loginsignup = findViewById(R.id.loginsingup)
        auth = FirebaseAuth.getInstance()

        loginsignup.setOnClickListener {
            val move = Intent(this,MainActivity::class.java)
            startActivity(move)
            finish()

        }
        // check user is already login or not before
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this,Home::class.java))
            finish()
        }
        loginbutton.setOnClickListener {
            logIn()
        }

    }

    private fun logIn() {
        val email = loginemail.text.toString()
        val password = loginpassword.text.toString()
        val verify = verifypassword.text.toString()

        if (email.isEmpty() || password.isEmpty() || verify.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }
        else if(password != verify){
            Toast.makeText(this,"Bhai Yar sahi daal yar kya kar raha ha",Toast.LENGTH_LONG).show()
        }

        else{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(baseContext, "Login successful : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,Home::class.java))
                        finish()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }



}