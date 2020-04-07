package com.bronet.blockchain.model;

import android.util.Log;

import com.bronet.blockchain.data.UploadFile;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18514 on 2019/6/24.
 */

public class UpFile {

    private static onListener listener;

    public UpFile(List<String> data ) {
        upLoadFile(data);
    }

    /**
     * 定义一个接口
     */
    public interface onListener{
        void OnListener(ArrayList<String> list, String src);
    }

    public void setListener( onListener listener){
        this.listener = listener;
    }
    /**
     * 通过上传的文件的完整路径生成RequestBody
     * @param fileNames 完整的文件路径
     * @return
     */
    private static RequestBody getRequestBody(List<String> fileNames) {
        //创建MultipartBody.Builder，用于添加请求的数据
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < fileNames.size(); i++) { //对文件进行遍历
            File file = new File(fileNames.get(i)); //生成文件
            //根据文件的后缀名，获得文件类型
            String fileType = file.getName();
            builder.addFormDataPart( //给Builder添加上传的文件
                    "file",  //请求的名字
                    file.getName(), //文件的文字，服务器端用来解析的
                    RequestBody.create(MediaType.parse(fileType), file) //创建RequestBody，把上传的文件放入
            );
        }
        return builder.build(); //根据Builder创建请求
    }
    /**
     * 获得Request实例
     * @param url
     * @param fileNames 完整的文件路径
     * @return
     */
    private static Request getRequest(String url, List<String> fileNames) {
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .post(getRequestBody(fileNames));
        return builder.build();
    }
    /**
     * 根据url，发送异步Post请求
     * @param fileNames 完整的上传的文件的路径名
     */
    public static void upLoadFile(List<String> fileNames){
        OkHttpClient okHttpClient = new OkHttpClient();
        okhttp3.Call call = okHttpClient.newCall(getRequest("http://img.ncecoin.io/home/Upload",fileNames)) ;
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response!=null&&response.body()!=null) {
                    String string = response.body().string();
                    Log.e("upLoadFile:", "onResponse: "+string);
                    UploadFile uploadFile = new Gson().fromJson(string, UploadFile.class);
                        if (listener!=null) {
                            listener.OnListener(uploadFile.getFilelist(),"");
                        }
                    }
                }
        });
    }
}
