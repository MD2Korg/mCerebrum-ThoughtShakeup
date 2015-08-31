package org.md2k.thoughtshakeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p/>
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p/>
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class ActivityDiaryOld extends Activity {
    private static final String TAG = ActivityDiaryOld.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_old);
        setTitle("Diary");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onResume(){
        super.onResume();
        loadHistory();
        loadFavorites();

    }
    TextView createTextView(String text, final long timestamp){
        TextView textView=new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.table_border);
        textView.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
        textView.setTag(timestamp);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDiaryOld.this, ActivityThoughtShow.class);
                intent.putExtra("timestamp", timestamp);
                startActivity(intent);
            }
        });

        textView.setPadding(32, 32, 32, 32);
        return textView;
    }
    void loadHistory(){
        TableLayout tableLayout=(TableLayout)findViewById(R.id.tablelayout_history);
        tableLayout.removeAllViews();
        ArrayList<HistoryData.DataPoint> datapoints=HistoryData.getInstance().get(false);
        for(int i=0;i<datapoints.size();i++){
            TableRow tableRow=new TableRow(this);
            tableRow.addView(createTextView(datapoints.get(i).thought,datapoints.get(i).timestamp));
            tableRow.addView(createTextView(datapoints.get(i).rephrase,datapoints.get(i).timestamp));
            tableLayout.addView(tableRow);
        }
    }

    void loadFavorites(){
        TableLayout tableLayout=(TableLayout)findViewById(R.id.tablelayout_favorites);
        tableLayout.removeAllViews();
        ArrayList<HistoryData.DataPoint> datapoints=HistoryData.getInstance().get(true);
        for(int i=0;i<datapoints.size();i++){
            TableRow tableRow=new TableRow(this);
            tableRow.addView(createTextView(datapoints.get(i).thought,datapoints.get(i).timestamp));
            tableRow.addView(createTextView(datapoints.get(i).rephrase,datapoints.get(i).timestamp));
            tableLayout.addView(tableRow);
        }
    }

    PopupMenu popup = null;

    @Override
    public void onStop() {
        super.onStop();
        if (popup != null) popup.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (popup == null) {
                    Window window = getWindow();
                    View v = window.getDecorView();
                    int resId = getResources().getIdentifier("home", "id", "android");
                    popup = new PopupMenu(getActionBar().getThemedContext(), v.findViewById(resId));
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.menu_options, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    NavUtils.navigateUpTo(ActivityDiaryOld.this, new Intent(ActivityDiaryOld.this, ActivityThoughtShakeup.class));
                                    break;
                                case R.id.action_supporting_literature:
                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    });
                }
                popup.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
