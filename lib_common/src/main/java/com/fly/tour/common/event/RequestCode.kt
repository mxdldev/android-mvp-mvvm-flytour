package com.fly.tour.common.event

/**
 * Description: <RequestCode><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</RequestCode> */
interface RequestCode {
    interface Main//1000开始

    interface News//2000开始

    interface Find//3000开始

    interface Me {
        companion object {
            //4000开始
            val NEWS_TYPE_ADD = 4000
        }
    }
}
