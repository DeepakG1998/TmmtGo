package com.blogspot.devofandroid.tmmtgo.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.devofandroid.tmmtgo.R
import com.blogspot.devofandroid.tmmtgo.adapter.StudentDataAdapter
import com.blogspot.devofandroid.tmmtgo.dataClass.GetData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_data.*
import kotlinx.android.synthetic.main.activity_view_recycler.*

class DataRecyclerViewActivity : AppCompatActivity() {

    private var userRecyclerView: RecyclerView? = null
    private var userArrayLocationList: ArrayList<GetData>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_recycler_view)


        userRecyclerView = findViewById(R.id.RecyclerView2)
        userRecyclerView?.layoutManager = LinearLayoutManager(this)
        userRecyclerView?.setHasFixedSize(true)
        userArrayLocationList = arrayListOf()
        getUserdata()
    }

    private fun getUserdata() {
        val dbReference = FirebaseDatabase.getInstance().getReference("DataAll").child("Student")
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayLocationList?.clear()
                    for (userSnapshot in snapshot.children) {
                        val student = userSnapshot.getValue(GetData::class.java)
                        if (student != null) {
                            userArrayLocationList?.add(student)
                        }
                    }
                    userRecyclerView?.adapter = userArrayLocationList?.let {
                        StudentDataAdapter(
                            this@DataRecyclerViewActivity,
                            it
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    applicationContext,
                    "Fetched Data's From Database Is Fail",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}