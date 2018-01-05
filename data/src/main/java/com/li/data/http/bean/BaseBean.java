package com.li.data.http.bean;

/**
 * 作者：CangJie on 2016/2/26 10:20
 * 邮箱：cangjie2016@gmail.com
 */
public class BaseBean<T> {
    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
