package com.example.jnucenter.mvvm.feature.lecture

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.jnucenter.R

class CustomLectureDate : ConstraintLayout {

    var title : TextView? = null
    var date : TextView? = null
    var layout : ConstraintLayout? = null

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
        val view = layoutInflater.inflate(R.layout.lecture_date_customview, this, false)
        addView(view)

        title = findViewById(R.id.lecture_title) as TextView
        date = findViewById(R.id.lecture_date) as TextView
        layout = findViewById(R.id.constraint_layout) as ConstraintLayout
    }


    private fun getAttrs(attrs: AttributeSet){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLectureDate)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defstyle: Int){
        val typeArray  = context.obtainStyledAttributes(attrs,
            R.styleable.CustomLectureDate,
            defstyle, 0)

        setTypeArray(typeArray)
    }

    private fun setTypeArray(typedArray: TypedArray){

        // title set
        val title_resId = typedArray.getString(R.styleable.CustomLectureDate_lecture_title)
        title?.setText(title_resId)

        // date set
        val date_resId = typedArray.getString(R.styleable.CustomLectureDate_lecture_date)
        date?.setText(date_resId)

        // background color set
        val color_resId = typedArray.getColor(R.styleable.CustomLectureDate_back_color, 0)
        layout?.setBackgroundColor(color_resId)
    }

    fun setTitle(lecture_title : String){
        title?.setText(lecture_title)
    }

    fun setDate(lecture_date : String){
        date?.setText(lecture_date)
    }

    fun setBackColor(color : Int){
        layout?.setBackgroundColor(color)
    }

}