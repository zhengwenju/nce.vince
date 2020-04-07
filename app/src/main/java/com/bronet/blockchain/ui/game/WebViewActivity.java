package com.bronet.blockchain.ui.game;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.fix.ConstantUtil;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.mywebview)
    WebView mywebview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected void initView() {

    }
    private void setToken(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, "uid:"+ConstantUtil.ID);
    }
    @Override
    protected void initData() {

        mywebview.setWebViewClient(new WebViewClient());
        mywebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mywebview.setWebViewClient(viewClient);
        mywebview.loadUrl(ConstantUtil.GAME_URL);
    }
    private WebViewClient viewClient = new WebViewClient() {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { // http://target.back/?isrefresh=1
            return true;
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            setToken(url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    };
    @Override
    protected void setEvent() {
    }
}
