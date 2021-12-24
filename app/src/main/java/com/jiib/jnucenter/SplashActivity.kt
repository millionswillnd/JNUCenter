package com.jiib.jnucenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.jiib.jnucenter.databinding.ActivitySplashBinding
import com.jiib.jnucenter.mvvm.feature.main.MainActivity

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding
    private var handler : Handler? = null
    private var anim: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        // 화면 Fade-In Animation
        anim = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in_animation)
        binding.splashEntireLayout.startAnimation(anim)

        // 2.5초 뒤 MainActivity로 이동
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed(object : Runnable{
            override fun run() {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }, 2500)
    }

    override fun onStop() {
        super.onStop()
        handler = null
        anim = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if(handler!=null) handler = null
        if(anim!=null) anim = null
    }
}