package com.bronet.blockchain.ui.game;//package com.bronet.blockchain.ui.game;
//
//import android.view.View;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.TextView;
//
//import com.bronet.blockchain.R;
//import com.bronet.blockchain.base.BaseActivity;
//import com.bronet.blockchain.ui.MainActivity;
//import com.bronet.blockchain.util.JumpUtil;
//
//import butterknife.BindView;
//
///**
// * An example full-screen activity that shows and hides the system UI (i.e.
// * status bar and navigation/system bar) with user interaction.
// */
//public class LoadGameActivity extends BaseActivity {
//    @BindView(R.id.mywebview)
//    WebView mywebview;
//    @BindView(R.id.jump)
//    TextView jump;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_game;
//    }
//
//    @Override
//    protected void initView() {
//
//    }
//
//    @Override
//    protected void initData() {
//
//        mywebview.setWebViewClient(new WebViewClient());
//        mywebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
//        //得到webview设置
//        WebSettings webSettings = mywebview.getSettings();
//        //允许使用javascript
//        webSettings.setJavaScriptEnabled(true);
//        //设置字符编码
//        webSettings.setDefaultTextEncodingName("UTF-8");
//        //支持缩放
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//
//        String url = "file:///android_asset/" + "index.html";
//        mywebview.loadUrl(url);
//
//
////        String url = "file:///android_asset/" + "xiaopingguo/index.html";
////        mywebview.loadUrl(url);
//
//        jump.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                JumpUtil.overlay(LoadGameActivity.this, MainActivity.class);
//                finish();
//            }
//        });
//    }
//
//    @Override
//    protected void setEvent() {
//
//    }
//
//}
