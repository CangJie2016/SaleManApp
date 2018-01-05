package com.li.data.http;


import com.li.data.http.bean.BaseBean;
import com.li.data.http.bean.CheckFace;
import com.li.data.http.bean.Record;
import com.li.data.http.bean.RecordList;
import com.li.data.http.bean.VersionBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

/**
 * author：CangJie on 2016/10/12 15:14
 * email：cangjie2016@gmail.com
 */
public class HttpInterfaces {

    /**
     * 版本更新
     */
    public interface UpdateVersion {
        /**
         * 获取版本信息
         */
        @FormUrlEncoded
        @POST
        Observable<BaseBean<VersionBean>> checkVersion(@Url String url, @Field("time") String keyCode);

        /**
         * 下载安装包
         */
        @GET
        Call<ResponseBody> getFile(@Url String url);
    }

    public interface Record {

        @POST("record/check/photo")
        Observable<BaseBean<CheckFace>> upLoad(
                @Body RequestBody Body);

        @FormUrlEncoded
        @POST("record/submit")
        Observable<BaseBean> submit(@Field("title") String title,
                                    @Field("content") String content,
                                    @Field("faceToken") String faceToken);
        @FormUrlEncoded
        @POST("record/id")
        Observable<BaseBean<com.li.data.http.bean.Record>> record(@Field("id") int recordId);

        @FormUrlEncoded
        @POST("record/list")
        Observable<BaseBean<RecordList>> recordList(@Field("page") Integer page,
                                                    @Field("pageSize")Integer pageSize);

    }


}