package com.example.madquiz1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class BookAppointmentActivity : AppCompatActivity() {

    private lateinit var etFullName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var spinnerType: Spinner
    private lateinit var btnDatePicker: MaterialButton
    private lateinit var tvSelectedDate: TextView
    private lateinit var btnTimePicker: MaterialButton
    private lateinit var tvSelectedTime: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var cbTerms: CheckBox
    private lateinit var btnConfirm: MaterialButton

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

        val types = arrayOf(
            "Doctor Consultation",
            "Dentist Appointment",
            "Eye Specialist",
            "Skin Specialist",
            "General Checkup"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter

        btnDatePicker.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(
                this@BookAppointmentActivity,
                { _, y, m, d ->
                    selectedDate = "$d/${m + 1}/$y"
                    tvSelectedDate.text = selectedDate
                    tvSelectedDate.setTextColor(
                        ContextCompat.getColor(this@BookAppointmentActivity, R.color.primary)
                    )
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnTimePicker.setOnClickListener {
            val c = Calendar.getInstance()
            TimePickerDialog(
                this@BookAppointmentActivity,
                { _, h, min ->
                    selectedTime = String.format("%02d:%02d", h, min)
                    tvSelectedTime.text = selectedTime
                    tvSelectedTime.setTextColor(
                        ContextCompat.getColor(this@BookAppointmentActivity, R.color.primary)
                    )
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            ).show()
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
                intent.putExtra("GENDER", radioButton.text?.toString() ?: "Not Selected")

                startActivity(intent)
            }
        }
    }

    private fun validateForm(): Boolean {
        if (etFullName.text.isNullOrBlank()) {
            etFullName.requestFocus()
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etPhone.text.isNullOrBlank()) {
            etPhone.requestFocus()
            Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etEmail.text.isNullOrBlank()) {
            etEmail.requestFocus()
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
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
