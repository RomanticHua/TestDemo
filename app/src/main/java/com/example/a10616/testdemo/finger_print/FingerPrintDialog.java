package com.example.a10616.testdemo.finger_print;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.example.a10616.testdemo.R;

public class FingerPrintDialog extends DialogFragment {

    private OnFingerPrintDialogListener listener;

    public OnFingerPrintDialogListener getListener() {
        return listener;
    }

    public void setListener(OnFingerPrintDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_finger_printer, null);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });
        dialog.setContentView(view);

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null) {
            listener.onDismiss();
        }
    }

    public interface OnFingerPrintDialogListener {
        void onDismiss();
    }
}
