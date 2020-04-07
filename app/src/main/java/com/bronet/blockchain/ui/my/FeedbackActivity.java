package com.bronet.blockchain.ui.my;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.Attendance;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.UpFile;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.Const;
import com.bronet.blockchain.util.FileUtils;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;
import com.bronet.blockchain.util.Log.CrashHandler2;
import com.bronet.blockchain.util.ShareUtils;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.Util;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.google.gson.Gson;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 反馈
 * Created by 18514 on 2019/7/26.
 */

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.btn_list)
    PercentRelativeLayout btnList;
    @BindView(R.id.date)
    EditText date;
    @BindView(R.id.btn_iv)
    ImageView btnIv;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.iv)
    ImageView iv;
    PopupWindow popWindow;
    String path;
    Uri mImageUri;
    String FILE_PROVIDER_AUTHORITY = "com.bronet.blockchain.FileProvider";
    int REQUEST_TAKE_PHOTO_CODE = 10;
    int CODE_RESULT_REQUEST = 11;
    File imageFile;
    Bitmap bitmap;
    @BindView(R.id.loding)
    ProgressBar loding;
    @BindView(R.id.loding2)
    ProgressBar loding2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
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

    }

    @Override
    protected void setEvent() {
        btnOk.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                List<String> paths = new ArrayList<String>();
                String logFilePath = FileUtils.ddx_root + "/" +FileUtils.sdf1.format(Calendar.getInstance().getTime()) + "-log.txt";
                paths.add(logFilePath);
                for(String path :paths) {
                    ShareUtils.sendFileByOtherApp(FeedbackActivity.this,path);
                }
                return false;
            }
        });
        btnIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CrashHandler2 crashHandler2 = new CrashHandler2();
                File file = crashHandler2.getCrashFile();
                String path = file.getPath();
                ShareUtils.sendFileByOtherApp(FeedbackActivity.this,path);
                return false;
            }
        });
    }


    @OnClick({R.id.btn_back, R.id.btn_list, R.id.btn_iv, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_list:
                //反馈列表
                JumpUtil.overlay(activity, FeedbackListActivity.class);
                break;
            case R.id.btn_iv:
                showPop();
                break;
            case R.id.btn_ok:
                if (!TextUtils.isEmpty(date.getText().toString())) {
                    if (!TextUtils.isEmpty(path)) {
                        loding.setVisibility(View.VISIBLE);
                        ArrayList<String> list = new ArrayList<>();
                        list.add(path);
                        UpFile upFile = new UpFile(list);
                        upFile.setListener(
                                new UpFile.onListener() {
                            @Override
                            public void OnListener(ArrayList<String> list, String src) {
                                String s = list.get(0);
                                Add(s);
                            }
                        });
                        path=null;
                    } else {
                        Add("");
                    }
                } else {
                    ToastUtil.show(activity, getString(R.string.pop3));
                }
                break;
        }
    }

    /**
     * 反馈
     *
     * @param s
     */
    private void Add(String s) {
        String sysinfo="";
        try {
            sysinfo = dumpPhoneInfo();
        }catch (Exception ex){

        }

        Data data = new Data();
        data.setFujian(s);
        data.setUserId(ConstantUtil.ID);
        data.setFeedback(date.getText().toString());
        data.sysinfo=sysinfo;


        String deviceid =Util.getAndroidId(this);
        String netType = Util.getNetType(this);
        String inIp = Util.getIPAddress(this);
        data.isSimulator= Util.isMonitor(this);
        data.appVersion=Util.getAppVersion(this);
        String osModel = Util.getOsModel();

        data.deviceId = deviceid;
        data.netType=netType;
        data.inIp=inIp;
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>1) {
            data.ipAddress = Const.outIpAddressArray[1];
        }else {
            data.ipAddress = " ";
        }
        if(Const.outIpAddressArray!=null&&Const.outIpAddressArray.length>0) {
            data.outIp = Const.outIpAddressArray[0];
        }else {
            data.outIp = " ";
        }
        data.osModel=osModel;


        Gson gson = StringUtil.getGson(true);
        String s1 = gson.toJson(data);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s1);
        ApiStore.createApi(ApiService.class)
                .Add(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Attendance>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Attendance attendance) {
                        ToastUtil.show(activity, attendance.getMsg());
                        if (attendance.getStatus() == 0) {
//                            finish();
                            iv.setImageBitmap(null);
                            date.setText("");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        JumpUtil.errorHandler(FeedbackActivity.this,151,e.getMessage(),true);

                    }

                    @Override
                    public void onComplete() {
                        loding.setVisibility(View.GONE);
                    }
                });
    }

    private void showPop() {
        View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop, null);
        initA(inflate);
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        changeWindowAlfa(0.5f);
        popWindow.setOutsideTouchable(true);
        popWindow.setTouchable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeWindowAlfa(1f);
            }
        });
    }

    void changeWindowAlfa(float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 头像
     *
     * @param inflate
     */
    private void initA(View inflate) {
        inflate.findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第二个参数是需要申请的权限
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    Intent init = new Intent(Intent.ACTION_PICK);
                    init.setType("image/*");
                    startActivityForResult(init, 1);
                }
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相机
                //去寻找是否已经有了相机的权限
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    //否则去请求相机权限
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

                }
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
    }

    public void startCamera() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = createImageFile();//创建用来保存照片的文件
        if (imageFile != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                /*7.0以上要通过FileProvider将File转化为Uri*/
                mImageUri = FileProvider.getUriForFile(activity, FILE_PROVIDER_AUTHORITY, imageFile);
            } else {
                /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                mImageUri = Uri.fromFile(imageFile);
            }
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//将用于输出的文件Uri传递给相机
            startActivityForResult(takePhotoIntent, CODE_RESULT_REQUEST);//打开相机
            popWindow.dismiss();
        }
    }

    /**
     * 创建用来存储图片的文件，以时间来命名就不会产生命名冲突
     *
     * @return 创建的图片文件
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".png", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    public void startSetting() {

        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

        Uri uri = Uri.fromParts("package", getPackageName(), null);

        intent.setData(uri);

        startActivityForResult(intent, 10);

    }

    public void startAlertDiaLog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        alert.setTitle("说明");
        alert.setMessage("需要相机权限 去拍照");
        alert.setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里的立即开启 是打开手机的设置页面(打开相机权限)
                startSetting();

            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //如果用户还不打开 只能等用户下次点击时再次询问
//                Toast.makeText(activity, "当您点击我们会再次询问", Toast.LENGTH_SHORT).show();

            }
        });
        alert.create();
        alert.show();

    }

    private long getFileLength(File file){
        try {
            FileInputStream mFIS = new FileInputStream(file);
            long l = mFIS.available();
            return l;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    private Thread thread;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                File newFile = new File((String) msg.obj);
                L.test("===>>newFile.length:"+newFile.length());
                long uploadLength=1024*1024*1;
                if(newFile.length()<uploadLength){ //小于1M
                    L.test("===>>1");
                    path = FileUtils.saveBitmap(String.valueOf(System.currentTimeMillis() + "avatar.png"), bitmap);
                    iv.setImageBitmap(bitmap);
                }else {
                    L.test("===>>2");
                    bitmap = Bitmap.createScaledBitmap(bitmap, 600, 900, true);
                    path = FileUtils.saveBitmap(String.valueOf(System.currentTimeMillis() + "avatar.png"), bitmap);
                    iv.setImageBitmap(bitmap);
                }
                loding2.setVisibility(View.GONE);
                thread.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
             if (requestCode == 1) {
                final Uri mCutUri = data.getData();
                    loding2.setVisibility(View.VISIBLE);
                    thread = new Thread(){
                        @Override
                        public void run() {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), mCutUri);
                                String filePath = FileUtils.saveBitmap(String.valueOf(System.currentTimeMillis() + "avatar.png"), bitmap);
                                Message message = new Message();
                                message.obj = filePath;
                                handler.sendMessage(message);
                                super.run();
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    };
                    thread.start();
            } else if (requestCode == CODE_RESULT_REQUEST) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                    bitmap = Bitmap.createScaledBitmap(bitmap, 600, 900, true);
                    path = FileUtils.saveBitmap(String.valueOf(System.currentTimeMillis() + "avatar.png"), bitmap);
                    iv.setImageBitmap(bitmap);
                    bitmap = null;
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                //上面的俩个判断可有可无
                if (permissions[0].equals(Manifest.permission.CAMERA)) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //如果用户同意了再去打开相机
                        startCamera();
                    } else {
                        //因为第一次的对话框是系统提供的 从这以后系统不会自动弹出对话框 我们需要自己弹出一个对话框
                        //进行询问的工作
                        startAlertDiaLog();
                    }

                }
                break;
            case 110:
                if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //开启相册
                    Intent init = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    init.setType("image/*");
                    startActivityForResult(init, 1);
                } else {

                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public class Data {
        String userId;
        String feedback;

        String sysinfo;
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getFujian() {
            return fujian;
        }

        public void setFujian(String fujian) {
            this.fujian = fujian;
        }

        public String getSysinfo() {
            return sysinfo;
        }

        public void setSysinfo(String sysinfo) {
            this.sysinfo = sysinfo;
        }

        String fujian;


        String deviceId;
        String osModel;
        String outIp;
        String ipAddress;
        String netType;
        String appVersion;
        String inIp;
        String isSimulator;


    }

    private String dumpPhoneInfo() throws PackageManager.NameNotFoundException {
        StringBuffer sb = new StringBuffer();
        //应用的版本名称和版本号
        PackageManager pm = this.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
        sb.append("App Version: ");
        sb.append(pi.versionName);

        //android版本号
        sb.append(" OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT);

        //手机制造商
        sb.append(" Vendor: ");
        sb.append(Build.MANUFACTURER);

        //手机型号
        sb.append(" Model: ");
        sb.append(Build.MODEL);

        //cpu架构
        sb.append(" CPU ABI: ");
        sb.append(Build.CPU_ABI);

        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        if(handler!=null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }
}