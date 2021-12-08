package com.jiib.jnucenter.mvvm.feature.main

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.jiib.jnucenter.R

class CustomNewsTitle : ConstraintLayout {

    var title : TextView? = null
    var more : TextView? = null

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
        val view = layoutInflater.inflate(R.layout.mani_news_customview, this, false)
        addView(view)

        title = findViewById(R.id.custom_news_title) as TextView
        more = findViewById(R.id.custom_news_more) as TextView
        
    }

    private fun getAttrs(attrs: AttributeSet){
        val typedArray= context.obtainStyledAttributes(attrs, R.styleable.CustomNewsTitle)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defstyle: Int){
        val typedArray = context.obtainStyledAttributes(attrs,
            R.styleable.CustomNewsTitle,
            defstyle,
            0)

        setTypeArray(typedArray)
    }


    private fun setTypeArray(typedArray: TypedArray){

        // Title SET
        val title_resId = typedArray.getString(R.styleable.CustomNewsTitle_title)
        title?.setText(title_resId)

        // URL SET
        val url_resId = typedArray.getString(R.styleable.CustomNewsTitle_url)

        typedArray.recycle()
    }

    fun setTitle(title_string : String){
        title?.setText(title_string)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        return true
    }
}