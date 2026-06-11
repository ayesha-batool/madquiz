package com.example.madquiz1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class BookAppointmentActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var spinnerType: Spinner
    private lateinit var btnDatePicker: Button
    private lateinit var tvSelectedDate: TextView
    private lateinit var btnTimePicker: Button
    private lateinit var tvSelectedTime: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var cbTerms: CheckBox
    private lateinit var btnConfirm: Button

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        etFullName = findViewById(R.id.etFullName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        spinnerType = findViewById(R.id.spinnerType)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        btnTimePicker = findViewById(R.id.btnTimePicker)
        tvSelectedTime = findViewById(R.id.tvSelectedTime)
        rgGender = findViewById(R.id.rgGender)
        cbTerms = findViewById(R.id.cbTerms)
        btnConfirm = findViewById(R.id.btnConfirm)

        // Spinner Setup
        val types = arrayOf("Doctor Consultation", "Dentist Appointment", "Eye Specialist", "Skin Specialist", "General Checkup")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter

        // Date Picker
        btnDatePicker.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d/${m + 1}/$y"
                tvSelectedDate.text = selectedDate
            }, year, month, day)
            dpd.show()
        }

        // Time Picker
        btnTimePicker.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, { _, h, min ->
                selectedTime = String.format("%02d:%02d", h, min)
                tvSelectedTime.text = selectedTime
            }, hour, minute, true)
            tpd.show()
        }

        btnConfirm.setOnClickListener {
            if (validateForm()) {
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("NAME", etFullName.text.toString())
                intent.putExtra("PHONE", etPhone.text.toString())
                intent.putExtra("EMAIL", etEmail.text.toString())
                intent.putExtra("TYPE", spinnerType.selectedItem.toString())
                intent.putExtra("DATE", selectedDate)
                intent.putExtra("TIME", selectedTime)
                
                val selectedGenderId = rgGender.checkedRadioButtonId
                val radioButton = findViewById<RadioButton>(selectedGenderId)
                intent.putExtra("GENDER", radioButton?.text.toString() ?: "Not Selected")
                
                startActivity(intent)
            }
        }
    }

    private fun validateForm(): Boolean {
        if (etFullName.text.isBlank()) {
            etFullName.error = "Name is required"
            return false
        }
        if (etPhone.text.isBlank()) {
            etPhone.error = "Phone number is required"
            return false
        }
        if (etEmail.text.isBlank()) {
            etEmail.error = "Email is required"
            return false
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (selectedTime.isEmpty()) {
            Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show()
            return false
        }
        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!cbTerms.isChecked) {
            Toast.makeText(this, "You must accept Terms and Conditions", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}