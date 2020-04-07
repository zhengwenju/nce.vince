///*
// * Copyright (C) 2013 Google, Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.bronet.blockchain.ui;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bronet.blockchain.R;
//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.CacheFlag;
//import com.facebook.ads.InterstitialAd;
//import com.facebook.ads.InterstitialAdListener;
//
//import java.util.EnumSet;
//
///**
// * facebook 插屏广告
// */
//public class InterstitialFaceBookAdActivity extends AppCompatActivity  implements InterstitialAdListener {
//
//
//    private TextView interstitialAdStatusLabel;
//    private Button loadInterstitialButton;
//    private Button showInterstitialButton;
//    private InterstitialAd interstitialAd;
//
//    private String statusLabel = "";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_interstitial);
//        interstitialAdStatusLabel = findViewById(R.id.interstitialAdStatusLabel);
//        loadInterstitialButton = findViewById(R.id.loadInterstitialButton);
//        showInterstitialButton =findViewById(R.id.showInterstitialButton);
//
//        loadInterstitialButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (interstitialAd != null) {
//                    interstitialAd.destroy();
//                    interstitialAd = null;
//                }
//                setLabel("Loading interstitial ad...");
//
//                // Create the interstitial unit with a placement ID (generate your own on the Facebook app settings).
//                // Use different ID for each ad placement in your app.
//                interstitialAd = new InterstitialAd(InterstitialFaceBookAdActivity.this,
//                        "1542807559327931_1609143346027685"); //576319189855755
//
//                // Set a listener to get notified on changes or when the user interact with the ad.
//                interstitialAd.setAdListener(InterstitialFaceBookAdActivity.this);
//
//                // Load a new interstitial.
//                interstitialAd.loadAd(EnumSet.of(CacheFlag.VIDEO));
//            }
//        });
//
//        showInterstitialButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
//                    // Ad not ready to show.
//                    setLabel("Ad not loaded. Click load to request an ad.");
//                } else {
//                    // Ad was loaded, show it!
//                    interstitialAd.show();
//                    setLabel("");
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onError(Ad ad, AdError error) {
//        if (ad == interstitialAd) {
//            setLabel("Interstitial ad failed to load: " + error.getErrorMessage());
//        }
//    }
//
//    @Override
//    public void onAdLoaded(Ad ad) {
//        if (ad == interstitialAd) {
//            setLabel("Ad loaded. Click show to present!");
//        }
//    }
//
//    @Override
//    public void onInterstitialDisplayed(Ad ad) {
//    }
//
//    @Override
//    public void onInterstitialDismissed(Ad ad) {
//
//        // Cleanup.
//        interstitialAd.destroy();
//        interstitialAd = null;
//    }
//
//    @Override
//    public void onAdClicked(Ad ad) {
//    }
//
//    @Override
//    public void onLoggingImpression(Ad ad) {
//    }
//
//    private void setLabel(String label) {
//        statusLabel = label;
//        if (interstitialAdStatusLabel != null) {
//            interstitialAdStatusLabel.setText(statusLabel);
//        }
//    }
//}
