package com.example.loginlogout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class forgetpassword : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var button: Button
    private lateinit var auth: FirebaseAuth
    lateinit var textview :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpassword)

        editText = findViewById(R.id.forgetemail)
        button = findViewById(R.id.forgetdone)
        textview = findViewById(R.id.move)
        auth = FirebaseAuth.getInstance()
        //val user = Firebase.auth.currentUser

        button.setOnClickListener {
            val email = editText.text.toString()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"Hogaya Bhai Email Check Kar",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"Bhai yar sahi email daal de",Toast.LENGTH_LONG).show()
                    }
                }

        }

        textview.setOnClickListener {
            startActivity(Intent(this,LogIn::class.java))
            finish()
        }
    }
}