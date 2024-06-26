package com.example.loginlogout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class addinglist : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    lateinit var save:Button
    lateinit var name:EditText
    lateinit var email:EditText
    lateinit var phone:EditText
    lateinit var relation:EditText
    lateinit var address:EditText
    lateinit var backpress:FloatingActionButton
    

    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addinglist)



        save = findViewById(R.id.save)
        name = findViewById(R.id.name)
        email = findViewById(R.id.emailaddress)
        phone = findViewById(R.id.phone)
        relation = findViewById(R.id.relation)
        address = findViewById(R.id.address)
        backpress = findViewById(R.id.backpress)




        database = Firebase.database.reference
        auth = Firebase.auth



        backpress.setOnClickListener{
            startActivity(Intent(this,Home::class.java))

        }
        save.setOnClickListener {
            val Name = name.text.toString()
            val Email = email.text.toString()
            val Phone = phone.text.toString()
            val Relation = relation.text.toString()
            val address = address.text.toString()



            if(Name.isNotBlank() && Email.isNotEmpty() && Phone.isNotEmpty() && Relation.isNotEmpty() && address.isNotEmpty()){
                writeNewUser(Name,Email,Phone,Relation,address)
                //Userinfo(Name,Email,Phone,Relation,address)
                startActivity(Intent(this,Home::class.java))
                finish()
            }
            else{
                Toast.makeText(this,"something is missing please check",Toast.LENGTH_LONG).show();
                startActivity(Intent(this,Home::class.java))
                finish()
            }


        }
    }

    fun writeNewUser(name: String, email: String, phone: String, relation: String, address: String) {
        // Get the current authenticated user

        val User = auth.currentUser

        if (User != null) {
            val userId = User.uid  // This is the unique ID for the authenticated user
            val user = Userinfo(name, email, phone, relation, address)

            // Use the user's UID as the key to store user-specific data
            database.child("users").child(userId).child(name).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "User is not authenticated", Toast.LENGTH_LONG).show()
        }
    }

    data class Userinfo(val Name: String?= null,val Email:String?=null,val phone:String? =null ,val relation:String? = null,val address:String?= null){

    }
}