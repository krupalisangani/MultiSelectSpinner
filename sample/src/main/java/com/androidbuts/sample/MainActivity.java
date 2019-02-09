package com.androidbuts.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.multiselectedspinner.KeyPairBoolData;
import com.multiselectedspinner.MultiSpinnerListener;
import com.multiselectedspinner.MultiSpinnerNone;
import com.multiselectedspinner.MultiSpinnerSearch;
import com.multiselectedspinner.MultiSpinnerSelect;
import com.multiselectedspinner.SingleSpinner;
import com.multiselectedspinner.SingleSpinnerSearch;
import com.multiselectedspinner.SpinnerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    LinkedHashMap<String, Boolean> items = new LinkedHashMap<>();
    List<String> list;
    List<String> Nonelist = new ArrayList<>();
    private String select_category = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Getting array of String to Bind in Spinner
         */
         list = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        Nonelist.addAll(list);
        final List<KeyPairBoolData> listArray0 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }

        final List<KeyPairBoolData> listArray1 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray1.add(h);
        }

        final List<KeyPairBoolData> listArray2 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray2.add(h);
        }
        final List<KeyPairBoolData> listArray3 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray3.add(h);
        }
        /**
         * Simple MultiSelection Spinner (Without Search/Filter Functionality)
         *
         *  Using MultiSpinner class
         */
        /*MultiSpinner simpleSpinner = (MultiSpinner) findViewById(R.id.simpleMultiSpinner);

        simpleSpinner.setItems(listArray0, -1, new MultiSpinnerListener() {

            @Override
            public void onItemsSelected(boolean[] selected) {
            }

        });*/

        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        MultiSpinnerSearch searchMultiSpinnerUnlimited = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerUnlimited);
        MultiSpinnerSearch searchMultiSpinnerLimit = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerLimit);
        SingleSpinnerSearch searchSingleSpinner = (SingleSpinnerSearch) findViewById(R.id.searchSingleSpinner);
        SingleSpinner singleSpinner = (SingleSpinner) findViewById(R.id.singleSpinner);
        MultiSpinnerNone noneSpinner = (MultiSpinnerNone) findViewById(R.id.noneSpinner);
        MultiSpinnerSelect selectSpinner = (MultiSpinnerSelect) findViewById(R.id.selectSpinner);

        searchMultiSpinnerUnlimited.setItems(listArray0, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        /***
         * -1 is no by default selection
         * 0 to length will select corresponding values
         */
        searchMultiSpinnerLimit.setItems(listArray1, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        searchMultiSpinnerLimit.setLimit(2, new MultiSpinnerSearch.LimitExceedListener() {
            @Override
            public void onLimitListener(KeyPairBoolData data) {
                Toast.makeText(getApplicationContext(),
                        "Limit exceed ", Toast.LENGTH_LONG).show();
            }
        });

        searchSingleSpinner.setItems(listArray2, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        singleSpinner.setItems(listArray3, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });

        items = new LinkedHashMap<>();
        Nonelist.add(0,"None");
        for (int i = 0; i < Nonelist.size(); i++) {
            items.put(Nonelist.get(i), false);
        }

        noneSpinner.setItems(items, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        Log.i(TAG, i + " : " + Nonelist.get(i) + " : " +selected[i]);
                    }
                }
            }
        });

        items = new LinkedHashMap<>();
        Nonelist.remove(0);
        Nonelist.add(0,"Select All");
        for (int i = 0; i < Nonelist.size(); i++) {
            items.put(Nonelist.get(i), true);
        }

        selectSpinner.setItems(items, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                Log.e("SELECT", "----select---" + selected);
                select_category = "";
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        if (i == 0) {
                            select_category = "SelectAll";
                        } else {
                            if (select_category.equals("")) {
                                select_category = Nonelist.get(i);
                            } else {
                                select_category = select_category + "," + Nonelist.get(i);
                            }
                        }
                        Log.e("SELECT", select_category + " : " + select_category);
                    }
                }

                if (select_category.equals("")) {
                    select_category = "SelectAll";
                }

            }
        });

    }
}
