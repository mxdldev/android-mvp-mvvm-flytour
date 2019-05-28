package com.fly.tour.common.manager;

import android.content.Context;

import com.fly.tour.common.R;
import com.fly.tour.common.util.log.KLog;
import com.fly.tour.db.dao.NewsTypeDao;
import com.fly.tour.db.entity.NewsType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Description: <ChannelManager><br>
 * Author:      gxl<br>
 * Date:        2019/5/28<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class ChannelManager {
    public static final String TAG = ChannelManager.class.getSimpleName();
    private static ChannelManager channelManager;
    private Context mContext;
    private List<NewsType> mListNewsType;

    private ChannelManager(Context context) {
        mContext = context;
    }

    public static ChannelManager getInstance(Context context){
        if(channelManager == null){
            synchronized (ChannelManager.class){
                if(channelManager == null){
                    channelManager = new ChannelManager(context);
                }
            }
        }
        return channelManager;
    }
    public void initChannel(){
        NewsTypeDao newsTypeDao = new NewsTypeDao(mContext);
        mListNewsType = newsTypeDao.getListNewsType();
        if(mListNewsType == null || mListNewsType.size() == 0){
            InputStream inputStream = mContext.getResources().openRawResource(R.raw.channel);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            try {
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                Gson gson = new Gson();
                Type type = new TypeToken<List<NewsType>>() {
                }.getType();
                String json = stringBuilder.toString();
                KLog.v(TAG,"newstype:"+json);
                mListNewsType = gson.fromJson(json, type);
                newsTypeDao.addListNewStype(mListNewsType);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public List<NewsType> getListNewsType() {
        return mListNewsType;
    }
}
