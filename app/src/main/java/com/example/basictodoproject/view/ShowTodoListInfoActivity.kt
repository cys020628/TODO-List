package com.example.basictodoproject.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.basictodoproject.R
import com.example.basictodoproject.databinding.ActivityShowTodoListInfoBinding

class ShowTodoListInfoActivity : AppCompatActivity() {
    private lateinit var activityShowTodoListInfoBinding: ActivityShowTodoListInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityShowTodoListInfoBinding = ActivityShowTodoListInfoBinding.inflate(layoutInflater)
        setContentView(activityShowTodoListInfoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpTextView()
    }

    // 데이터 세팅
    private fun setUpTextView() {
        activityShowTodoListInfoBinding.apply {
            textViewShowTitle.text = "제목 : ${intent.getStringExtra("title")}"
            textViewShowDescription.text = "내용 : ${intent.getStringExtra("description")}"
            textViewShowDate.text = "등록 날짜 : ${intent.getStringExtra("date")}"
        }
    }
}