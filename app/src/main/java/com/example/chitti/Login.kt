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

class Login : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    var progress: ProgressBar? = null
    private lateinit var forgo: TextView
    private lateinit var dirsignupp: TextView
    private lateinit var btnlogin: Button
    private lateinit var log_user: TextView
    private lateinit var log_pass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        progress = findViewById(R.id.progress)
        progress?.visibility = View.GONE

        forgo = findViewById(R.id.forgo)
        dirsignupp = findViewById(R.id.dirsignupp)
        log_user = findViewById(R.id.loguser)
        log_pass = findViewById(R.id.logpass)

        btnlogin = findViewById(R.id.btnlogin)

        val currentuser = auth.currentUser
        if(currentuser != null){
            startActivity(Intent(this@Login, Home::class.java))
            finish()
        }

        forgo.setOnClickListener{
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
        }

        dirsignupp.setOnClickListener{
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }
        login()
    }
    private fun login(){

        btnlogin.setOnClickListener{
            progress?.visibility = View.VISIBLE
            if(TextUtils.isEmpty(log_user.text.toString())){
                log_user.error = "Please Enter Email"
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(log_pass.text.toString())){
                log_pass.error = "Please Enter Password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(log_user.text.toString(),log_pass.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        progress?.visibility = View.GONE
                        Toast.makeText(
                            this@Login,
                            "Login Successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@Login, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(
                            "User_id",
                            FirebaseAuth.getInstance().currentUser!!.uid
                        )
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        progress?.visibility = View.GONE
                        Toast.makeText(
                            this@Login,
                            "Login Failed", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}