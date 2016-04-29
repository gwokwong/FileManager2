package com.wells.filemanager.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wells.filemanager.R;

public class ProgressWheelDialog extends ProgressDialog {
	private Context mContext;
	private static ProgressWheelDialog instance;
	private ProgressWheel progressWheel;
	private TextView loadingTxt;
	private static String message;

	private ProgressWheelDialog(Context context) {
		super(context);
		this.mContext = context;
	}

//	public ProgressWheelDialog setLoadingMsg(String msg){
//		loadingTxt.setText(msg);
//		return instance;
//	}

	public static ProgressWheelDialog getInstance(Context context,String msg) {
		message = msg;
		return getInstance(context);
	}



	public static ProgressWheelDialog getInstance(Context context) {
		if (instance == null) {
			instance = new ProgressWheelDialog(context);
		}
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_progress);
		progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
		loadingTxt = (TextView)findViewById(R.id.msg);
		final int defaultBarColor = progressWheel.getBarColor();
		progressWheel.setBarColor(defaultBarColor);
		setGravity();

	}

	private void setGravity() {
		if (null != instance) {
			Window dialogWindow = instance.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			lp.x = 0;
			lp.y = 0;
			lp.width = mContext.getResources().getDimensionPixelSize(R.dimen.loading_dialog_width);
			lp.height = mContext.getResources().getDimensionPixelSize(R.dimen.loading_dialog_height);
			dialogWindow.setAttributes(lp);
			instance.setCanceledOnTouchOutside(false); // 设置外部不可点击
			instance.setCancelable(false);
		}
	}

	@Override
	public void dismiss() {
		if (instance != null) {
			try {
				super.dismiss();
				instance = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void show() {
		try {
			if (!instance.isShowing() && null != instance) {
				super.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
