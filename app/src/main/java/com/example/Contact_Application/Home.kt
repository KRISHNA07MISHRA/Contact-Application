package com.example.Contact_Application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.loginlogout.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Home : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var button: Button
    lateinit var save:Button
    lateinit var name:EditText
    lateinit var email:EditText
    lateinit var phone:EditText
    lateinit var relation:EditText
    lateinit var address:EditText
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button = findViewById(R.id.logout)

        save = findViewById(R.id.save)
        name = findViewById(R.id.name)
        email = findViewById(R.id.emailaddress)
        phone = findViewById(R.id.phone)
        relation = findViewById(R.id.relation)
        address = findViewById(R.id.address)


        database = Firebase.database.reference
        auth = Firebase.auth



        button.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this,LogIn::class.java))
            finish()
        }

        save.setOnClickListener {
            val Name = name.text.toString()
            val Email = email.text.toString()
            val Phone = phone.text.toString()
            val Relation = relation.text.toString()
            val address = address.text.toString()

            writeNewUser(Name,Email,Phone,Relation,address)
            User(Email,Phone,Relation,address)



        }
    }

    fun writeNewUser(name:String,email:String,phone:String,Relation:String,address:String){
        val user = User(email,phone,Relation,address)
        database.child("users").child(name).setValue(user)



    }
    data class User(val Email:String?=null,val phone:String? =null ,val relation:String? = null,val address:String?= null){

    }
}