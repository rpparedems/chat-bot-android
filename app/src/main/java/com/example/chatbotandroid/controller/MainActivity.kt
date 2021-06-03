package com.example.chatbotandroid.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatbotandroid.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbotandroid.model.ChatAdapter
import com.example.chatbotandroid.model.Message
import com.example.chatbotandroid.utils.Constants
import com.example.chatbotandroid.utils.Time
import org.java_websocket.client.WebSocketClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ChatAdapter
    private lateinit var webSocketClient: WebSocketClient
    private val webSocketUri: URI? = URI("ws://10.0.2.2:8080/chat")

    //Life-cycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView()
        registerOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        initWebSocket()
    }

    override fun onPause() {
        super.onPause()
        //Close web socket if app is on background to avoid unnecessary active connection
        webSocketClient.close()
    }

    //Websocket methods
    private fun initWebSocket() {
        createWebSocketClient(webSocketUri)
        webSocketClient.connect()
    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        println("createws")
        webSocketClient = object : WebSocketClient(coinbaseUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                println("onOpen")

            }
            override fun onMessage(message: String?) {
                if (message != null) {
                    addBotResponse(message)
                }
            }
            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                println("onClose")
                println(reason)
            }
            override fun onError(ex: Exception?) {
                println("onError")
                println(ex)
            }
        }
    }

    //Helper methods
    private fun registerOnClickListeners(){
        //Send message from client to server
        send_button.setOnClickListener {
            sendMessage()
        }
    }

    private fun recyclerView() {
        adapter = ChatAdapter()
        recycler_view_chat.adapter = adapter
        recycler_view_chat.layoutManager = LinearLayoutManager(applicationContext)

    }

    private fun sendMessage() {
        val message = input_message.text.toString()

        if (message.isNotEmpty()) {
            //Clean message input
            input_message.setText("")
            //Pass message to adapter to add it to chat view
            adapter.insertMessage(Message(message, Constants.SEND_TYPE, Time.timeStamp()))
            recycler_view_chat.scrollToPosition(adapter.itemCount - 1)
            webSocketClient.send(message)
        }
    }

    private fun addBotResponse(message: String) {
        GlobalScope.launch {
            //Simulate network latency
            delay(500)
            withContext(Dispatchers.Main) {
                //Inserts sender message
                adapter.insertMessage(Message(message, Constants.RECEIVE_TYPE, Time.timeStamp()))

                //Scroll to the position of the latest message
                recycler_view_chat.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

}