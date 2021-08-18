package com.blogspot.devofandroid.tmmtgo.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.devofandroid.tmmtgo.R
import kotlinx.android.synthetic.main.activity_screen_login.*


class LoginScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_login)

        btn_login.setOnClickListener {
            startActivity(Intent(applicationContext, LoginSignInActivity::class.java))
            finish()
        }


    }

}