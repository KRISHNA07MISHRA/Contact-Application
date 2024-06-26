package com.example.loginlogout


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

class Home : AppCompatActivity(),adapterclass.OnItemClickListener{

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

        recyclerview.layoutManager = LinearLayoutManager(this)

        recyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<user_dataclass>()
        getUserData()



        buttonadding.setOnClickListener {
                startActivity(Intent(this,addinglist::class.java))

            }


        logout = findViewById(R.id.logout)


        logout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this,LogIn::class.java))
            finish()
        }




   }

    private fun getUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            database = FirebaseDatabase.getInstance().getReference("users").child(userId)
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userArrayList.clear()
                    snapshot.children.mapNotNullTo(userArrayList) { it.getValue(user_dataclass::class.java) }
                    recyclerview.adapter = adapterclass(userArrayList,this@Home )

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        }

    }

    override fun onItemdeleteClick(username: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if(userId != null){
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child(username)
            userRef.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Data deleted successfully!", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Failed to remove data
                    Toast.makeText(this, "Failed to delete data", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onItemupdateclick(username: String,useremail: String,useraddress: String,userrelation: String,userphone: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.update,null)

        val updatename = dialogView.findViewById<EditText>(R.id.updatename)
        val updateaddress = dialogView.findViewById<EditText>(R.id.updateaddress)
        val updaterelation = dialogView.findViewById<EditText>(R.id.updaterelation)
        val updatephone = dialogView.findViewById<EditText>(R.id.updatephone)
        val updateemail = dialogView.findViewById<EditText>(R.id.updateemailaddress)


        updatename.setText(username)
        updateaddress.setText(useraddress)
        updateemail.setText(useremail)
        updatephone.setText(userphone)
        updaterelation.setText(userrelation)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("update user information")
            .setPositiveButton("update"){dialog, _ ->


                updatelist(updatename.text.toString(),updateemail.text.toString(),updateaddress.text.toString(),updatephone.text.toString(),updaterelation.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
                val dialog = dialogBuilder.create()
            dialog.show()










    }

    private fun updatelist(username: String, useremail: String, useraddress: String, userphone: String, userrelation: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if(userId != null){
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child(username)
            val updatelist = user_dataclass(useraddress,useremail,username,userphone,userrelation)
            userRef.setValue(updatelist)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"succesful",Toast.LENGTH_LONG).show()
                    }

                }
        }
    }




}