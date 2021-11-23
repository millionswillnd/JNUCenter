package com.example.jnucenter.mvvm.feature.number

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jnucenter.R
import com.example.jnucenter.mvvm.repository.NumberReopository

class NumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number)


        NumberReopository().getNumbers()
    }
}