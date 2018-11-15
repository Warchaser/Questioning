package com.warchaser.questioning.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.warchaser.questioning.app.App;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class AssetsUtil {

    private static final String TAG = "AssetsUtil";

    /**
     * 读取assets文件获取测试数据json
     *
     * @param demoFileName
     * @return
     */
    public static String getDemoData(String demoFileName) {
        String demoString = new String();
        try {
            AssetManager assetManager = App.getInstance().getApplicationContext().getAssets();
            String fullFn = "tempData/" + demoFileName;
            demoString = readTextFile(assetManager.open(fullFn), Charset.forName("UTF-8"));
        } catch (Exception e) {
            NLog.e(TAG, e.getMessage());
        }
        return demoString;
    }

    /**
     * 远端服务器配置文件json
     * */
    public static String getNetWorkConfig(Context context, String netWorkConfigFileName){
        String configString = "";
        try {
            AssetManager assetManager = context.getAssets();
            String fullFn = "networkconfig/" + netWorkConfigFileName;
            configString = readTextFile(assetManager.open(fullFn), Charset.forName("UTF-8"));
        } catch (Exception e) {
            NLog.e(TAG, e.getMessage());
        }
        return configString;
    }

    public static String readTextFile(InputStream stream, Charset charset) throws IOException {
        if (stream == null) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        char[] buffer = new char[255];
        InputStreamReader reader;
        reader = new InputStreamReader(stream, charset);
        int len;
        while ((len = reader.read(buffer)) != -1) {
            result.append(buffer, 0, len);
        }
        reader.close();
        return result.toString();
    }

}
