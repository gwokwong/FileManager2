package com.wells.filemanager.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.wells.filemanager.R;

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
        if (mPos.contains((Integer)position)) {
            mPos.remove((Integer) position);
        } else {
            mPos.add((Integer)position);
        }
        notifyDataSetChanged();
    }

    public void setAllCheck(boolean isAllCheck) {
        if (isAllCheck) {
            Log.v("info","setallcheck size"+mDatas.size());
            for (int i = 0; i < mDatas.size(); i++) {
                mPos.add((Integer)i);
            }

            for(int j=0;j<mPos.size();j++){
                Log.v("info","mPos "+mPos.get(j).toString());

            }
        } else {
            Log.v("info","setallcheck size"+mDatas.size());
            for (int i = 0; i < mDatas.size(); i++) {
                if(mPos.contains((Integer)i)){
                    mPos.remove((Integer)i);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public void convert(final ViewHolder holder, File file) {
        holder.setText(R.id.item_file_name, file.getName());

        final CheckBox cb = holder.getView(R.id.item_file_checkbox);
        if (mPos.contains((Integer)holder.getPosition())) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    if(!mPos.contains((Integer)holder.getPosition())){
                        mPos.add((Integer)holder.getPosition());
                    }else{
                        mPos.remove((Integer)holder.getPosition());
                    }
                }else {
                    if(mPos.contains((Integer) holder.getPosition())){
                        mPos.remove((Integer)holder.getPosition());
                    }
                }
                notifyDataSetChanged();
            }
        });

    }
}
