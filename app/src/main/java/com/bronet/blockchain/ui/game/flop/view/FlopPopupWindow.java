//package com.bronet.blockchain.ui.game.flop.view;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.GridView;
//import android.widget.PopupWindow;
//
//
//import com.bronet.blockchain.R;
//import com.bronet.blockchain.ui.game.flop.custom.FlopTextView;
//import com.bronet.blockchain.ui.game.flop.listner.FlopOnAnimationEndListner;
//import com.bronet.blockchain.ui.game.flop.model.FlopPopupModel;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by lxp  on 2018/4/26.
// * 翻牌
// */
//public class FlopPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener, FlopOnAnimationEndListner {
//
//        private FlopTextView flopTextView;
//        private Context mContext;
//        private List<FlopPopupModel> mData = new ArrayList<>();
//
//    private GridView mGridView;
////private RecyclerView mRecycler;
//
//    private int mClickPosition;//点击的position
//    private boolean isClickAnim;//是否点击过动画，为true的时候是点击，不可以点击了
//
//    private FlopTextView[] mFlopTextView  = new FlopTextView[4];
//
//    private String url = "http://www.mocky.io/v2/5af3ecb2340000ca327705dd";
//
//    private JSONObject data;
//    private Bitmap bm=null;
//    public FlopPopupWindow(Context context) {
//        super(context);
//        this.mContext = context;
//        initPopWindow(getView(context), R.style.take_photo_anim,true);
//        initData();
//    }
//    /**
//     * 设置popupwindow
//     * @param view
//     */
//    public void initPopWindow(final View view, int anim, boolean touchable){
//        // 设置外部可点击
//        this.setOutsideTouchable(touchable);
//        this.setFocusable(touchable);
//
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        /* 设置弹出窗口特征 */
//        // 设置视图
//        this.setContentView(view);
//        // 设置弹出窗体的宽和高
//
//
//        // 设置弹出窗体可点击
////        this.setFocusable(true);
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x00000000);
//        // 设置弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//        // 设置弹出窗体显示时的动画，从底部向上弹出
//        this.setAnimationStyle(anim);
//
//    }
//    public int getRangeDate(){
//        int max=9;
//        int min=0;
//        Random random = new Random();
//        return random.nextInt(max)%(max-min+1) + min;
//    }
//    private void initData() {
//        try {
//            String s="{\n" +
//                    "        \"code\": 1,\n" +
//                    "        \"message\":\"success\",\n" +
//                    "        \"success\": true,\n" +
//                    "        \"data\": {\n" +
//                    "            \"backgroundImage\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525945341633&di=1728909339d09cbb4a1a8a9ceeabd80a&imgtype=0&src=http%3A%2F%2Fimg01.taopic.com%2F141112%2F267859-14111212514824.jpg\",\n" +
//                    "            \"obtainRewardIndex\": 1,\n" +
//                    "            \"rewords\": [\n" +
//                    "                {\n" +
//                    "                    \"rewordImageUrl\": \"http://test2.wawazhua.com/ftp_user1/toy/1516353947079.jpg\",\n" +
//                    "                    \"descriptionText\": \""+getRangeDate()+"NCE\",\n" +
//                    "                    \"rewordType\": 1\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"rewordImageUrl\": \"http://test2.wawazhua.com/ftp_user1/toy/1516353947079.jpg\",\n" +
//                    "                      \"descriptionText\": \""+getRangeDate()+"NCE\",\n" +
//                    "                    \"rewordType\": 2\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"rewordImageUrl\": \"http://test2.wawazhua.com/ftp_user1/toy/1516353947079.jpg\",\n" +
//                    "                        \"descriptionText\": \""+getRangeDate()+"NCE\",\n" +
//                    "                    \"rewordType\": 3\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"rewordImageUrl\": \"http://test2.wawazhua.com/ftp_user1/toy/1516353947079.jpg\",\n" +
//                    "                      \"descriptionText\": \""+getRangeDate()+"NCE\",\n" +
//                    "                    \"rewordType\": 4\n" +
//                    "                }\n" +
//                    "            ]\n" +
//                    "        }\n" +
//                    "    }";
//
//            JSONObject js = new JSONObject(s);
//            data = js.optJSONObject("data");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        updateData();
//
//
//
//    }
//
//    private void updateData() {
//
//
//        JSONArray rewords = null;
//        String backgroundImgUrl=null;
//        if (data != null){
//            backgroundImgUrl = data.optString("backgroundImage");
//            rewords = data.optJSONArray("rewords");
//
//        }
////        CLog.e("flopPopup","backgroundUrl:"+backgroundImgUrl+"\nrewords:"+rewords.toString());
//        for (int i = 0; i < mFlopTextView.length; i++) {
//
////            mFlopTextView[i].initImageUrl(backgroundImgUrl,null);
//            Drawable drawable = mContext.getResources().getDrawable(R.drawable.pkp_hide_bg);
//            mFlopTextView[i].initDrableImageUrl(drawable,null);
//            mFlopTextView[i].setOnClickListener(this);
//
//        }
//        for (int i = 0; i < mFlopTextView.length; i++) {
//            String str = null;
//            String url = null;
//            FlopPopupModel model = new FlopPopupModel();
//            if (rewords != null ){
//                JSONObject jsonObject = rewords.optJSONObject(i);
//                if (!jsonObject.isNull("descriptionText")){
//                    str = jsonObject.optString("descriptionText");
//                }
//                url = jsonObject.optString("rewordImageUrl");
//            }
//            FlopTextView.Builder builder = new FlopTextView.Builder();
//            builder.
//                    setmIsCustom(1).setmIsText(1).setmTextColor(Color.RED)
//                    .setmTextSize(10).setmIsAnimType(1)
////                    .setmPositiveBitmap(BitmapFactory.decodeResource(UIUtils.getResource(),R.mipmap.flop_01))
////                    .setmOppositeBitmap(BitmapFactory.decodeResource(UIUtils.getResource(),R.mipmap.flop_02))
//                    .setmIvUrl(url)
//                    .setmText(str);
//            model.setBuilder(builder);
//            mData.add(model);
//        }
//
//
//    }
//
//
//    private View getView(final Context context) {
//        View view = LayoutInflater.from(context).inflate( R.layout.popup_flop,null);
//        mGridView = view.findViewById(R.id.flopGridView);
//
//        mFlopTextView[0] = view.findViewById(R.id.popupFlop01);
//        mFlopTextView[1] = view.findViewById(R.id.popupFlop02);
//        mFlopTextView[2] = view.findViewById(R.id.popupFlop03);
//        mFlopTextView[3] = view.findViewById(R.id.popupFlop04);
////        Bitmap bitmap = BitmapFactory.decodeResource(UIUtils.getResource(), R.mipmap.flop_01);
//
//        setOnDismissListener(this);
//        return view;
//    }
//
//
//    @Override
//    public void onDismiss() {
//        mData.clear();
//        mData = null;
//        mFlopTextView = null;
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.popupFlop01:
//                clickIndex(0);
//                break;
//            case R.id.popupFlop02:
//                clickIndex(1);
//                break;
//            case R.id.popupFlop03:
//                clickIndex(2);
//                break;
//            case R.id.popupFlop04:
//                clickIndex(3);
//                break;
//        }
//    }
//
//    private void clickIndex(int position){
//        if (!isClickAnim) {
//            isClickAnim = true;
//            mClickPosition = position;
//            mFlopTextView[position].setConfig(mData.get(position).getBuilder());
//            mFlopTextView[position].setFlopOnAnimationEndListner(position, this);
//            mFlopTextView[position].startAnimation(mContext,position);
//        }
//    }
//
//    @Override
//    public void onAnimationEnd(int position) {
//        for (int i = 0; i < mFlopTextView.length; i++) {
//            Log.e("flopPopup","mClickPosition:"+mClickPosition+"\t"+position);
//            if (mClickPosition != i ){
//
//                FlopTextView.Builder builder = mData.get(i).getBuilder();
//                mFlopTextView[i].setConfig(builder);
//                mFlopTextView[i].startAnimation(mContext,i);
//            }
//        }
//    }
//
//}
//
//
//
//
//
//
//
//
