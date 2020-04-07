package com.bronet.blockchain.model.api;



import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.Log.AppLogMessageUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * okhttp 拦截器
 *
 * @packageName: cn.white.ymc.wanandroidmaster.Model.api
 * @fileName: Http
 * @date: 2018/7/23  17:47
 * @author: ymc
 * @QQ:745612618
 */

public class HttpLoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request.newBuilder();
        RequestBody requestBody = request.body();
        String body = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }

        AppLogMessageUtil.logE("jxy",
                "发送请求: method：" + request.method()
                        + "\nurl：" + request.url()
                        + "\n请求头：" + request.headers()
                        + "\n请求参数: " + body);

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        String rBody = null;

//        if(HttpEngine.hasBody(response)) {
        BufferedSource source = responseBody.source();
        // Buffer the entire body.
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBody = buffer.clone().readString(charset);
//        }


        AppLogMessageUtil.logE("jxy",
                "收到响应: code:" + response.code()
                + "\n请求url："+response.request().url()
                + "\n请求body：" + body
                        + "\nResponse: " + rBody);

//        try {
//            if (response.request().url().toString().contains("Invest/list")) {
//                FileUtils.writeV10Mesage("\"jxy\",\n" +
//                        "                \"收到响应: code:" + response.code() +
//                        "                + \"\\n请求url："+response.request().url() +
//                        "                + \"\\n请求body：" + body+
//                        "                        + \"\\nResponse: " + rBody);
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }

        if (response.request().url().toString().contains("pi/Values")) {
            L.test("Values===============>>>");
        }
        return response;
    }

}
