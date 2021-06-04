package com.example.chatbotandroid.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbotandroid.R
import com.example.chatbotandroid.utils.Constants
import kotlinx.android.synthetic.main.message_item.view.*

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
    var messages = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messages.size
    }
    
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messages[position]

        when (currentMessage.type) {
            Constants.SEND_TYPE -> {
                holder.itemView.user_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.bot_message.visibility = View.GONE
            }
            Constants.RECEIVE_TYPE -> {
                holder.itemView.bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.user_message.visibility = View.GONE
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messages.add(message)
        //Notify observers of item change
        notifyItemInserted(messages.size)
    }
}