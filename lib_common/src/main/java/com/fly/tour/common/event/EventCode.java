package com.fly.tour.common.event;

/**
 * Description: <EventCode><br>
 * Author:      mxdl<br>
 * Date:        2018/4/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public interface EventCode {
    interface MainCode {
        //1000开始
    }

    interface NewsCode {
        //2000开始
    }

    interface FindCode {
        //3000开始
    }

    interface MeCode {
        //4000开始
        int NEWS_TYPE_ADD = 4000;
        int NEWS_TYPE_DELETE = 4001;
        int NEWS_TYPE_UPDATE = 4002;
        int NEWS_TYPE_QUERY = 4003;

        int news_detail_add  = 4004;
    }
}
