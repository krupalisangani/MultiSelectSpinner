package com.androidbuts.multispinnerfilter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by iblinfotech on 08/02/19.
 */

public class MultiSpinnerSelect extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {
    private List<String> items;
    private boolean[] selected;
    private String defaultText = "SelectAll";
    private String spinnerTitle = "";
    private MultiSpinnerListener listener;

    public MultiSpinnerSelect(Context context) {
        super(context);
    }

    public MultiSpinnerSelect(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        TypedArray a = arg0.obtainStyledAttributes(arg1, R.styleable.MultiSpinnerSearch);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.MultiSpinnerSearch_hintText) {
                spinnerTitle = a.getString(attr);
            }
        }
        a.recycle();
    }


    public MultiSpinnerSelect(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

//    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//        this.selected[which] = isChecked;
//        if (which == 0 && selected[which]) {
//            for (int j = 1; j < items.size(); ++j) {
//                if (selected[j])
//                    ((AlertDialog) dialog).getListView().setItemChecked(j, false);
//                this.selected[j] = false;
//            }
//        } else if (selected[which]) {
//            ((AlertDialog) dialog).getListView().setItemChecked(0, false);
//            this.selected[0] = false;
//        }
//    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        this.selected[which] = isChecked;
        if (which == 0 && selected[which]) {
            for (int j = 1; j < items.size(); ++j) {
                if (selected[j])
                    Log.e("CHECKBOX", "---check--box--if-pos--00-" + selected[j]);
                ((AlertDialog) dialog).getListView().setItemChecked(j, true);
                this.selected[j] = true;
            }
        } else if (which == 0 && !selected[which]) {
            for (int j = 1; j < items.size(); ++j) {
                if (selected[j])
                    ((AlertDialog) dialog).getListView().setItemChecked(j, false);
                this.selected[j] = false;
            }
        } else if (selected[which]) {
            ((AlertDialog) dialog).getListView().setItemChecked(0, false);
            this.selected[0] = false;
        } else if (!selected[which]) {
            ((AlertDialog) dialog).getListView().setItemChecked(0, false);
            this.selected[0] = false;
        }
    }

    public void onCancel(DialogInterface dialog) {
        StringBuilder spinnerBuffer = new StringBuilder();

        try {
            for (int i = 0; i < this.items.size(); ++i) {
                if (i == 0 && selected[i]) {
//                    for (int j = 1; j < items.size(); ++j) {
//                        this.selected[j] = true;
//                        if (this.selected.length > 0) {
//                            this.listener.onItemsSelected(this.selected);
//                        }
//                    }
                    spinnerBuffer.delete(0, spinnerBuffer.length());
                    spinnerBuffer.append((String) this.items.get(i));
                    spinnerBuffer.append(", ");
                    break;
                } else {
                    if (this.selected[i]) {
                        spinnerBuffer.append((String) this.items.get(i));
                        spinnerBuffer.append(", ");
                    }
                }
            }

            String spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2) {
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
            } else {
                spinnerText = this.defaultText;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter(this.getContext(), R.layout.textview_for_spinner, new String[]{spinnerText});
            this.setAdapter(adapter);
            if (this.selected.length > 0) {
                this.listener.onItemsSelected(this.selected);
            }
//            for (int j = 1; j < items.size(); ++j) {
//                this.selected[j] = true;
//                if (((AlertDialog) dialog).getListView() != null)
//                    ((AlertDialog) dialog).getListView().setItemChecked(j, true);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean performClick() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext(), R.style.myDialog);
        builder.setTitle(this.spinnerTitle);
        builder.setMultiChoiceItems((CharSequence[]) this.items.toArray(new CharSequence[this.items.size()]), this.selected, this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(LinkedHashMap<String, Boolean> items, MultiSpinnerListener listener) {
        this.items = new ArrayList(items.keySet());
        this.listener = listener;
        List<Boolean> values = new ArrayList(items.values());
        this.selected = new boolean[values.size()];

        for (int i = 0; i < items.size(); ++i) {
            this.selected[i] = ((Boolean) values.get(i)).booleanValue();
        }

//        if (values.size() > 0)
//            this.selected[0] = true;

        ArrayAdapter<String> adapter = new ArrayAdapter(this.getContext(), R.layout.textview_for_spinner, new String[]{this.defaultText});
        this.setAdapter(adapter);
        this.onCancel((DialogInterface) null);

    }
}
