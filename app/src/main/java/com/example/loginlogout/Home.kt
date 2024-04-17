package com.example.loginlogout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Home : AppCompatActivity(),UserActionListener {

    lateinit var logout: Button


    private lateinit var database:DatabaseReference

      lateinit var buttonadding: Button

      private lateinit var recyclerview : RecyclerView
      private lateinit var userArrayList:ArrayList<user_dataclass>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        buttonadding = findViewById(R.id.adding)
        recyclerview = findViewById(R.id.Recyclerview)
        //specificuser = findViewById(R.id.specificlist)
        recyclerview.layoutManager = LinearLayoutManager(this)

        recyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<user_dataclass>()
        getUserData()

        buttonadding.setOnClickListener {
                startActivity(Intent(this,addinglist::class.java))

            }
//        specificuser.setOnClickListener{
//            startActivity(Intent(this,showdata::class.java))
//            finish()
//        }




        //setupRecyclerview()

          logout = findViewById(R.id.logout)


        logout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this,LogIn::class.java))
            finish()
        }


   }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("users")

        database.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(user_dataclass::class.java)
                        userArrayList.add(user!!)
                    }
                    recyclerview.adapter = adapterclass(userArrayList,this@Home)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onDeleteUser(user: user_dataclass, position: Int) {
        val userId = user.name!!
        FirebaseDatabase.getInstance().getReference("Users").child(userId).removeValue()
            .addOnSuccessListener {
                // Remove the user from the list and notify the adapter
                userArrayList.removeAt(position)
                recyclerview.adapter?.notifyItemRemoved(position)
            }
            .addOnFailureListener{
                Toast.makeText(this,"failded",Toast.LENGTH_LONG).show()
            }

    }

    override fun onUpdateUser(user: user_dataclass, position: Int) {

    }

}