package com.wells.filemanager;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by wells on 16/4/22.
 */
public class FileUtils {

    /**
     * 删除目录下所有空文件夹
     *
     * @param file
     */
    public static void deleteEmptyDirectory(File file) {
        try {
            if (file == null) {
                return;
            }
            if (file.exists()) {
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    if (files.length == 0) {
                        file.delete();  //如果是空文件直接删除
                        deleteParentDir(file);
                    } else {
                        for (int i = 0; i < files.length; i++) {
                            deleteEmptyDirectory(files[i]);  //循环删除子目录
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("deleteFile", e.getMessage());
            return;
        }
    }

    /**
     * 如果父目录为空目录则删除
     *
     * @param file
     */
    public static void deleteParentDir(File file) {
        File parentFile = file.getParentFile();
        if (parentFile.isDirectory()) {
            if (parentFile.listFiles().length == 0) {
                parentFile.delete();
                deleteParentDir(parentFile);
            }
        }
    }

    /**
     * 监测SD卡是否存在
     *
     * @return
     */
    public static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? true : false;
    }

    /**
     * 将大于size的文件加入files中
     * @param files
     * @param file
     * @param size
     */
    public static void getGreaterSizeFiles(List<File> files, File file, int size) {
        if (file == null || files == null) {
            return;
        }

        if (file.exists()) {
            if (file.isDirectory()) {
                File fileList[] = file.listFiles();
                if (fileList.length != 0) {
                    for (int i = 0; i < fileList.length; i++) {
                        if (getFileSize(fileList[i]) > size) {
                            files.add(fileList[i]);
                        }
                    }
                }
            } else {
                if (getFileSize(file) > size) {
                    files.add(file);
                }
            }
        }
    }

    /**
     * 获取文件大小
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } catch (Exception e) {
            e.printStackTrace();
            size = 0;
        }
        return size;
    }

}
