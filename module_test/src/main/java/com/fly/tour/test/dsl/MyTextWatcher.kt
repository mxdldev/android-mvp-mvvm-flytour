package com.fly.tour.test.dsl

import android.text.Editable
import android.text.TextWatcher

/**
 * Description: <MyTextWatcher><br>
 * Author:      mxdl<br>
 * Date:        2020/2/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
open class MyTextWatcher: TextWatcher{
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}