package com.napworks.screenlock.utilPackage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;

import com.napworks.screenlock.R;


public class LoadingDialog {
    private Dialog dialog;
    public LoadingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_progress_layout);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
    }
    public void showDialog() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
    public void hideDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
