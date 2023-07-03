package com.usy.ko

import com.usy.ko.R.drawable
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


// MainActivity.kt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageButton1 = findViewById<ImageButton>(R.id.imageButton1)
        val imageButton2 = findViewById<ImageButton>(R.id.imageButton2)
        val imageButton3 = findViewById<ImageButton>(R.id.imageButton3)
        val imageButton4 = findViewById<ImageButton>(R.id.imageButton4)
        val imageButton5 = findViewById<ImageButton>(R.id.imageButton5)
        val imageButton6 = findViewById<ImageButton>(R.id.imageButton6)
        val btnTrans = findViewById<Button>(R.id.btnTrans)

        // 추가적인 ImageButton 변수들을 여기에 선언해주세요

        imageButton1.setOnClickListener {
            val intent = Intent(this@MainActivity, NextActivity::class.java)
            startActivity(intent)
        }

        imageButton2.setOnClickListener {
            val intent = Intent(this@MainActivity, NextActivity2::class.java)
            startActivity(intent)
        }

        imageButton3.setOnClickListener {
            val intent = Intent(this@MainActivity, NextActivity3::class.java)
            startActivity(intent)
        }

        imageButton4.setOnClickListener {
            val intent = Intent(this@MainActivity, NextActivity4::class.java)
            startActivity(intent)
        }

        imageButton5.setOnClickListener {
            val intent = Intent(this@MainActivity, NextActivity5::class.java)
            startActivity(intent)
        }

        imageButton6.setOnClickListener {
            val intent = Intent(this@MainActivity, NextActivity6::class.java)
            startActivity(intent)
        }
        btnTrans.setOnClickListener {
            val intent = Intent(this@MainActivity, Translate::class.java)
            startActivity(intent)
        }

        // 추가적인 ImageButton에 대한 클릭 이벤트 처리를 여기에 추가해주세요
    }
}
