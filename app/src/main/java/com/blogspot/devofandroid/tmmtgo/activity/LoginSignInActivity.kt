package com.blogspot.devofandroid.tmmtgo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.devofandroid.tmmtgo.R
import com.blogspot.devofandroid.tmmtgo.model.SignUpModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_login_sign_in.*

class LoginSignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sign_in)

        btn_already_account.setOnClickListener(this)
        btn_create.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        btn_SignUp.setOnClickListener(this)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("StudentDataBase").child("users")

    }

    override fun onClick(v: View?) {
        when (v) {
            btn_already_account -> {
                login_layout.visibility = View.VISIBLE
                signup_layout.visibility = View.GONE
            }
            btn_create -> {
                login_layout.visibility = View.GONE
                signup_layout.visibility = View.VISIBLE

            }
            btn_login -> {
                login()
            }
            btn_SignUp -> {
                signUp()

            }
        }
    }

    private fun login() {
        val phone = name_user.text.toString().trim()
        val password = password.text.toString().trim()
        if (phone.isEmpty() || password.isEmpty()) {
            showToast("All fields required")
        } else {
            isUserExist(phone, password)
        }
    }

    private fun isUserExist(name: String, password: String) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val list = ArrayList<SignUpModel>()
                var isDataExist = false
                for (postSnapshot in dataSnapshot.children) {
                    val value = postSnapshot.getValue<SignUpModel>()
                    if (value!!.name == name && value.password == password) {
                        isDataExist = true
                    }
                    list.add(value)
                }
                if (isDataExist) {
                    showToast("Login successfully")
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                } else {
                    showToast("Oops! Either entered Password or Username is wrong")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast(error.toString())
            }
        })

    }

    private fun signUp() {
        val name = user_signup.text.toString().trim()
        val number = number_signUp.text.toString().trim()
        val password = signUp_password.text.toString().trim()
        val conformPassword = signUp_confirmPassword.text.toString().trim()

        if (name.isEmpty() || number.isEmpty() || password.isEmpty() || conformPassword.isEmpty()) {
            showToast("All fields required")
        } else {
            val id = databaseReference.push().key
            val model = SignUpModel(name, number, password, id!!)
            databaseReference.child(id).setValue(model)
            showToast("SignUp successfully, Please enter the same details to login")
            startActivity(Intent(applicationContext, LoginSignInActivity::class.java))
            finish()
        }

    }


    private fun showToast(text: String) {
        Toast.makeText(this@LoginSignInActivity, text, Toast.LENGTH_LONG).show()
    }
}