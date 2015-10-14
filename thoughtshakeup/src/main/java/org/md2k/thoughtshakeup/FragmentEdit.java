package org.md2k.thoughtshakeup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.md2k.datakitapi.time.DateTime;
import org.md2k.utilities.Report.Log;

import java.util.ArrayList;
import java.util.Random;


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

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class FragmentEdit extends FragmentBase {
    private static final String TAG = FragmentEdit.class.getSimpleName();
    EditText editTextThought;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FragmentEdit create(int pageNumber) {
        FragmentEdit fragment = new FragmentEdit();
        fragment.setArguments(getArgument(pageNumber));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        updateNext(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_edit, container, false);
        initializeUI(rootView);
        setEditTextThought(rootView);
        return rootView;
    }
    void setEditTextThought(ViewGroup rootView) {
        editTextThought = (EditText) rootView.findViewById(R.id.editText_thought);
        editTextThought.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String response = editTextThought.getText().toString().trim();
                    response = response.trim();
                    question.getQuestion_responses_selected().clear();
                    if (response.length() > 0) {
                        question.getQuestion_responses_selected().add(response);
                    }
                }
                return false;
            }
        });
        editTextThought.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.d(TAG, "Focus=" + b);
                if (b)
                    setEditTextFocused();
                else setEditTextNotFocused();

            }
        });
    }

    void setEditTextFocused() {
        if (editTextThought.getText().toString().trim().equals(Constants.TAP)) {
            editTextThought.setText("");
        }
        editTextThought.setTextColor(getResources().getColor(R.color.BLACK));
    }

    void setEditTextNotFocused() {
        String text=editTextThought.getText().toString().trim();
        if (text.length() == 0) {
            editTextThought.setText(Constants.TAP);
            editTextThought.setTextColor(getResources().getColor(R.color.BLACK));
        }
    }
    @Override
    public void onPause(){
        if(!editTextThought.getText().toString().trim().equals(Constants.TAP) && editTextThought.getText().toString().trim().length()!=0) {
            question.getQuestion_responses_selected().clear();
            question.getQuestion_responses_selected().add(editTextThought.getText().toString().trim());
        }
        hideKeyboard();
        super.onPause();
    }
}
