package org.md2k.thoughtshakeup;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
public class FragmentTextReview extends FragmentBase {
    private static final String TAG = FragmentTextReview.class.getSimpleName();
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    LinearLayout layoutTextReview;

    public static FragmentTextReview create(int pageNumber) {
        FragmentTextReview fragment = new FragmentTextReview();
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
                .inflate(R.layout.fragment_screen_text_review, container, false);
        initializeUI(rootView);
        rootView.findViewById(R.id.linearLayoutThought).setVisibility(LinearLayout.GONE);
        layoutTextReview = (LinearLayout) rootView.findViewById(R.id.layoutTextReview);
        for (int qno = 0; qno < question.getQuestion_id(); qno++) {
            Question curQuestion = Questions.getInstance().getQuestion(qno);
            if (curQuestion.getQuestion_id() == 10) {
                if(curQuestion.getQuestion_responses_selected().size()!=4) continue;
                layoutTextReview.addView(createTextView(curQuestion.getShortenText(), 0));
                layoutTextReview.addView(createTextView("&#8226; My thought is correct because <b>" + curQuestion.getQuestion_responses_selected().get(0) + "</b>", 40));
                layoutTextReview.addView(createTextView("&#8226; Additionally, my thought is accurate because <b>" + curQuestion.getQuestion_responses_selected().get(1) + "</b>", 40));
                layoutTextReview.addView(createTextView("&#8226; On the other hand, my though may be inaccurate because <b>" + curQuestion.getQuestion_responses_selected().get(2) + "</b>", 40));
                layoutTextReview.addView(createTextView("&#8226; Still, my thought may be inaccurate because <b>" + curQuestion.getQuestion_responses_selected().get(3) + "</b>", 40));
            } else if(curQuestion.getQuestion_id()==1){
                if(curQuestion.getQuestion_responses_selected().size()==0){
                    ArrayList<String> answer=new ArrayList<>();
                    answer.add("(none)");
                    curQuestion.setQuestion_responses_selected(answer);
                    addTextView(curQuestion);
                    answer.clear();
                    curQuestion.setQuestion_responses_selected(answer);
                }
                else
                    addTextView(curQuestion);
                addTextView("<i><u><b>You noted that:</b></u></i>");
            }
            else
                addTextView(curQuestion);
        }
        return rootView;
    }

    TextView createTextView(String text, int padLeft) {
        if (text == null) return null;
        TextView textView = new TextView(getActivity());
        textView.setTextSize(16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setText(Html.fromHtml(text));
        textView.setPadding(padLeft, 0, 0, 0);
        return textView;
    }

    TextView addQuestionText(Question question) {
        String questionText = question.getShortenText();
        if (questionText == null) return null;
        if (question.getQuestion_responses_selected().size() == 0) return null;
        if (questionText.contains("(answer)")) {
            questionText = questionText.replace("(answer)", question.getQuestion_responses_selected().get(0));
        }
        return createTextView(questionText, 40);
    }

    TextView addQuestionResponse(Question question) {
        TextView textViewQuestionResponse = new TextView(getActivity());
        textViewQuestionResponse.setTextAppearance(getActivity(), android.R.style.TextAppearance_DeviceDefault_Small);
        textViewQuestionResponse.setTypeface(null, Typeface.BOLD);
        textViewQuestionResponse.setPadding(0, 0, 0, 20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewQuestionResponse.setLayoutParams(params);
        String response = "Answer: ";

        for (int i = 0; i < question.getQuestion_responses_selected().size(); i++) {
            if (i != 0) response += ";";
            response += question.getQuestion_responses_selected().get(i);
        }
        textViewQuestionResponse.setText(response);
        return textViewQuestionResponse;
    }

    void addTextView(Question question) {
        TextView tv = addQuestionText(question);
        if (tv != null)
            layoutTextReview.addView(tv);
    }

    void addTextView(String text) {
        TextView tv = createTextView(text, 0);
        if (tv != null)
            layoutTextReview.addView(tv);
    }

}
