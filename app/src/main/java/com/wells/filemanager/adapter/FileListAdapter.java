package com.wells.filemanager.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.wells.filemanager.R;
import com.wells.filemanager.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wells on 16/4/25.
 */
public class FileListAdapter extends CommonAdapter<File> {

    private List<Integer> mPos = new ArrayList<Integer>();

    public FileListAdapter(Context context, List<File> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public void setAllCheck(boolean isAllCheck) {
        if (isAllCheck) {
            for (int i = 0; i < mDatas.size(); i++) {
                if(mPos.contains(i)){

                }else {
                    mPos.add(i);
                }

            }
        } else {
            mPos.clear();
        }
        notifyDataSetChanged();
    }

    public List<File> getCheckFiles() {
        List<File> checkFiles = new ArrayList<File>();
        for (int i = 0; i < mPos.size(); i++) {
            checkFiles.add(mDatas.get(mPos.get(i)));
        }
        return checkFiles;
    }

    public void updateData(){
        List<File> files = new ArrayList<File>();
        for (int i = 0; i < mPos.size(); i++) {
            files.add(mDatas.get(mPos.get(i)));

        }
        mDatas.removeAll(files);
        mPos.clear();
        notifyDataSetChanged();
    }

    public void setmPos(int position){
        if (mPos.contains(position)) {
            mPos.remove((Integer)position);
        } else {
            mPos.add(position);
        }
        notifyDataSetChanged();
    }


    @Override
    public void convert(final ViewHolder holder, File file) {
        holder.setText(R.id.item_file_name, file.getName());
        holder.setText(R.id.item_file_size, FileUtils.getAutoFileOrFilesSize(file.getAbsolutePath()));

        Button checked = holder.getView(R.id.item_file_image);
        if (mPos.contains(holder.getPosition())) {
            checked.setBackgroundResource(R.drawable.chexkbox_pressed);
        } else {
            checked.setBackgroundResource(R.drawable.chexkbox_normal);
        }

        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPos.contains(holder.getPosition())) {
                    mPos.remove((Integer) holder.getPosition());
                } else {
                    mPos.add(holder.getPosition());
                }
                notifyDataSetChanged();
            }
        });

    }
}
