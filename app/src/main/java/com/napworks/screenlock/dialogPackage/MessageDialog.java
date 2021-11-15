package com.napworks.screenlock.dialogPackage;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.widget.AppCompatTextView;

import com.napworks.screenlock.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDialog implements View.OnClickListener {
    private Activity activity;
    private Dialog dialog;
    @BindView(R.id.ok)
    AppCompatTextView ok;
    @BindView(R.id.message)
    AppCompatTextView message;

    public MessageDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(this.activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.message_dialog_layout);
        ButterKnife.bind(this, dialog);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        ok.setOnClickListener(this);
    }
    public void showDialog(String message) {
        if (!dialog.isShowing()) {
            this.message.setText(message);
            dialog.show();
        }
    }
    public void hideDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ok) {
            hideDialog();
        }
    }
}
