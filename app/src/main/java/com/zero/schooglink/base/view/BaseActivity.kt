package com.zero.schooglink.base.view

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity(){

    /**
     * @param message Messages to be shown on UI
     */
    fun showToast(context: Context, message: String) {
        runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}