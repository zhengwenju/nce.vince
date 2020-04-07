package com.bronet.blockchain.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Qing on 2017/11/6.
 */
public class QRCodeUtil {

    public static final String QR_FORMAT = "png";

    public static final int QRCODE_DEFAULT_WIDTH = 600;
    public static final int QRCODE_DEFAULT_HEIGHT = 600;

    private static WeakHashMap<String, Bitmap> mQRCodeCache = new WeakHashMap<>();

    public static Bitmap createQRCodeBitmap(String content) {
        return createQRCodeBitmap(content, ECLOUD_QR_CODE);
    }
    public final static String ECLOUD_QR_CODE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    public static Bitmap createQRCodeBitmap(String content, String dir) {
        return createQRCodeBitmap(content, dir, QRCODE_DEFAULT_WIDTH, QRCODE_DEFAULT_HEIGHT);
    }

    public static Bitmap createQRCodeBitmap(String content, String dir, int width, int height) {
        if (mQRCodeCache.containsKey(content)) {
            return mQRCodeCache.get(content);
        }
        if (isQRCodeExist(content, dir)) {
            Bitmap bitmap = readQRCodeFile(content, dir, width, height);
            if (bitmap != null) {
                mQRCodeCache.put(content, bitmap);
            }
            return bitmap;
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成矩阵
        BitMatrix bitMatrix;
        try {
            bitMatrix = writer.encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
            bitMatrix = deleteWhite(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        Bitmap bitmap = encode(bitMatrix);
        mQRCodeCache.put(content, bitmap);
        if (!TextUtils.isEmpty(dir) && bitmap != null) {
            save2File(bitmap, content, dir);
        }
        return bitmap;
    }

    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    private static Bitmap encode(BitMatrix bitMatrix) {
        if (bitMatrix == null) {
            return null;
        }
        // 开始利用二维码数据创建Bitmap图片
        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        int[] data = new int[w * h];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (bitMatrix.get(x, y))
                    data[y * w + x] = 0xFF000000;// 黑色
                else
                    data[y * w + x] = 0xFFFFFFFF;// 白色
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        // 传入二维码颜色数组
        bitmap.setPixels(data, 0, w, 0, 0, w, h);
        return bitmap;
    }

    private static boolean save2File(Bitmap bitmap, String content, String dir) {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File QRFile = new File(fileDir, getQRFormatName(content));
        if (QRFile.exists()) {
            QRFile.delete();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(QRFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static Bitmap readQRCodeFile(String content, String dir, int width, int height) {
        File file = new File(dir, getQRFormatName(content));
        if (!file.exists()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = width;
        options.outHeight = height;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return bitmap;
    }

    private static boolean isQRCodeExist(String content, String dir) {
        File file = new File(dir, getQRFormatName(content));
        return file.exists();
    }

    private static String getQRFormatName(String content) {
        return String.format("%s.%s", content.hashCode(), QR_FORMAT);
    }


//    /**
//     * @param attachment 图片附件。
//     * @return
//     */
//    public static String isQrCodeImage(final Context context, final String attachment, final QRCodeListener qrCodeListener) {
//        try {
//            if (!TextUtils.isEmpty(attachment)) {
//              /*  int widthScreen = context.getResources().getDimensionPixelSize(R.dimen.destination_width);
//                int heightScreen = context.getResources().getDimensionPixelSize(R.dimen.destination_height);
//                Bitmap obmp = ImageUtil.decodeSampledBitmapFromFile(attachment, widthScreen, heightScreen);*/
//                Bitmap obmp = ImageUtil.decodeImage(attachment);
//                if (obmp == null) {
//                    new Thread(
//                            new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        String path = Glide.with(context)
//                                                .load(attachment)
//                                                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                                .get().getAbsolutePath();
//                                        Log.e("ddd", "1232141453532532 : " + path);
//                                        /*int widthScreen = context.getResources().getDimensionPixelSize(R.dimen.destination_width);
//                                        int heightScreen = context.getResources().getDimensionPixelSize(R.dimen.destination_height);*/
//                                        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
//                                        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); //设置二维码内容的编码
////                                        Bitmap obmp = ImageUtil.decodeSampledBitmapFromFile(path, widthScreen, heightScreen);
//                                        Bitmap obmp = ImageUtil.decodeImage(path);
//                                        int width = obmp.getWidth();
//                                        int height = obmp.getHeight();
//                                        LuminanceSource source1 = new PlanarYUVLuminanceSource(
//                                                ImageUtil.rgb2YUV(obmp), width,
//                                                height, 0, 0, width,
//                                                height, false);
//
//                                        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source1));
//                                        MultiFormatReader reader1 = new MultiFormatReader();
//
//                                       /* Bitmap obmp = ImageUtil.decodeImage(path);
//                                        int width = obmp.getWidth();
//                                        int height = obmp.getHeight();
//                                        int[] data = new int[width * height];
//                                        obmp.getPixels(data, 0, width, 0, 0, width, height);
//                                        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
//                                        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//                                        QRCodeReader reader = new QRCodeReader();*/
//
//                                        if (qrCodeListener != null) {
//                                            qrCodeListener.qrCodeResult(reader1.decode(bitmap1, hints).getText());
//                                        }
//                                        return;
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                    ).start();
//                    return null;
//                }
//
//                Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
//                hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); //设置二维码内容的编码
//                int width = obmp.getWidth();
//                int height = obmp.getHeight();
//                LuminanceSource source1 = new PlanarYUVLuminanceSource(
//                        ImageUtil.rgb2YUV(obmp), width,
//                        height, 0, 0, width,
//                        height, false);
//
//                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source1));
//                MultiFormatReader reader1 = new MultiFormatReader();
//
//               /* int width = obmp.getWidth();
//                int height = obmp.getHeight();
//                int[] data = new int[width * height];
//                obmp.getPixels(data, 0, width, 0, 0, width, height);
//                RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
//                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//                QRCodeReader reader = new QRCodeReader();*/
//                return reader1.decode(bitmap1, hints).getText();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    public static String isQrCodeImage(Bitmap obmp) {
        try {
//            if (!TextUtils.isEmpty(attachment)) {
//                Bitmap obmp = ImageUtil.decodeImage(attachment);
            int width = obmp.getWidth();
            int height = obmp.getHeight();
            int[] data = new int[width * height];
            obmp.getPixels(data, 0, width, 0, 0, width, height);
            RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
            QRCodeReader reader = new QRCodeReader();
            return reader.decode(bitmap1).getText();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public interface QRCodeListener {
        void qrCodeResult(String qrCode);
    }
}
