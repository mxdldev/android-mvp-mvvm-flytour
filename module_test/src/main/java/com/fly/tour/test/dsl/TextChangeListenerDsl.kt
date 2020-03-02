package com.fly.tour.test.dsl

import android.text.Editable
import android.text.TextWatcher

/**
 * Description: <TextChangeListenerDsl><br>
 * Author:      mxdl<br>
 * Date:        2020/2/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class TextChangeListenerDsl : TextWatcher {
    var afterTextChanged: ((Editable?) -> Unit)? = null
    var beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    var onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null

    fun afterTextChanged(method: (Editable?) -> Unit) {
        afterTextChanged = method
    }

    fun beforeTextChanged(method: (CharSequence?, Int, Int, Int) -> Unit) {
        beforeTextChanged = method
    }

    fun onTextChanged(method: (CharSequence?, Int, Int, Int) -> Unit) {
        beforeTextChanged = method
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

}