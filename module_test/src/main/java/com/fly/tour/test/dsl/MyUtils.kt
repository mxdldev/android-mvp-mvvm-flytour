package com.fly.tour.test.dsl

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * Description: <MyUtils><br>
 * Author:      mxdl<br>
 * Date:        2020/2/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
inline fun TextView.addTextChangeListenerClosure(
    crossinline afterTextChanged: (Editable?) -> Unit = {},
    crossinline beforeTextChanged:(CharSequence?,Int,Int,Int) -> Unit = {s,start,count,after ->},
    crossinline onTextChanged:(CharSequence?, Int, Int, Int) -> Unit = { s, start, before, count ->}){
    var listener = object:TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(s,start,count,after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s,start,before,count)
        }
    }
    this.addTextChangedListener(listener)
}

fun TextView.addTextChangeListenerDsl(listener:TextChangeListenerDsl.()->Unit){
    var mListener = TextChangeListenerDsl()
    //mListener.listener()
    mListener.apply(listener)
    this.addTextChangedListener(mListener)
}