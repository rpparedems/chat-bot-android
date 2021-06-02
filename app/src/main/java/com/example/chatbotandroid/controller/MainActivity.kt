package com.example.chatbotandroid.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatbotandroid.R
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerClickEvents()
    }
    private fun registerClickEvents(){
        btn_send.setOnClickListener {
            //sendMessage()
            println("Pressing send button")
        }
    }
}