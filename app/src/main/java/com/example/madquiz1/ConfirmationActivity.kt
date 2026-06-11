package com.example.madquiz1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val tvName: TextView = findViewById(R.id.tvConfirmName)
        val tvPhone: TextView = findViewById(R.id.tvConfirmPhone)
        val tvEmail: TextView = findViewById(R.id.tvConfirmEmail)
        val tvType: TextView = findViewById(R.id.tvConfirmType)
        val tvDate: TextView = findViewById(R.id.tvConfirmDate)
        val tvTime: TextView = findViewById(R.id.tvConfirmTime)
        val tvGender: TextView = findViewById(R.id.tvConfirmGender)
        val btnBackHome: Button = findViewById(R.id.btnBackHome)

        val name = intent.getStringExtra("NAME") ?: ""
        val phone = intent.getStringExtra("PHONE") ?: ""
        val email = intent.getStringExtra("EMAIL") ?: ""
        val type = intent.getStringExtra("TYPE") ?: ""
        val date = intent.getStringExtra("DATE") ?: ""
        val time = intent.getStringExtra("TIME") ?: ""
        val gender = intent.getStringExtra("GENDER") ?: ""

        tvName.text = getString(R.string.confirm_name, name)
        tvPhone.text = getString(R.string.confirm_phone, phone)
        tvEmail.text = getString(R.string.confirm_email, email)
        tvType.text = getString(R.string.confirm_type, type)
        tvDate.text = getString(R.string.confirm_date, date)
        tvTime.text = getString(R.string.confirm_time, time)
        tvGender.text = getString(R.string.confirm_gender, gender)

        btnBackHome.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(homeIntent)
            finish()
        }
    }
}