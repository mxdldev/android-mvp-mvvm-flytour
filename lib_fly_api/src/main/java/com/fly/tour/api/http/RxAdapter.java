package com.fly.tour.api.http;

import android.widget.Toast;

import com.fly.tour.api.RetrofitManager;
import com.fly.tour.api.dto.RespDTO;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Description: <Rx适配器><br>
 * Author:      gxl<br>
 * Date:        2019/3/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class RxAdapter {
    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull LifecycleProvider lifecycle) {
        if (lifecycle != null) {
            return lifecycle.bindUntilEvent(ActivityEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("context not the LifecycleProvider type");
        }
    }
    /**
     * 线程调度器
     */
    public static SingleTransformer singleSchedulersTransformer() {
        return new SingleTransformer() {
            @Override
            public SingleSource apply(Single upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    public static SingleTransformer singleExceptionTransformer() {

        return new SingleTransformer() {
            @Override
            public SingleSource apply(Single observable) {
                return observable
                        .map(new HandleFuc())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }
    /**
     * 线程调度器
     */
    public static ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
                        .map(new HandleFuc())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            ResponseThrowable exception = ExceptionHandler.handleException(t);
            if(exception.code ==  ExceptionHandler.SYSTEM_ERROR.TIMEOUT_ERROR ){
                Toast.makeText(RetrofitManager.mContext,"网络不给力哦！",Toast.LENGTH_SHORT).show();
            }
            return Observable.error(exception);
        }
    }

    private static class HandleFuc implements Function<Object,Object> {

        @Override
        public Object apply(Object o) throws Exception {
            if(o instanceof RespDTO){
                RespDTO respDTO = (RespDTO) o;
                if(respDTO.code != ExceptionHandler.APP_ERROR.SUCC){
                    Toast.makeText(RetrofitManager.mContext,respDTO.error,Toast.LENGTH_SHORT).show();
                }
            }
            return o;
        }
    }

}
