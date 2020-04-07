package com.bronet.blockchain.ui.game;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bronet.blockchain.R;
import com.bronet.blockchain.base.BaseActivity;
import com.bronet.blockchain.fix.ConstantUtil;
import com.bronet.blockchain.ui.NativeAdMobActivity;
import com.bronet.blockchain.ui.game.flop.Pkp_MainActivity;
import com.bronet.blockchain.ui.gamepan.ZP_MainActivity;
import com.bronet.blockchain.ui.login.LoginActivity;
import com.bronet.blockchain.util.JumpUtil;
import com.bronet.blockchain.util.L;

import butterknife.BindView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameAccoutActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    RelativeLayout btn_back;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.game_zp_rl)
    RelativeLayout game_zp_rl;
    @BindView(R.id.game_zp_dep)
    RelativeLayout game_zp_dep;
    @BindView(R.id.game_zp_triger)
    RelativeLayout game_zp_triger;
    @BindView(R.id.game_zp_other)
    RelativeLayout game_zp_other;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_accout;
    }

    @Override
    protected void initView() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_tv.setText(getString(R.string.home4));
        //转盘抽奖
        game_zp_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ConstantUtil.ID)) {
                    JumpUtil.overlay(activity, LoginActivity.class);
                    return;
                }
                JumpUtil.overlay(GameAccoutActivity.this, ZP_MainActivity.class);
            }
        });
        //翻牌游戏
        game_zp_dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ConstantUtil.ID)) {
                    JumpUtil.overlay(activity, LoginActivity.class);
                    return;
                }
                JumpUtil.overlay(GameAccoutActivity.this,Pkp_MainActivity.class);
            }
        });

        //老虎机
//        game_zp_triger.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (TextUtils.isEmpty(ConstantUtil.ID)) {
//                    JumpUtil.overlay(activity, LoginActivity.class);
//                    return;
//                }
//                JumpUtil.overlay(GameAccoutActivity.this,Tiger_MainActivity.class);
//            }
//        });


        game_zp_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ConstantUtil.ID)) {
                    JumpUtil.overlay(activity, LoginActivity.class);
                    return;
                }
                if(L.isDebug) {
                    JumpUtil.overlay(GameAccoutActivity.this, NativeAdMobActivity.class);
//                    JumpUtil.overlay(GameAccoutActivity.this, InterstitialAdActivity.class);
//                    JumpUtil.overlay(GameAccoutActivity.this, InterstitialFaceBookAdActivity.class);
                }
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setEvent() {

    }

}
