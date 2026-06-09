package sksa.aa.tweaker.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * AlertDialog wrapper preserving the original BottomDialog Builder API.
 * Replaces the javiersantos/BottomDialogs external library — no layout resources needed.
 */
public class BottomDialog {

    private final Dialog mDialog;

    BottomDialog(Dialog dialog) {
        mDialog = dialog;
    }

    @UiThread
    public void show() {
        if (mDialog != null) mDialog.show();
    }

    @UiThread
    public void dismiss() {
        if (mDialog != null) mDialog.dismiss();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        if (mDialog != null) mDialog.setOnDismissListener(listener);
    }

    public static class Builder {
        private final Context context;
        private CharSequence title, content;
        private CharSequence btn_positive, btn_negative;
        private ButtonCallback positiveCallback, negativeCallback;
        private boolean isCancelable = true;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setTitle(@StringRes int res) { title = context.getString(res); return this; }
        public Builder setTitle(@NonNull CharSequence t) { title = t; return this; }
        public Builder setContent(@StringRes int res) { content = context.getString(res); return this; }
        public Builder setContent(@NonNull CharSequence c) { content = c; return this; }
        public Builder setIcon(@NonNull Drawable d) { return this; }
        public Builder setIcon(@DrawableRes int res) { return this; }
        public Builder setBackgroundColor(int colorRes) { return this; }
        public Builder setShadowHeight(int heightDp) { return this; }
        public Builder setPositiveBackgroundColorResource(@ColorRes int res) { return this; }
        public Builder setPositiveBackgroundColor(int color) { return this; }
        public Builder setPositiveTextColorResource(@ColorRes int res) { return this; }
        public Builder setPositiveTextColor(int color) { return this; }
        public Builder setPositiveText(@StringRes int res) { btn_positive = context.getString(res); return this; }
        public Builder setPositiveText(@NonNull CharSequence t) { btn_positive = t; return this; }
        public Builder onPositive(@NonNull ButtonCallback cb) { positiveCallback = cb; return this; }
        public Builder setNegativeTextColorResource(@ColorRes int res) { return this; }
        public Builder setNegativeTextColor(int color) { return this; }
        public Builder setNegativeText(@StringRes int res) { btn_negative = context.getString(res); return this; }
        public Builder setNegativeText(@NonNull CharSequence t) { btn_negative = t; return this; }
        public Builder onNegative(@NonNull ButtonCallback cb) { negativeCallback = cb; return this; }
        public Builder setCancelable(boolean cancelable) { isCancelable = cancelable; return this; }
        public Builder autoDismiss(boolean dismiss) { return this; }
        public Builder setCustomView(View v) { return this; }
        public Builder setCustomView(View v, int l, int t, int r, int b) { return this; }

        @UiThread
        public BottomDialog build() {
            AlertDialog.Builder ab = new AlertDialog.Builder(context);
            if (title != null) ab.setTitle(title);
            if (content != null) ab.setMessage(content);
            ab.setCancelable(isCancelable);
            final BottomDialog[] ref = {null};
            if (btn_positive != null) {
                final ButtonCallback cb = positiveCallback;
                ab.setPositiveButton(btn_positive, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface d, int which) {
                        if (cb != null && ref[0] != null) cb.onClick(ref[0]);
                    }
                });
            }
            if (btn_negative != null) {
                final ButtonCallback cb = negativeCallback;
                ab.setNegativeButton(btn_negative, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface d, int which) {
                        if (cb != null && ref[0] != null) cb.onClick(ref[0]);
                    }
                });
            }
            BottomDialog bd = new BottomDialog(ab.create());
            ref[0] = bd;
            return bd;
        }

        @UiThread
        public BottomDialog show() {
            BottomDialog bd = build();
            bd.show();
            return bd;
        }
    }

    public interface ButtonCallback {
        void onClick(@NonNull BottomDialog dialog);
    }
}
