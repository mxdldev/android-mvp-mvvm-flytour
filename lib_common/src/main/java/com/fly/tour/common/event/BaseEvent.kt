package com.fly.tour.common.event

/**
 * Description: <BaseEvent><br>
 * Author:      mxdl<br>
 * Date:        2018/4/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseEvent> */
open class BaseEvent<T> {
    var code: Int = 0
    var data: T? = null

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: T) {
        this.code = code
        this.data = data
    }
}
