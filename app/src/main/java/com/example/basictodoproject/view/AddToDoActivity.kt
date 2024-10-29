package com.example.basictodoproject.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.basictodoproject.R
import com.example.basictodoproject.databinding.ActivityAddToDoBinding

class AddToDoActivity : AppCompatActivity() {
    private lateinit var activityAddToDoBinding: ActivityAddToDoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityAddToDoBinding = ActivityAddToDoBinding.inflate(layoutInflater)
        setContentView(activityAddToDoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 저장 버튼 클릭 메서드
        onClickSaveButton()
    }

    // 저장버튼 클릭 메서드
    private fun onClickSaveButton() {
        activityAddToDoBinding.apply {
            buttonSave.setOnClickListener {
                val dataIntent = Intent()
                dataIntent.putExtra("title", textFieldTitle.editText?.text.toString())
                dataIntent.putExtra("description", textFieldDescription.editText?.text.toString())
                dataIntent.putExtra("date", textFieldDate.editText?.text.toString())

                setResult(RESULT_OK,dataIntent)
                finish()
            }
        }
    }
}