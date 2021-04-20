package com.wolves.anunapidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // [API] 做初使化的動作
        AndroidAPI.initObject(this)
    }

    fun onShow (view : View)
    {
        AndroidAPI.startLogin()
    }
}
