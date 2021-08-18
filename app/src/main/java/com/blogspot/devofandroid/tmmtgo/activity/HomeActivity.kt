package com.blogspot.devofandroid.tmmtgo.activity


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.devofandroid.tmmtgo.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        add_student.setOnClickListener {
            startActivity(Intent(applicationContext, MapsActivity::class.java))
            showToast("Long press to select your location")
        }

        view_student.setOnClickListener {
            startActivity(Intent(applicationContext, DataRecyclerViewActivity::class.java))
        }

        view_location.setOnClickListener {
            startActivity(Intent(applicationContext, DataRecyclerViewActivity::class.java))
            showToast("Click the direction button to view in Map view..")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this@HomeActivity, text, Toast.LENGTH_LONG).show()
    }

}