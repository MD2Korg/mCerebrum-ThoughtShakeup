package org.md2k.thoughtshakeup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.md2k.utilities.Report.Log;

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
public class ActivityHistory extends Activity {
    private static final String TAG = ActivityHistory.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("History");
        if(getActionBar()!=null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onResume(){
        super.onResume();
        loadHistory();
    }
    TextView createTextView(String text, final long timestamp){
        TextView textView=new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
        if(timestamp!=-1) {
            textView.setBackgroundResource(R.drawable.table_border);
            textView.setTag(timestamp);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityHistory.this, ActivityThoughtShow.class);
                    intent.putExtra("timestamp", timestamp);
                    startActivity(intent);
                }
            });
        }else{
            textView.setTypeface(null, Typeface.BOLD);
            textView.setBackgroundResource(R.drawable.table_border_header);
        }

        textView.setPadding(32, 32, 32, 32);
        return textView;
    }

    void loadHistory(){
        TableLayout tableLayout=(TableLayout)findViewById(R.id.tablelayout_history);
        tableLayout.removeAllViews();
        TableRow tableRowHeader=new TableRow(this);
        tableRowHeader.addView(createTextView("Original Thoughts",-1));
        tableLayout.addView(tableRowHeader);

        ArrayList<DataPoint> datapoints=HistoryData.getInstance().get(false);
        for(int i=0;i<datapoints.size();i++){
            TableRow tableRow=new TableRow(this);
            tableRow.addView(createTextView(datapoints.get(i).thought,datapoints.get(i).timestamp));
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
                    popup.getMenuInflater().inflate(R.menu.menu_history_clear, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    NavUtils.navigateUpTo(ActivityHistory.this, new Intent(ActivityHistory.this, ActivityThoughtShakeup.class));
                                    break;
                                case R.id.action_history_clear:
                                    buildAlertMessageHistoryClear();
                                    break;
                                case R.id.action_supporting_literature:
                                    Intent intentL=new Intent(ActivityHistory.this, ActivityLiterature.class);
                                    startActivity(intentL);
                                    break;
                                case R.id.action_exit:
                                    finish();
                                    break;
                                case R.id.action_about:
                                    Intent intent=new Intent(ActivityHistory.this, ActivityAbout.class);
                                    startActivity(intent);
                                    break;
                                case R.id.action_copyright:
                                    Intent intentC=new Intent(ActivityHistory.this, ActivityCopyright.class);
                                    startActivity(intentC);
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
    private void buildAlertMessageHistoryClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to clear the history?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        HistoryData.getInstance().clear();
                        Toast.makeText(ActivityHistory.this, "History is cleared", Toast.LENGTH_SHORT).show();
                        loadHistory();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
