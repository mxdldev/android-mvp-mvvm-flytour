package com.fly.tour.trip.util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

/**
 * Description: <><br>
 * Author:      gxl<br>
 * Date:        2018/12/17<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class AmapUtil {
    public static AmapUtil pointConverUtil;
    private CoordinateConverter mConverter;

    private AmapUtil(Context context) {
        mConverter = new CoordinateConverter(context);

    }

    public static AmapUtil getInstance(Context context) {
        if (pointConverUtil == null) {
            synchronized (AmapUtil.class) {
                if (pointConverUtil == null) {
                    pointConverUtil = new AmapUtil(context);
                }
            }
        }
        return pointConverUtil;
    }

    public LatLng convertAmpToGps(LatLng latLng) {
        mConverter.from(CoordinateConverter.CoordType.GPS);
        mConverter.coord(latLng);
        return mConverter.convert();
    }

    public int getLocationType(int type) {
        //static int	LOCATION_TYPE_CELL 	6
        //static int	LOCATION_TYPE_GPS 1
        //static int	LOCATION_TYPE_WIFI 5
        //定位标记 0-无定位，1-GPS定位，2-综合定位
        switch (type) {
            case AMapLocation.LOCATION_TYPE_GPS:
                return 1;
            case AMapLocation.LOCATION_TYPE_CELL:
            case AMapLocation.LOCATION_TYPE_WIFI:
                return 2;
            default:
                return 0;
        }
    }
}
