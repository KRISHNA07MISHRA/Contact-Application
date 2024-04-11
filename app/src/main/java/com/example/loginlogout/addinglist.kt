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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class addinglist : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    lateinit var save:Button
    lateinit var name:EditText
    lateinit var email:EditText
    lateinit var phone:EditText
    lateinit var relation:EditText
    lateinit var address:EditText
    

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




        database = Firebase.database.reference
        auth = Firebase.auth




        save.setOnClickListener {
            val Name = name.text.toString()
            val Email = email.text.toString()
            val Phone = phone.text.toString()
            val Relation = relation.text.toString()
            val address = address.text.toString()

            writeNewUser(Name,Email,Phone,Relation,address)
            User(Name,Email,Phone,Relation,address)

            if(Name.isNotBlank() && Email.isNotEmpty() && Phone.isNotEmpty() && Relation.isNotEmpty() && address.isNotEmpty()){
                startActivity(Intent(this,Home::class.java))
                finish()
            }

//            database.child("users").child(Name).get().addOnSuccessListener { snapshot ->
//                if (snapshot.exists()) {
//                    val user = snapshot.getValue(User::class.java)
//                    // Set the fetched data to the TextView
//                     val allvalue = listOf(Name,Email,Phone,Relation,address)
//                    val adapter = ArrayAdapter(this,R.layout.activity_home,R.id.view,allvalue)
//                    //view.text = "Name: ${Name}\nEmail: ${user?.Email}\nPhone: ${user?.phone}\nRelation: ${user?.relation}\nAddress: ${user?.address}"
//                } else {
//                    Toast.makeText(this, "No data found for $Name", Toast.LENGTH_LONG).show()
//                }
//            }.addOnFailureListener {
//                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_LONG).show()
//            }




        }
    }

    fun writeNewUser(name:String,email:String,phone:String,Relation:String,address:String){
        val user = User(name,email,phone,Relation,address)
        database.child("users").child(name).setValue(user)



    }
    data class User(val Name: String?= null,val Email:String?=null,val phone:String? =null ,val relation:String? = null,val address:String?= null){

    }
}