package com.example.chitti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chitti.adapters.groupitemadapter
import com.example.chitti.model.groupplay
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class Home : AppCompatActivity() {

    private lateinit var mrecycler: RecyclerView
    private lateinit var myadapter: groupitemadapter
    private lateinit var mArraylist: ArrayList<groupplay>
    private lateinit var mfirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        //recyclerviewcode
        mrecycler = findViewById(R.id.groups)
        mrecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mrecycler.setHasFixedSize(true)

        //Arraylist
        mArraylist = arrayListOf<groupplay>()
        myadapter = groupitemadapter(
            this,
            mArraylist,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.selectedItemId = R.id.home

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    startActivity(Intent(applicationContext, profiletab::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        EventChangeListner()

    }

    private fun EventChangeListner() {
        mfirestore = FirebaseFirestore.getInstance()
        mfirestore.collection("Groups")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            mArraylist.add(dc.document.toObject(groupplay::class.java))
                        }
                    }
                    mrecycler.adapter = groupitemadapter(
                        this@Home,
                        mArraylist,
                        FirebaseFirestore.getInstance(),
                        FirebaseAuth.getInstance()
                    )
                    myadapter.notifyDataSetChanged()
                }


            })
    }
}