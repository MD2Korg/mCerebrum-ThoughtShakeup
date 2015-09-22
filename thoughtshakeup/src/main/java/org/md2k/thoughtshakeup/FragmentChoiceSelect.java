package org.md2k.thoughtshakeup;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
public class FragmentChoiceSelect extends FragmentBase {
    private static final String TAG = FragmentChoiceSelect.class.getSimpleName();

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FragmentChoiceSelect create(int pageNumber) {
        FragmentChoiceSelect fragment = new FragmentChoiceSelect();
        fragment.setArguments(getArgument(pageNumber));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void setTypeMultipleChoiceSelect(ViewGroup rootView, Question question) {
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_multiple_choice);
        if(question.getQuestion_responses().size()>3)
            ll.setOrientation(LinearLayout.VERTICAL);
        else ll.setOrientation(LinearLayout.HORIZONTAL);
        ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
        CompoundButton.OnCheckedChangeListener listener;
        for (int i = 0; i < question.getQuestion_responses().size(); i++) {
            ToggleButton toggleButton = addToggleButtons(question, i);
            if (question.isType(Questions.MULTIPLE_CHOICE))
                listener = setOnCheckedListenerMultipleChoice(question, toggleButtons);
            else listener = setOnCheckedListenerMultipleSelect(question, toggleButtons);
            toggleButton.setOnCheckedChangeListener(listener);
            toggleButtons.add(toggleButton);
            if (i != 0) ll.addView(addView());
            ll.addView(toggleButton);
        }
    }

    public boolean isAnswered() {
        if (question.getQuestion_type() == null) return true;
        if (question.getQuestion_type().equals(Questions.MULTIPLE_SELECT) ||
                question.getQuestion_type().equals(Questions.MULTIPLE_CHOICE)) {
            if (question.getQuestion_responses_selected().size() > 0)
                return true;
            else return false;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        updateNext(isAnswered());
    }

    CompoundButton.OnCheckedChangeListener setOnCheckedListenerMultipleSelect(final Question question, final ArrayList<ToggleButton> toggleButtons) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false)
                    question.getQuestion_responses_selected().remove(buttonView.getText().toString());
                else if (buttonView.getText().equals("None of the above")) {
                    for (int i = 0; i < toggleButtons.size(); i++) {
                        if (!toggleButtons.get(i).getText().equals(buttonView.getText()))
                            toggleButtons.get(i).setChecked(false);
                    }
                    question.getQuestion_responses_selected().clear();
                    question.getQuestion_responses_selected().add(buttonView.getText().toString());
                } else {
                    for (int i = 0; i < toggleButtons.size(); i++)
                        if (toggleButtons.get(i).getText().equals("None of the above")) {
                            toggleButtons.get(i).setChecked(false);
                            question.getQuestion_responses_selected().remove(toggleButtons.get(i).getText().toString());
                        }
                    question.getQuestion_responses_selected().add(buttonView.getText().toString());
                }
                updateNext(isAnswered());
            }
        };
    }

    CompoundButton.OnCheckedChangeListener setOnCheckedListenerMultipleChoice(final Question question, final ArrayList<ToggleButton> toggleButtons) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    question.getQuestion_responses_selected().remove(buttonView.getText().toString());
                else {
                    for (int i = 0; i < toggleButtons.size(); i++) {
                        if (!toggleButtons.get(i).getText().equals(buttonView.getText()))
                            toggleButtons.get(i).setChecked(false);
                    }
                    question.getQuestion_responses_selected().clear();
                    question.getQuestion_responses_selected().add(buttonView.getText().toString());
                }
                updateNext(isAnswered());
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_choice_select, container, false);
        initializeUI(rootView);

        if (question.isType(Questions.MULTIPLE_CHOICE) || question.isType(Questions.MULTIPLE_SELECT)) {
            ((TextView)rootView.findViewById(R.id.textViewTap)).setText("Tap to select");
            setTypeMultipleChoiceSelect(rootView, question);
        }
        else{
            ((TextView)rootView.findViewById(R.id.textViewTap)).setVisibility(View.GONE);
        }
        return rootView;
    }


    private ToggleButton addToggleButtons(final Question question, int response_id) {
        ToggleButton toggleButton = new ToggleButton(this.getActivity());
        String option = question.getQuestion_responses().get(response_id);
        toggleButton.setTextOn(option);
        toggleButton.setTextOff(option);
        toggleButton.setText(option);
        if (question.isResponseExist(option))
            toggleButton.setChecked(true);
        else toggleButton.setChecked(false);
        return toggleButton;
    }
}
