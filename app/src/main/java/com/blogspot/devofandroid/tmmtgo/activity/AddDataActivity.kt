package com.blogspot.devofandroid.tmmtgo.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.devofandroid.tmmtgo.R
import com.blogspot.devofandroid.tmmtgo.model.DataModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_data.*
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class AddDataActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)


    companion object {
        private const val PIC_ID = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)


        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("DataAll").child("Student")
        add_image.setOnClickListener {
            clickImage()
        }

        dob.setOnClickListener {
            dateOfBirth()
        }

        btn_save.setOnClickListener {
            addData()
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }
    }

    private fun clickImage() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, PIC_ID)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PIC_ID && data != null) {
            view_image.setImageBitmap(data.extras?.get("data") as Bitmap)


        }
    }

    @SuppressLint("SetTextI18n")
    private fun dateOfBirth() {
        val dpd = DatePickerDialog(this, { view, year, monthOfMonth, dayOfMonth ->

            dobEdit.setText("$dayOfMonth / ${monthOfMonth + 1} / $year")

        }, year, month, day)

        dpd.show()
        //databaseReference.setValue(dobEdit.text.toString())

    }


    private fun addData() {
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val name = name.text.toString().trim()
        val className = student_class.text.toString().trim()
        val section = section.text.toString().trim()
        val schoolName = name_school.text.toString().trim()
        val genderMale = btn_male.text.toString().trim()
        val genderFemale = btn_female.text.toString().trim()
        val bloodGroup = blood_group.text.toString().trim()
        val fatherName = name_father.text.toString().trim()
        val motherName = name_mother.text.toString().trim()
        val parentNumber = number_parent.text.toString().trim()
        val address1 = address_1.text.toString().trim()
        val address2 = address_2.text.toString().trim()
        val city = city.text.toString().trim()
        val state = state.text.toString().trim()
        val zipCode = zip_code.text.toString().trim()
        val emergencyNumber = number_emergency.text.toString().trim()

        if (name.isEmpty() || className.isEmpty() || section.isEmpty() || schoolName.isEmpty() || bloodGroup.isEmpty() || fatherName.isEmpty() || motherName.isEmpty()
            || parentNumber.isEmpty() || address1.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty() || emergencyNumber.isEmpty()
        ) {
            showToast("All fields required")
        } else {
            val id = databaseReference.push().key

            val dataModel = DataModel(
                latitude,
                longitude,
                name,
                className,
                section,
                schoolName,
                genderMale,
                genderFemale,
                bloodGroup,
                fatherName,
                motherName,
                parentNumber,
                address1,
                address2,
                city,
                state,
                zipCode,
                emergencyNumber,
                id!!
            )
            databaseReference.child(id).setValue(dataModel)
            databaseReference
            showToast("Data saved successfully")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this@AddDataActivity, text, Toast.LENGTH_LONG).show()
    }

}