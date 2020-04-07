package com.bronet.blockchain.ui.help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bronet.blockchain.R;
import com.bronet.blockchain.adapter.AssetsAdapter;
import com.bronet.blockchain.adapter.HelpListAdapter;
import com.bronet.blockchain.adapter.JournalismItemAdapter;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.TypeList;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;

public class JournalismActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    int id;
    int page,totalPage;
    ArrayList<TypeList.Data> list=new ArrayList<>();
    private JournalismItemAdapter journalismItemAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_journalism;
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        journalismItemAdapter = new JournalismItemAdapter(R.layout.item_journalism, list);
        rv.setAdapter(journalismItemAdapter);
        journalismItemAdapter.OnItemAssetsClickListener(new AssetsAdapter.OnItemAssetsClickListener() {
            @Override
            public void onItemAssetsClick(int position) {
                Toast.makeText(ctx, "分享成功", Toast.LENGTH_SHORT).show();
                //sharePic();
            }
        });
    }

    @Override
    protected void initData() {
        id = getIntent().getExtras().getInt("id");
        TypeList(page);
    }

    private void TypeList(int i) {
        i++;
        Data data = new Data();
        data.typeId=id;
        data.keyword="";
        data.pageNo=i;

        Gson gson = StringUtil.getGson(true);
        String s1 = gson.toJson(data);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s1);
        ApiStore.createApi(ApiService.class)
                .TypeList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TypeList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TypeList typeList) {
                        if (typeList.getStatus()==0){
                            page=typeList.getPageNo();
                            totalPage=typeList.getTotalPage();
                            ArrayList<TypeList.Data> result = typeList.getData();
                            if (page==1){
                                journalismItemAdapter.setNewData(result);
                            }else{
                                journalismItemAdapter.addData(result);
                            }
                            journalismItemAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void setEvent() {
        journalismItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",journalismItemAdapter.getItem(i).getId());
                JumpUtil.startForResult(activity,HelpDataActivity.class,1,bundle);
            }
        });
        journalismItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (page < totalPage) {
                    TypeList(page++);
                } else {
                    journalismItemAdapter.loadMoreEnd();
                }
            }
        }, rv);
    }

    @OnClick({R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
    public class Data{
        int typeId;
        String keyword;
        int pageNo;
        String pageSize;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==10){
            finish();
        }
    }

    /*public void sharePic() {
        if(qrImage!=null){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), qrImage, null, null);
                if(path!=null) {
                    Uri imageUri = Uri.parse(path);
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, "分享到"));
                }
            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == 0) {
            String path =MediaStore.Images.Media.insertImage(this.getContentResolver(), qrImage, null, null);
            if(path!=null) {
                Uri imageUri = Uri.parse(path);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        }

    }*/
}
