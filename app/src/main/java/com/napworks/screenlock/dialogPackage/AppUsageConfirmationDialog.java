package com.napworks.screenlock.dialogPackage;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;

import com.napworks.mohra.utilPackage.MyConstants;
import com.napworks.screenlock.R;
import com.napworks.screenlock.interfacePackage.ConfirmationInterface;

import java.util.Objects;


public class AppUsageConfirmationDialog implements View.OnClickListener {
    private Activity activity;
    private Dialog dialog;
//    private EditText name;
    private String id;
    private AppCompatTextView yes;
    private TextView title, message,stepsTextView;
    private String TAG = getClass().getSimpleName();
    ConfirmationInterface confirmationInterface;

    public AppUsageConfirmationDialog(Activity activity, ConfirmationInterface confirmationInterface) {
        this.activity = activity;
        this.confirmationInterface = confirmationInterface;
        dialog = new Dialog(this.activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
//        name = dialog.findViewById(R.id.name);
        yes = dialog.findViewById(R.id.yes);
        stepsTextView = dialog.findViewById(R.id.stepsTextView);
        title = dialog.findViewById(R.id.title);
        message = dialog.findViewById(R.id.message);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        yes.setOnClickListener(this);
        stepsTextView.setOnClickListener(this);
    }

    public void showDialog(String id, String title, String message) {
        if (!dialog.isShowing()) {
            dialog.show();
            this.title.setText(title);
            this.message.setText(message);
            this.id = id;
        }
    }

    public void hideDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                hideDialog();
                confirmationInterface.confirmationResponse(id, MyConstants.YES,activity);
                break;
                case R.id.stepsTextView:
                hideDialog();
                confirmationInterface.confirmationResponse(id, MyConstants.OPEN_STEPS_ACTIVITY,activity);
                break;

        }
    }
}
