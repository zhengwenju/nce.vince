package com.bronet.blockchain.ui.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.ZXingUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 邀请码
 * Created by 18514 on 2019/7/1.
 */

public class InvitationCodeActivity extends BaseActivity {
    String code;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    PopupWindow popWindow;
    Bitmap qrImage;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_i_code;
    }

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(ConstantUtil.ID)) {
            JumpUtil.overlay(activity, LoginActivity.class);
            return;
        }
    }

    @Override
    protected void initData() {
        code = getIntent().getExtras().getString("code");
        qrImage = ZXingUtils.createQRImage(code, 1000, 1000);
        iv.setImageBitmap(qrImage);
        tvCode.setText(code);
    }

    @Override
    protected void setEvent() {

    }

    @OnClick({R.id.btn_back, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_ok:
                sharePic();
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, code);
//                shareIntent.setType("text/plain");
//                startActivity(shareIntent);
//                showPop();
                break;
        }
    }

    private void showPop() {
        View inflate= LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop5, null);
        dx(inflate);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        changeWindowAlfa(0.5f);
        popWindow.setOutsideTouchable(true);
        popWindow.setTouchable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.showAtLocation(parent,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }

    private void dx(View inflate) {
        inflate.findViewById(R.id.btn_dx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("smsto:" + ""));
                sendIntent.putExtra("sms_body", code);
                startActivity(sendIntent);
            }
        });
        inflate.findViewById(R.id.btn_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
    }


    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    public void sharePic() {
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

    }
}
