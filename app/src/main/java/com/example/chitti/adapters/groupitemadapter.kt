package com.example.chitti.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chitti.R
import com.example.chitti.model.groupplay
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class groupitemadapter(private val context: Context, val grouplist: ArrayList<groupplay>, private var firestore: FirebaseFirestore, private var auth: FirebaseAuth) : RecyclerView.Adapter<groupitemadapter.MyViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): groupitemadapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.grouplay, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: groupitemadapter.MyViewHolder, position: Int) {
        val currentitem = grouplist[position]

        holder.mgroupname.text = currentitem.groupname
        holder.mrounds.text = currentitem.rounds
        holder.mmemsize.text = currentitem.membersize
        holder.mdeposit.text = currentitem.deposit

        Glide.with(context).load(currentitem.image).into(holder.mimage)
    }

    override fun getItemCount(): Int {
        return grouplist.size
    }
    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        var mgroupname: TextView = itemView.findViewById(R.id.groupname)
        var mrounds: TextView = itemView.findViewById(R.id.rounds)
        var mimage: ImageView = itemView.findViewById(R.id.image)
        var mmemsize: TextView = itemView.findViewById(R.id.membersize)
        var mdeposit: TextView = itemView.findViewById(R.id.deposit)
    }
}