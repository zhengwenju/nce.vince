package com.bronet.blockchain.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bronet.blockchain.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ShareUtils {

    public static void sendFileByOtherApp(Context context,String path) {
        File file = new File(path);
        if (file.exists()) {
            String type = getMimeType(path);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setDataAndType(Uri.fromFile(file),type);
            List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resInfo.isEmpty()) {
                ArrayList<Intent> targetIntents = new ArrayList<Intent>();
                for (ResolveInfo info : resInfo) {
                    ActivityInfo activityInfo = info.activityInfo;
                    if (activityInfo.packageName.contains("com.tencent.mobileqq")
                            ||activityInfo.packageName.contains("com.tencent.mm")) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setPackage(activityInfo.packageName);
                        intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
                        intent.setDataAndType(Uri.fromFile(file),type);
                        intent.setClassName(activityInfo.packageName, activityInfo.name);
                        targetIntents.add(intent);
                    }


                }
                Intent chooser = Intent.createChooser(targetIntents.remove(0), "分享");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooser);

            }
        }else {
//            Toast.makeText(this, context.getString(R.string.downfile_share), Toast.LENGTH_LONG).show();
        }

    }
    public static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }

}
