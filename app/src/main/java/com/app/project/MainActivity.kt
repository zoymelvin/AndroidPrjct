package com.app.project

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPalindrome: EditText
    private lateinit var btnCheck: Button
    private lateinit var btnNext: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.et_name)
        etPalindrome = findViewById(R.id.et_palindrome)
        btnCheck = findViewById(R.id.btn_check)
        btnNext = findViewById(R.id.btn_next)

        // Penanganan aksi tombol "Check"
        btnCheck.setOnClickListener {
            val palindromeText = etPalindrome.text.toString()

            if (TextUtils.isEmpty(palindromeText)) {
                Toast.makeText(this, "Please enter a text", Toast.LENGTH_SHORT).show()
            } else {
                if (isPalindrome(palindromeText)) {
                    showDialog("Palindrome Check", "The text is a palindrome!")
                } else {
                    showDialog("Palindrome Check", "The text is not a palindrome.")
                }
            }
        }

        // Penanganan aksi tombol "Next"
        btnNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        btnNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            val name = etName.text.toString()
            intent.putExtra("USER_NAME", name)
            startActivity(intent)
        }

    }

    // Fungsi untuk memeriksa apakah teks adalah palindrom
    private fun isPalindrome(text: String): Boolean {
        val cleanText = text.replace("\\s".toRegex(), "").lowercase()
        return cleanText == cleanText.reversed()
    }

    // Fungsi untuk menampilkan dialog hasil pemeriksaan palindrom
    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}