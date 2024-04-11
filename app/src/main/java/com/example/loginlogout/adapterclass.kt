package com.example.loginlogout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterclass(private val userList: ArrayList<user_dataclass>) : RecyclerView.Adapter<adapterclass.MyViewHolder>() {


    class  MyViewHolder(itemView:View) : RecyclerView.ViewHolder (itemView){
        val setname: TextView = itemView.findViewById(R.id.setname)
        val setemail: TextView = itemView.findViewById(R.id.setemail)
        val setaddress: TextView = itemView.findViewById(R.id.setaddress)
        val setphone : TextView = itemView.findViewById(R.id.setphone)
        val setrelation: TextView = itemView.findViewById(R.id.setrelation)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.design,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.setemail.text = currentitem.email
        holder.setaddress.text = currentitem.address
        holder.setphone.text = currentitem.phone
        holder.setrelation.text = currentitem.relation
        holder.setname.text = currentitem.name
    }


}