package com.nadillla.tabunganapp.View

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.nadillla.tabunganapp.Helper.SessionManager
import com.nadillla.tabunganapp.R
import kotlinx.android.synthetic.main.dialog_add_tabungan.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }






}