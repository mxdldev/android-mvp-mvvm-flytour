package com.fly.tour.common.util.log.klog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by zhaokaiqiang on 15/11/18.
 */
public class FileLog {

    private static final String FILE_PREFIX = "KLog_";
    private static final String FILE_FORMAT = ".log";

    public static void printFile(String tag, File targetDirectory, @Nullable String fileName, String headString, String msg) {

        fileName = (fileName == null) ? getFileName() : fileName;
        if (save(targetDirectory, fileName, msg)) {
            Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + fileName);
        } else {
            Log.e(tag, headString + "save log fails !");
        }
    }

    private static boolean save(File dic, @NonNull String fileName, String msg) {

        File file = new File(dic, fileName);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(msg);
            outputStreamWriter.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static String getFileName() {
        Random random = new Random();
        return FILE_PREFIX + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + FILE_FORMAT;
    }

}
