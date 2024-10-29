package com.example.basictodoproject.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basictodoproject.R
import com.example.basictodoproject.data.ToDoData
import com.example.basictodoproject.databinding.ActivityMainBinding
import com.example.basictodoproject.databinding.ItemTodoBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    // RecyclerView를 구성하기 위한 리스트
    val memoList = mutableListOf<ToDoData>()

    // Activity 실행을 위한 런처
    private lateinit var addTodoActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var showTodoListInfoLauncher: ActivityResultLauncher<Intent>

    // 테스트를 위한 더미 데이터 구성
    val dummyData = ToDoData("잠자기", "잠을 완전 푹 잘꺼야!", "2024-10-29 12:30")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        //memoList.add(dummyData) // 더미 데이터
        // 앱 초기 세팅 메서드
        setUpSetting()

        // addTodoActivityLauncher 설정
        setUpAddTodoActivityLauncher()
        // ShowTodoListInfoLauncher 설정
        setUpShowTodoListInfoLauncher()

        // FAB 버튼 설정
        setUpFloatinButton()
        // RecyclerView 설정
        setUpRecyclerView()
    }

    // addTodoActivityLauncher 설정
    private fun setUpAddTodoActivityLauncher() {
        // 런처를 구성해준다.
        val contract = ActivityResultContracts.StartActivityForResult()
        addTodoActivityLauncher = registerForActivityResult(contract) {
            // 데이터가 있다면
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {
                    // 데이터 추출
                    val title = it.data?.getStringExtra("title")
                    val description = it.data?.getStringExtra("description")
                    val date = it.data?.getStringExtra("date")
                    // 객체에 담는다.
                    val dataModel = ToDoData(title!!, description!!, date!!)
                    // 리스트에 담는다.
                    memoList.add(dataModel)
                    // 리싸이클러뷰 갱신
                    activityMainBinding.recyclerViewTodo.adapter?.notifyDataSetChanged()
                }

            }
        }
    }

    // showTodoListInfoLauncher 설정
    private fun setUpShowTodoListInfoLauncher() {
        // 런처를 구성해준다,
        val contract = ActivityResultContracts.StartActivityForResult()
        showTodoListInfoLauncher = registerForActivityResult(contract) {

        }
    }

    // FAB 버튼 세팅
    private fun setUpFloatinButton() {
        activityMainBinding.apply {
            fabAddButton.setOnClickListener {
                val intent = Intent(this@MainActivity, AddToDoActivity::class.java)
                addTodoActivityLauncher.launch(intent)
            }
        }
    }


    // 기본 앱 세팅 메서드
    private fun setUpSetting() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // RecyclerView 세팅
    private fun setUpRecyclerView() {
        activityMainBinding.apply {
            recyclerViewTodo.adapter = ToDoRecyclerView()
            recyclerViewTodo.layoutManager = LinearLayoutManager(this@MainActivity)
            // 구분선 데코레이션
            val deco = MaterialDividerItemDecoration(
                this@MainActivity,
                MaterialDividerItemDecoration.VERTICAL
            )
            recyclerViewTodo.addItemDecoration(deco)

            recyclerViewTodo.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (oldScrollY == 0) {
                    fabAddButton.show()
                } else {
                    if (recyclerViewTodo.canScrollVertically(1) == false) {
                        fabAddButton.hide()
                    } else {
                        if (fabAddButton.isShown == false) {
                            // FloatingActiobButton을 나타나게 한다.
                            fabAddButton.show()
                        }
                    }
                }
            }
        }
    }

    // RecyclerView Adapter
    inner class ToDoRecyclerView(
    ) : RecyclerView.Adapter<ToDoRecyclerView.MemoViewHolderClass>() {

        inner class MemoViewHolderClass(val item: ItemTodoBinding) :
            RecyclerView.ViewHolder(item.root),
            OnClickListener {
            override fun onClick(v: View?) {
                // 항목을 눌렀을 경우
                if (memoList.size > 0) {
                    // showTodoListInfoLauncher 실행
                    val showMemoInfoIntent =
                        Intent(this@MainActivity, ShowTodoListInfoActivity::class.java)
                    showMemoInfoIntent.putExtra("title", memoList[adapterPosition].todoTitle)
                    showMemoInfoIntent.putExtra(
                        "description",
                        memoList[adapterPosition].todoDescription
                    )
                    showMemoInfoIntent.putExtra("date", memoList[adapterPosition].todoDate)
                    showTodoListInfoLauncher.launch(showMemoInfoIntent)
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolderClass {
            val itemListBinding =
                ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val viewHolderClass = MemoViewHolderClass(itemListBinding)
            itemListBinding.apply {
                root.setOnClickListener(viewHolderClass)
            }
            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return if (memoList.size == 0) {
                1
            } else {
                memoList.size
            }
        }

        override fun onBindViewHolder(holder: MemoViewHolderClass, position: Int) {
            holder.item.apply {
                if (memoList.size == 0) {
                    textViewTitle.text = "데이터가 없습니다."
                } else {
                    textViewTitle.text = memoList[position].todoTitle
                    textViewDate.text = memoList[position].todoDate
                }
            }
        }
    }
}