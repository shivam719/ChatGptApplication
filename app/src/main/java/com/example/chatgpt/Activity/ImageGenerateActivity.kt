package com.example.chatgpt.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgpt.Adapter.MessageAdapter
import com.example.chatgpt.Api.ApiUtilities
import com.example.chatgpt.MessageModel.MessageModel
import com.example.chatgpt.Model.ChatRequest
import com.example.chatgpt.Model.ImageGenerateRequest
import com.example.chatgpt.R
import com.example.chatgpt.Utiles.Utils
import com.example.chatgpt.databinding.ActivityImageGenrateBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.ArrayList

class ImageGenerateActivity : AppCompatActivity() {
    lateinit var binding: ActivityImageGenrateBinding
    var list = ArrayList<MessageModel>()
    private lateinit var mLayout: LinearLayoutManager
    private lateinit var adapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageGenrateBinding.inflate(layoutInflater)
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
        list.add(MessageModel(isUser = false, isImage = false, message = binding.userMsg.text.toString()))
        adapter.notifyItemChanged(list.size - 1)
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size - 1)
        val apiInterface = ApiUtilities.getApiInterFace()
        val requestBody = Gson().toJson(
            ImageGenerateRequest(
                1,
                binding.userMsg.text.toString(),
                "1024x1024"
            )
        )
            .toRequestBody("application/json".toMediaTypeOrNull())
        val contentType = "application/json"
        val authorization = "Bearer ${Utils.API_KEY}"

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiInterface.generateImage(contentType, authorization, requestBody)
                val textResponse = response.data!!.first()?.url
                list.add(MessageModel(false, isImage = true, message = textResponse!!))
                withContext(Dispatchers.Main) {
                    adapter.notifyItemChanged(list.size - 1)
                    binding.recyclerView.recycledViewPool.clear()
                    binding.recyclerView.smoothScrollToPosition(list.size - 1)
                }
                binding.userMsg.text!!.clear()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ImageGenerateActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}