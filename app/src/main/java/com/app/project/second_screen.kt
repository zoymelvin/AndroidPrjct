package com.app.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var tvUserName: TextView
    private lateinit var btnBack: Button
    private lateinit var btnChooseUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        tvWelcome = findViewById(R.id.tv_welcome)
        tvUserName = findViewById(R.id.tv_user_name)  // Ganti dengan ID yang sesuai jika diperlukan
        btnBack = findViewById(R.id.btn_back)
        btnChooseUser = findViewById(R.id.btn_choose_user)  // Ganti dengan ID yang sesuai jika diperlukan

        // Mengambil data nama dari intent
        val userName = intent.getStringExtra("USER_NAME")

        // Menampilkan nama di TextView
        tvUserName.text = userName ?: "Unknown User"

        // Penanganan aksi tombol "Back"
        btnBack.setOnClickListener {
            finish()  // Mengakhiri activity ini dan kembali ke MainActivity
        }

        // Penanganan aksi tombol "Choose a User"
        btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)  // Mengarahkan ke layar ketiga
            startActivityForResult(intent, REQUEST_CODE)  // Mulai aktivitas dengan hasil
        }
    }

    companion object {
        private const val REQUEST_CODE = 100
    }
}
