package com.wells.filemanager.File;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.wells.filemanager.R;
import com.wells.filemanager.adapter.CommonAdapter;
import com.wells.filemanager.adapter.ViewHolder;

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

    public void setmPos(Integer position) {
        if (mPos.contains(position)) {
            mPos.remove((Integer) position);
        } else {
            mPos.add(position);
        }
        notifyDataSetChanged();
    }

    public void setAllCheck(boolean isAllCheck) {
        if (isAllCheck) {
            for (int i = 0; i < mDatas.size(); i++) {
                mPos.add(i);
            }
        } else {
            for (int i = 0; i < mDatas.size(); i++) {
                mPos.remove(i);
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public void convert(final ViewHolder holder, File file) {
        holder.setText(R.id.item_file_name, file.getName());

        final CheckBox cb = holder.getView(R.id.item_file_checkbox);
        if (mPos.contains(holder.getPosition())) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    mPos.add(holder.getPosition());
                }else {
                    mPos.remove(holder.getPosition());
                }
                notifyDataSetChanged();
            }
        });

    }
}
