package com.example.chitti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    var dbref : DatabaseReference?= null
    var  database : FirebaseDatabase?= null
    var progress: ProgressBar? = null
    private lateinit var alreadyuser: TextView
    private lateinit var name: TextView
    private lateinit var emailid: TextView
    private lateinit var mobile: TextView
    private lateinit var pass: TextView
    private lateinit var btngo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        progress = findViewById(R.id.progress)
        progress?.visibility = View.GONE

        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbref = database?.reference!!.child("Profile")

        btngo = findViewById(R.id.btngo)
        name = findViewById(R.id.name)
        emailid = findViewById(R.id.emailid)
        mobile = findViewById(R.id.mobile)
        pass = findViewById(R.id.pass)

        alreadyuser = findViewById(R.id.alreadyuser)
        alreadyuser.setOnClickListener{
            val intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
        }
        register()
    }

    private fun register() {
        btngo.setOnClickListener{

            progress?.visibility = View.VISIBLE
            if(TextUtils.isEmpty(name.text.toString())){
                name.setError("Please Enter Name")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(emailid.text.toString())){
                name.setError("Please Enter Email ID")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(mobile.text.toString())){
                name.setError("Please Enter Mobile")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(pass.text.toString())){
                name.setError("Please Enter Password")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(emailid.text.toString(),pass.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val currentUser = auth.currentUser
                        val currentUserdb = dbref?.child((currentUser?.uid!!))
                        currentUserdb?.child("name")?.setValue(name.text.toString())
                        currentUserdb?.child("email")?.setValue(emailid.text.toString())
                        currentUserdb?.child("mobile")?.setValue(mobile.text.toString())
                        currentUserdb?.child("pass")?.setValue(pass.text.toString())

                        progress?.visibility = View.GONE
                        Toast.makeText(
                            this@Register,
                            "Registration success", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        startActivity(Intent(this@Register, Home::class.java))

                    }
                    else{
                        progress?.visibility = View.GONE
                        Toast.makeText(
                            this@Register,
                            "Signup Failed", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}