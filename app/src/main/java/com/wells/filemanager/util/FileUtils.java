package com.wells.filemanager.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by wells on 16/4/22.
 */
public class FileUtils {

    public static final int TYPE_B = 1;  // 以B为单位
    public static final int TYPE_KB = 2; // 以KB为单位
    public static final int TYPE_MB = 3; // 以MB为单位
    public static final int TYPE_GB = 4; // 以GB为单位

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
     *
     * @param files
     * @param file
     * @param size
     */
    public static void getGreaterSizeFiles(List<File> files, File file, int size, int sizeType) {
        if (file == null || files == null) {
            return;
        }

        if (file.exists()) {
            if (file.isDirectory()) {
                File fileList[] = file.listFiles();
                if (fileList != null && fileList.length != 0) {
                    for (int i = 0; i < fileList.length; i++) {
                        getGreaterSizeFiles(files, fileList[i], size, sizeType);
                    }
                }
            } else {
                if (getFileSize(file, sizeType) > size) {
                    files.add(file);
                    Log.v("info", "size-->" + files.size());
                }
            }
        }
        Log.v("info", "size length -->" + files.size());
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static double getFileSize(File file, int sizeType) {
        double size = 0;
        FileInputStream fis = null;
        try {
                fis = new FileInputStream(file);
                size = FormetFileSize(fis.available(), sizeType);
        } catch (Exception e) {
            e.printStackTrace();
            size = 0;
        } finally {
            try {
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case TYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹的文件大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

}
