package com.example.chatgpt.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.chatgpt.MessageModel.MessageModel
import com.example.chatgpt.R

class MessageAdapter(private val list: ArrayList<MessageModel>) :
    Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(view: View) : ViewHolder(view) {
      val msgText =view.findViewById<TextView>(R.id.show_message)
      val imageContainer=view.findViewById<LinearLayout>(R.id.imageCard)
      val image=view.findViewById<ImageView>(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        var form=LayoutInflater.from(parent.context)
        var view:View?=null
        view = if(viewType==0){
            form.inflate(R.layout.chatrightitem,parent,false)
        }else{
            form.inflate(R.layout.chatleftitem,parent,false)
        }
        return MessageViewHolder(view)
    }

    @Override
    override fun getItemViewType(position: Int): Int {
        val message=list[position]
        return if(message.isUser) 0 else 1
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
       val message=list[position]
        if(!message.isUser){
            holder.imageContainer.visibility=View.GONE
        }
        if(message.isImage){
            holder.imageContainer.visibility=View.VISIBLE
            Glide.with(holder.itemView.context).load(message.message).into(holder.image)
        }else{
            holder.msgText.text=message.message
        }
    }

}
