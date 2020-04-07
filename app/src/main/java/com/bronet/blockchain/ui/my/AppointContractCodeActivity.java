package com.bronet.blockchain.ui.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.fix.AES;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.util.QRCodeUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 预约合约邀请码
 * Created by vince on 2019/7/31.
 */

public class AppointContractCodeActivity extends BaseActivity {
    String code;
    Bitmap qrImage=null;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.btn_no)
    ImageView btnNo;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    PopupWindow popWindow;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_yh_code;
    }
    @Override
    protected void initView() {
    }
    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        double nceData =bundle.getDouble("nceData");
        double usdtData =bundle.getDouble("usdtData");
        int pid =bundle.getInt("pid");
        String enc= AES.qrCodeEncrypt2(ConstantUtil.ID,usdtData,nceData,pid);
        qrImage = QRCodeUtil.createQRCodeBitmap(enc);
        iv.setImageBitmap(qrImage);
}
    @Override
    protected void setEvent() {

    }

    @OnClick({R.id.btn_back, R.id.btn_no, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_no:
                finish();
                break;
            case R.id.btn_ok:
                sharePic();
                break;
        }
    }
    public void sharePic() {
        if(qrImage!=null){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                String path =MediaStore.Images.Media.insertImage(AppointContractCodeActivity.this.getContentResolver(), qrImage, null, null);
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
            String path =MediaStore.Images.Media.insertImage(AppointContractCodeActivity.this.getContentResolver(), qrImage, null, null);
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
