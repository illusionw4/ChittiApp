package com.example.chitti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class profiletab : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiletab)
        supportActionBar?.hide()
    }
}