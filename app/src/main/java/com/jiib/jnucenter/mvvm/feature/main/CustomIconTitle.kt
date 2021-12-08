package com.jiib.jnucenter.mvvm.feature.main

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.jiib.jnucenter.R

class CustomIconTitle : ConstraintLayout{

    var icon : ImageView? = null
    var title : TextView? = null


    constructor(context: Context) : super(context){
        initView()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        initView()
        getAttrs(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet, defstyle: Int)
            : super(context!!, attrs, defstyle){
        initView()
        getAttrs(attrs, defstyle)
    }


    private fun initView(){

        val inflaterService = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = context.getSystemService(inflaterService) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.main_icon_customview, this, false)
        addView(view)

        icon = findViewById(R.id.custom_icon_vector) as ImageView
        title = findViewById(R.id.custom_icon_title) as TextView

    }

    private fun getAttrs(attrs: AttributeSet){
        val typedArray= context.obtainStyledAttributes(attrs, R.styleable.CustomIconTitle)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defstyle: Int){
        val typedArray = context.obtainStyledAttributes(attrs,
            R.styleable.CustomIconTitle,
            defstyle,
            0)

        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray){
        // 아이콘 SET
        val icon_resId = typedArray.getResourceId(R.styleable.CustomIconTitle_custom_icon, R.drawable.ic_baseline_place_24)
        icon?.setImageResource(icon_resId)

        // Title SET
        val title_resId = typedArray.getString(R.styleable.CustomIconTitle_custom_text)
        title?.setText(title_resId)

        typedArray.recycle()
    }

    fun setIcon(icon_resId : Int){
        icon?.setImageResource(icon_resId)
    }

    fun setTitle(title_string : String){
        title?.setText(title_string)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        return true
    }


}