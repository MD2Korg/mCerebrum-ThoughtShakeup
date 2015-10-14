package org.md2k.thoughtshakeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;

import org.md2k.datakitapi.messagehandler.OnConnectionListener;
import org.md2k.utilities.Report.Log;
import org.md2k.utilities.UI.UIShow;
import org.md2k.utilities.datakit.DataKitHandler;

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

public class ActivityThoughtShakeup extends Activity {
    DataKitHandler dataKitHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_shakeup);
        if (!connectDataKit()) {
            UIShow.ErrorDialog(this, "DataKit Error", "DataKit is not available.\n\nPlease Install DataKit");
        }

        Button button;
        button = (Button) findViewById(R.id.button_shakeup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityThoughtShakeup.this, ActivityExercise.class);
                Questions.getInstance().clear();
                startActivity(intent);
            }
        });
        button = (Button) findViewById(R.id.button_history);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityThoughtShakeup.this, ActivityHistory.class);
                startActivity(intent);
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean connectDataKit() {
        if (dataKitHandler == null)
            dataKitHandler = DataKitHandler.getInstance(getApplicationContext());
        return dataKitHandler.isConnected() || dataKitHandler.connect(new OnConnectionListener() {
            @Override
            public void onConnected() {
            }
        });
    }

    @Override
    public void onDestroy() {
        if (dataKitHandler.isConnected())
            dataKitHandler.disconnect();
        dataKitHandler.close();
        dataKitHandler = null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Window window = getWindow();
                View v = window.getDecorView();
                int resId = getResources().getIdentifier("home", "id", "android");
//                return v.findViewById(resId);
                PopupMenu popup = new PopupMenu(getActionBar().getThemedContext(), v.findViewById(resId));
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_options, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                return true;
                            case R.id.action_supporting_literature:
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();//showing popup menu

                //              NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
