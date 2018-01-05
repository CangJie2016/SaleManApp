package com.li.data.http;


import android.util.Log;

import com.li.data.http.bean.BaseBean;
import com.li.data.http.bean.CheckFace;
import com.li.data.http.bean.Record;
import com.li.data.http.bean.RecordList;
import com.li.data.http.bean.VersionBean;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author：CangJie on 2016/10/12 15:23
 * email：cangjie2016@gmail.com
 */
public class HttpMethods {
    public static final String BASE_URL = "http://192.168.0.107:8081/estate/";
    //    public static final String BASE_URL = "http://120.77.48.103:8080/yd_control_app/";
//    public static final String BASE_URL = "http://150970t1u9.51mypc.cn:52222/yd_control_app/";// 测试
    public Retrofit retrofit = RetrofitSetting.getInstance();



    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<BaseBean<T>, T> {
        @Override
        public T call(BaseBean<T> httpResult) {
            if (httpResult.code != 200) {
                throw new ApiException(httpResult.code, httpResult.msg);
            }
            return httpResult.data;
        }
    }

    // 版本更新
    public void checkVersion(Subscriber<VersionBean> subscriber, String keyCode) {
        HttpInterfaces.UpdateVersion updateVersion = retrofit.create(HttpInterfaces.UpdateVersion.class);
        Observable<VersionBean> observable = updateVersion.checkVersion("http://192.168.0.99:8080/MayDay1//servlet/VersionUpdate", keyCode)
                .map(new HttpResultFunc<VersionBean>());
        toSubscribe(observable, subscriber);
    }

    public void upload(Subscriber<CheckFace> subscriber, File file){

        HttpInterfaces.Record record = retrofit.create(HttpInterfaces.Record.class);
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("name", "李白")
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();


        //如果和rxjava1.x , call就换成 Observable
        Observable<CheckFace> call = record.upLoad(requestBody).map(new HttpResultFunc<CheckFace>());
        toSubscribe(call, subscriber);
    }



    //创建Multipart, fieldName为表单字段名
    public static MultipartBody.Part createFilePart(String fieldName, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(fieldName, file.getName(), requestFile);
        return body;
    }

    public void submit(Subscriber<BaseBean> subscriber, String title, String content, String faceToken) {
        HttpInterfaces.Record record = retrofit.create(HttpInterfaces.Record.class);
        Observable<BaseBean> observable = record.submit(title, content, faceToken);
        toSubscribe(observable, subscriber);
    }

    /**
     *  根据记录ID获取记录内容
     * @param recordId 记录ID
     */
    public void record(Subscriber<Record> subscriber, int recordId) {
        HttpInterfaces.Record record = retrofit.create(HttpInterfaces.Record.class);
        Observable<Record> observable = record.record(recordId).map(new HttpResultFunc<Record>());
        toSubscribe(observable, subscriber);
    }

    public void recordList(Subscriber<RecordList> subscriber, Integer page, Integer pageSize) {
        HttpInterfaces.Record record = retrofit.create(HttpInterfaces.Record.class);
        Observable<RecordList> observable = record.recordList(page, pageSize).map(new HttpResultFunc<RecordList>());;
        toSubscribe(observable, subscriber);
    }
}
