package org.md2k.thoughtshakeup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class FragmentEditSpecial extends FragmentBase {
    private static final String TAG = FragmentEditSpecial.class.getSimpleName();
    final int ITERATION = 4;
    int iteration;
    EditText editTextThought;
    RelativeLayout layoutEditTextSpecial;
    TextView textViewThoughtCorrectIncorrect;
    String EVENODD[] = {"My thought is correct because...", "On the other hand, my thought may be inaccurate because..."};

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FragmentEditSpecial create(int pageNumber) {
        FragmentEditSpecial fragment = new FragmentEditSpecial();
        fragment.setArguments(getArgument(pageNumber));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void updateUI() {
        if (iteration >= ITERATION) {
            editTextThought.setVisibility(View.GONE);
            textViewThoughtCorrectIncorrect.setVisibility(View.GONE);
            hideKeyboard();
            updateNext(true);
        } else {
            updateNext(false);
            if (editTextThought.isFocused())
                setEditTextFocused();
            else setEditTextNotFocused();

            textViewThoughtCorrectIncorrect.setText(EVENODD[iteration % 2]);
            textViewThoughtCorrectIncorrect.setTextColor(iteration % 2 == 0 ? getResources().getColor(R.color.blue_400) : getResources().getColor(R.color.orange_600));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_edit_special, container, false);
        initializeUI(rootView);
        setEditTextThought(rootView);
        layoutEditTextSpecial = (RelativeLayout) rootView.findViewById(R.id.layoutEditTextSpecial);
        textViewThoughtCorrectIncorrect = (TextView) rootView.findViewById(R.id.textViewThoughtCorrectIncorrect);
        for (int i = 0; i < question.getQuestion_responses_selected().size(); i++)
            addTextToBubble(i);
        iteration = question.getQuestion_responses_selected().size();
        updateUI();
        return rootView;
    }

    void setEditTextThought(ViewGroup rootView) {
        editTextThought = (EditText) rootView.findViewById(R.id.editText_thought);
        editTextThought.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String response = editTextThought.getText().toString();
                    response = response.trim();
                    if (response.length() > 0) {
                        question.getQuestion_responses_selected().add(response);
                        addTextToBubble(iteration);
                        iteration++;
                        editTextThought.setText("");
                        updateUI();
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
        if (editTextThought.getText().toString().equals(Constants.TAP)) {
            editTextThought.setText("");
        }
        editTextThought.setTextColor(getResources().getColor(R.color.BLACK));
    }

    void setEditTextNotFocused() {
        if (editTextThought.getText().toString().length() == 0) {
            editTextThought.setText(Constants.TAP);
            editTextThought.setTextColor(getResources().getColor(R.color.BLACK));
        }
    }


    void addTextToBubble(int curIteration) {
        TextView textView = new TextView(getActivity());
        textView.setTextAppearance(getActivity(), android.R.style.TextAppearance_DeviceDefault_Medium);
        textView.setId(curIteration + 1);
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParam.addRule(curIteration % 2 == 0 ? RelativeLayout.ALIGN_PARENT_LEFT : RelativeLayout.ALIGN_PARENT_RIGHT);
        if (curIteration > 0) layoutParam.addRule(RelativeLayout.BELOW, curIteration);
        textView.setLayoutParams(layoutParam);
        String response = question.getQuestion_responses_selected().get(curIteration);
        textView.setText(EVENODD[curIteration % 2] + response);
        textView.setBackgroundResource(curIteration % 2 == 0 ? R.drawable.correct : R.drawable.incorrect);
        layoutEditTextSpecial.addView(textView);
    }
}
