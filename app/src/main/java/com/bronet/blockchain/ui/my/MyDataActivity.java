package com.bronet.blockchain.ui.my;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.data.UserInfo;
import com.bronet.blockchain.data.Username;
import com.bronet.blockchain.model.ApiService;
import com.bronet.blockchain.model.ApiStore;
import com.bronet.blockchain.model.UpFile;
import com.bronet.blockchain.model.api.cookie.SharePreData;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.FileUtils;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.StringUtil;
import com.bronet.blockchain.util.toast.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
 * 会员资料
 * Created by 18514 on 2019/7/1.
 */

public class MyDataActivity extends BaseActivity {

    PopupWindow popWindow;
    String path;
    Uri mImageUri, mCutUri;
    String FILE_PROVIDER_AUTHORITY = "com.bronet.blockchain.FileProvider";
    int REQUEST_TAKE_PHOTO_CODE = 10;
    int CODE_RESULT_REQUEST = 11;
    File imageFile;
    Bitmap bitmap;
    @BindView(R.id.btn_back)
    PercentRelativeLayout btnBack;
    @BindView(R.id.RelativeLayout)
    PercentRelativeLayout RelativeLayout;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.vip_bh)
    TextView vipBh;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.pop)
    TextView pop;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.number2)
    TextView number2;
    @BindView(R.id.vip1)
    TextView vip1;
    @BindView(R.id.ncelevel)
    TextView ncelevel;

    @Override
    protected int getLayoutId() {
        return R.layout.activitymy_d;
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

        ApiStore.createApi(ApiService.class)
                .info(ConstantUtil.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UserInfo userInfo) {
                        try {
                            if (userInfo.getStatus() == 0) {
                                UserInfo.Result result = userInfo.getResult();
                                SharePreData sp = new SharePreData();
                                name.setText(result.getUsername());
                                if (!TextUtils.isEmpty(result.getAvatar())) {
                                    Glide.with(activity).load(result.getAvatar())
                                            .apply(new RequestOptions().error(R.mipmap.dicon_37).circleCrop())
                                            .into(iv);
                                }
                                vipBh.setText(result.getNid());
                                time.setText(result.getRegisterTime());
                                pop.setText(result.getIsPartner() == 0 ? "否" : "是");
                                number.setText(String.valueOf(result.getInviteUserCount()));
                                number2.setText(String.valueOf(result.getTeamUserCount()));
                                switch (result.getMemberLevel()) {
                                    case 0:
                                        vip1.setText("普通会员");
                                        break;
                                    case 1:
                                        vip1.setText("黄金会员");
                                        break;
                                    case 2:
                                        vip1.setText("钻石会员");
                                        break;
                                    case 3:
                                        vip1.setText("皇冠大使");
                                        break;
                                }
                                ncelevel.setText(String.valueOf(result.getNceLevel()));

                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
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

    }

    @OnClick({R.id.btn_back, R.id.RelativeLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.RelativeLayout:
                //修改头像
                showPop(1);
                break;

        }
    }

    @SuppressLint("WrongConstant")
    private void showPop(int type) {
        View inflate;
        if (type == 1) {
            inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop, null);
            initA(inflate);
        } else {
            inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop1, null);
            initB(inflate);
        }
        popWindow = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        changeWindowAlfa(0.5f);
        if (type == 1) {
            popWindow.setOutsideTouchable(true);
            popWindow.setTouchable(true);
            popWindow.setAnimationStyle(R.style.popwindow_anim_style);
            popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            popWindow.showAtLocation(parent,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            popWindow.setFocusable(true);
            popWindow.setAnimationStyle(R.style.popwindow_anim_style);
            popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            popWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popWindow.showAtLocation(parent,
                    Gravity.CENTER, 0, 0);
        }
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
     * 修改名称
     *
     * @param inflate
     */
    private void initB(View inflate) {
        final EditText name = inflate.findViewById(R.id.name);
        name.postDelayed(new Runnable() {
            @Override
            public void run() {
                name.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) MyDataActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        inflate.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    ToastUtil.show(activity, activity.getString(R.string.d15));
                } else {
                    username(name.getText().toString());
                }
            }
        });
    }

    public void username(String name) {
        MyDataActivity.name name1 = new name();
        name1.username = name;
        name1.id = ConstantUtil.ID;
        Gson gson = StringUtil.getGson(true);
        String s = gson.toJson(name1);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        ApiStore.createApi(ApiService.class)
                .username(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Username>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Username username) {
                        ToastUtil.show(activity, username.getMsg());
                        if (username.getStatus() == 0) {
                            popWindow.dismiss();
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
            startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO_CODE);//打开相机
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
                ToastUtil.show(MyDataActivity.this,"当您点击我们会再次询问");

            }
        });
        alert.create();
        alert.show();

    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    private void photoClip(Uri uri, boolean fromCapture) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent


        // 注意一定要添加该项权限，否则会提示无法裁剪
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);

        // 设置裁剪区域的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // 设置裁剪区域的宽度和高度
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // 图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);

        // 指定裁剪完成以后的图片所保存的位置,pic info显示有延时
        if (fromCapture) {
            // 如果是使用拍照，那么原先的uri和最终目标的uri一致,注意这里的uri必须是Uri.fromFile生成的
            mCutUri = Uri.fromFile(imageFile);
        } else { // 从相册中选择，那么裁剪的图片保存在take_photo中
            String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
            String fileName = "photo_" + time;
            File mCutFile = new File(Environment.getExternalStorageDirectory() + "/take_photo", fileName + ".jpeg");
            imageFile = mCutFile;
            if (!mCutFile.getParentFile().exists()) {
                mCutFile.getParentFile().mkdirs();
            }
            mCutUri = Uri.fromFile(mCutFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutUri);
        // 以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(uri);
        this.sendBroadcast(intentBc);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO_CODE) {
                /*如果拍照成功，将Uri用BitmapFactory的decodeStream方法转为Bitmap*/
                photoClip(mImageUri, true);
            } else if (requestCode == 1) {
                Uri mImageCaptureUri = data.getData();
                photoClip(mImageCaptureUri, false);
            } else if (requestCode == CODE_RESULT_REQUEST) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), mCutUri);
                    bitmap = toRoundBitmap(bitmap);
                    path = FileUtils.saveBitmap(String.valueOf(System.currentTimeMillis() + "avatar.png"), bitmap);
                    Matrix matrix = new Matrix();
                    matrix.setScale(0.9f, 0.9f);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    iv.setImageBitmap(bitmap);
                    ArrayList<String> list = new ArrayList<>();
                    list.add(path);
                    UpFile upFile = new UpFile(list);
                    upFile.setListener(new UpFile.onListener() {
                        @Override
                        public void OnListener(ArrayList<String> list, String src) {
                            String s = list.get(0);
                            avatar(s);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void avatar(String s) {
        avatar avatar = new avatar();
        avatar.avatar = s;
        avatar.id = ConstantUtil.ID;

        Gson gson = StringUtil.getGson(true);
        String s1 = gson.toJson(avatar);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s1);
        ApiStore.createApi(ApiService.class)
                .avatar(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Username>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Username username) {
                        ToastUtil.show(activity, username.getMsg());
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

    public class name {
        String id;
        String username;
    }

    public class avatar {
        String id;
        String avatar;
    }
}
