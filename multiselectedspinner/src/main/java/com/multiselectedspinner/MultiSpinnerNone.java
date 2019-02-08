package com.multiselectedspinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by iblinfotech on 08/02/19.
 */

public class MultiSpinnerNone extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {
    private List<String> items;
    private boolean[] selected;
    private String defaultText = "None";
    private String spinnerTitle = "";
    private MultiSpinnerListener listener;

    public MultiSpinnerNone(Context context) {
        super(context);
    }

    public MultiSpinnerNone(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        TypedArray a = arg0.obtainStyledAttributes(arg1, R.styleable.MultiSpinnerSearch);
        int N = a.getIndexCount();

        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.MultiSpinnerSearch_hintText) {
                this.spinnerTitle = a.getString(attr);
//                this.defaultText = defaultText + this.spinnerTitle;
            }
        }

        a.recycle();
    }

    public MultiSpinnerNone(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        this.selected[which] = isChecked;
        if (which == 0 && selected[which]) {
            for (int j = 1; j < items.size(); ++j) {
                if (selected[j])
                    ((AlertDialog) dialog).getListView().setItemChecked(j, false);
                this.selected[j] = false;
            }
        } else if (selected[which]) {
            ((AlertDialog) dialog).getListView().setItemChecked(0, false);
            this.selected[0] = false;
        }
    }

    public void onCancel(DialogInterface dialog) {
        StringBuilder spinnerBuffer = new StringBuilder();

        for (int i = 0; i < this.items.size(); ++i) {
            if (i == 0 && selected[i]) {
                for (int j = 1; j < items.size(); ++j) {
                    this.selected[j] = false;
                    if (this.selected.length > 0) {
                        this.listener.onItemsSelected(this.selected);
                    }
                }
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

        ArrayAdapter<String> adapter = new ArrayAdapter(this.getContext(), R.layout.textview_for_spinner, new String[]{this.defaultText});
        this.setAdapter(adapter);
        this.onCancel((DialogInterface) null);
    }
}


