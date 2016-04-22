package com.wells.filemanager;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wells on 16/4/22.
 */
public class FileUtils {

    static {
        exCount  = 0;
        deleteCount = 0;
    }

    /**执行次数*/
    private static int exCount  = 0;
    /**删除次数*/
    public static int deleteCount = 0;

    /**
     * 删除目录下所有空文件夹
     * @param file
     */
    public static void deleteEmptyDirectory(File file){
        exCount++;
        Log.v("deleteFile","执行了->"+exCount+"次");
        try {
            if (file == null) {
                return;
            }
            if(file.exists()){
                if(file.isDirectory()){
                    File files[] = file.listFiles();
                    if(files.length==0){
                        file.delete();  //如果是空文件直接删除
                        deleteCount++;
                        deleteParentDir(file);
                    }else {
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
     * 如果当期目录的父目录为空目录则删除
     * @param file
     */
    public static void deleteParentDir(File file){
        File parentFile = file.getParentFile();
        if(parentFile.isDirectory()){
            if(parentFile.listFiles().length==0){
                parentFile.delete();
                deleteCount++;
                deleteParentDir(parentFile);
            }
        }
    }

    /**
     * 监测SD卡是否存在
     * @return
     */
    public static boolean isSDAvailable(){
        return  Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? true : false;
    }

    public static List<File> getGreaterSizeFiles(int size){
       List<File> list = new ArrayList<File>();
        return  list;
    }

}
