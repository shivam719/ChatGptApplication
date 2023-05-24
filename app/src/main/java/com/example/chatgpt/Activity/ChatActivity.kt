package com.example.chatgpt.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgpt.Adapter.MessageAdapter
import com.example.chatgpt.Api.ApiUtilities
import com.example.chatgpt.MessageModel.MessageModel
import com.example.chatgpt.Model.ChatRequest
import com.example.chatgpt.Utiles.Utils
import com.example.chatgpt.databinding.ActivityChatBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.util.ArrayList

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    var list = ArrayList<MessageModel>()
    private lateinit var mLayout: LinearLayoutManager
    private lateinit var adapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mLayout = LinearLayoutManager(this)
        mLayout.stackFromEnd = true
        adapter = MessageAdapter(list)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = mLayout
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.sendbtn.setOnClickListener {
            if (binding.userMsg.text!!.toString().isEmpty()) {
                Toast.makeText(this, "Please ask your question ? ", Toast.LENGTH_LONG).show()
            } else {
                callApi()
            }
        }

    }

    private fun callApi() {
        list.add(MessageModel(isUser = true, isImage = false, message = binding.userMsg.text.toString()))
        adapter.notifyItemChanged(list.size - 1)
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size - 1)
        val apiInterface = ApiUtilities.getApiInterFace()
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            Gson().toJson(
                ChatRequest(
                    250,
                    "text-davinci-003",
                    binding.userMsg.text.toString(),
                    0.7
                )
            )
        )
        val contentType = "application/json"
        val authorization = "Bearer ${Utils.API_KEY}"

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiInterface.generateChat(contentType, authorization, requestBody)
                val textResponse = response.choices!!.first()!!.text
                list.add(MessageModel(true, isImage = false, message = textResponse!!))
                withContext(Dispatchers.Main) {
                    adapter.notifyItemChanged(list.size - 1)
                    binding.recyclerView.recycledViewPool.clear()
                    binding.recyclerView.smoothScrollToPosition(list.size - 1)
                }
                binding.userMsg.text!!.clear()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ChatActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}