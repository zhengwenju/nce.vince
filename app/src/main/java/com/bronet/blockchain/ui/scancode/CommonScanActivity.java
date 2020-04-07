
package com.bronet.blockchain.ui.scancode;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AlertDialog;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.ui.my.ConfirmAssureActivity;
import com.bronet.blockchain.ui.scancode.utils.Constant;
import com.bronet.blockchain.ui.scancode.zxing.ScanListener;
import com.bronet.blockchain.ui.scancode.zxing.ScanManager;
import com.bronet.blockchain.ui.scancode.zxing.decode.DecodeThread;
import com.bronet.blockchain.ui.scancode.zxing.decode.Utils;
import com.bronet.blockchain.fix.AES;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.google.zxing.Result;

import butterknife.BindView;


/**
 * 二维码扫描使用
 *
 * @author 刘红亮  2015年4月29日  下午5:49:45
 */
public final class CommonScanActivity extends BaseActivity implements ScanListener, View.OnClickListener {
    SurfaceView scanPreview = null;
    View scanContainer;
    View scanCropView;
    ImageView scanLine;
    ScanManager scanManager;
    TextView iv_light;
    TextView qrcode_g_gallery;
    TextView qrcode_ic_back;
    final int PHOTOREQUESTCODE = 1111;
    @BindView(R.id.service_register_rescan)
    Button rescan;
    @BindView(R.id.scan_image)
    ImageView scan_image;
    @BindView(R.id.authorize_return)
    ImageView authorize_return;
    private int scanMode,type;
    @BindView(R.id.common_title_TV_center)
    TextView title;
    @BindView(R.id.scan_hint)
    TextView scan_hint;
    @BindView(R.id.tv_scan_result)
    TextView tv_scan_result;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    protected void initView() {
        setBarBiack(true);




        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        scanMode=getIntent().getIntExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_ALL_MODE);
        type=getIntent().getIntExtra("type",0);
        title.setText(R.string.scan_allcode_title);
        scan_hint.setText(R.string.scan_allcode_hint);
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        scanCropView = findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        qrcode_g_gallery = (TextView) findViewById(R.id.qrcode_g_gallery);
        qrcode_g_gallery.setOnClickListener(this);
        qrcode_ic_back = (TextView) findViewById(R.id.qrcode_ic_back);
        qrcode_ic_back.setOnClickListener(this);
        iv_light = (TextView) findViewById(R.id.iv_light);
        iv_light.setOnClickListener(this);
        rescan.setOnClickListener(this);
        authorize_return.setOnClickListener(this);
        //构造出扫描管理器
        if (type==1) {
            showPictures(PHOTOREQUESTCODE);

        }
            scanManager = new ScanManager(this, scanPreview, scanContainer, scanCropView, scanLine, scanMode,this);



    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        scanManager.onResume();
        rescan.setVisibility(View.INVISIBLE);
        scan_image.setVisibility(View.VISIBLE);
      /*  if (type==1) {
            showPictures(PHOTOREQUESTCODE);


        }*/


    }



    @Override
    public void onPause() {
        super.onPause();
        scanManager.onPause();
    }
    public void scanResult(Result rawResult, Bundle bundle) {
        //扫描成功后，扫描器不会再连续扫描，如需连续扫描，调用reScan()方法。
        //scanManager.reScan();
//		Toast.makeText(that, "result="+rawResult.getText(), Toast.LENGTH_LONG).show();

        if (!scanManager.isScanning()) { //如果当前不是在扫描状态
            //设置再次扫描按钮出现
            rescan.setVisibility(View.VISIBLE);
            scan_image.setVisibility(View.VISIBLE);
            Bitmap barcode = null;
            byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
            }
            scan_image.setImageBitmap(barcode);
        }
        rescan.setVisibility(View.VISIBLE);
        scan_image.setVisibility(View.VISIBLE);
        tv_scan_result.setVisibility(View.VISIBLE);
        if(rawResult.getText().contains(AES.APPOINT)){
            try{
                long interval = 15*60;//15分钟
                String content =rawResult.getText();
                String[] array =content.split(AES.QR_TIP);
                String tag= array[0];
                String uid= array[1];
                String usdt= array[2];
                String nce= array[3];
                String pid= array[4];
                String username= array[5];
                String time= array[6];
                L.test("qrcode content====>>"+content);
                long timel =Long.valueOf(time)+interval;
                if(timel<System.currentTimeMillis()/1000){//超过15分钟，二维码失效.

                    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.FullScreenDialog);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().clearFlags(
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    dialog.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    Window window = dialog.getWindow();
                    window.setContentView(R.layout.alter);
                    View btnYes = window.findViewById(R.id.btn_yes);
                    TextView tv = (TextView)window.findViewById(R.id.content);
                    tv.setText(getString(R.string.hy_code_expire));
                    // 自定义图片的资源
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            dialog.dismiss();
                            startScan();
                        }
                    });

                }else {
                    Bundle b = new Bundle();
                    b.putDouble(Const.USDT,Double.valueOf(usdt));
                    b.putDouble(Const.NCE,Double.valueOf(nce));
                    b.putInt(Const.PID,Integer.valueOf(AES.decrypt(pid)));
                    String uidStr = AES.decrypt(uid);
                    b.putString(Const.UID, uidStr);
                    String userName = AES.decrypt(username);
                    b.putString(Const.USERNAME,userName);
                    JumpUtil.overlay(this, ConfirmAssureActivity.class,b);
                    finish();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return;
        }
        tv_scan_result.setText("结果："+rawResult.getText());
        Intent intent = new Intent();
        intent.putExtra("code",rawResult.getText().toString());
        setResult(10,intent);
        finish();
    }

    void startScan() {
        if (rescan.getVisibility() == View.VISIBLE) {
            rescan.setVisibility(View.INVISIBLE);
            scan_image.setVisibility(View.GONE);
            scanManager.reScan();
        }
    }

    @Override
    public void scanError(Exception e) {
        //相机扫描出错时
        if(e.getMessage()!=null&&e.getMessage().startsWith("相机")){
            scanPreview.setVisibility(View.INVISIBLE);
        }
    }

    public void showPictures(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String photo_path;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTOREQUESTCODE:
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(data.getData(), proj, null, null, null);
                    if (cursor.moveToFirst()) {
                        int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(colum_index);
                        if (photo_path == null) {
                            photo_path = Utils.getPath(getApplicationContext(), data.getData());
                        }
                        scanManager.scanningImage(photo_path);
                    }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_g_gallery:
                showPictures(PHOTOREQUESTCODE);
                break;
            case R.id.iv_light:
                scanManager.switchLight();
                break;
            case R.id.qrcode_ic_back:
                finish();
                break;
            case R.id.service_register_rescan://再次开启扫描
                startScan();
                break;
            case R.id.authorize_return:
                finish();
                break;
            default:
                break;
        }
    }
}