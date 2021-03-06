package org.md2k.thoughtshakeup;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.md2k.datakitapi.time.DateTime;
import org.md2k.utilities.Report.Log;


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
public class FragmentBase extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    private static final String TAG = FragmentBase.class.getSimpleName();
    Menu menu=null;

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    protected int mPageNumber;
    Question question = null;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    protected static Bundle getArgument(int pageNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        return args;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"FragmentBase-> onCreate()");
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        question = Questions.getInstance().getQuestion(mPageNumber);
        setHasOptionsMenu(true);
    }
    void setThoughts(ViewGroup rootView){
        if(question.getThoughts()==null){
            LinearLayout ll= (LinearLayout) rootView.findViewById(R.id.linearLayoutThought);
            ll.setVisibility(LinearLayout.INVISIBLE);
        }
        else if(question.getThoughts().equals(Questions.THOUGHT)|| question.getThoughts().equals(Questions.ORIGINAL_THOUGHT)){
            ((TextView) rootView.findViewById(R.id.textViewThoughtTitle)).setText(question.getThoughts());
            String thoughts="(none)";
            if(Questions.getInstance().getQuestion(1).getQuestion_responses_selected().size()>0)
                thoughts=Questions.getInstance().getQuestion(1).getQuestion_responses_selected().get(0);
            ((TextView) rootView.findViewById(R.id.textViewThoughtDesc)).setText(thoughts);
        }
        else if(question.getThoughts().equals(Questions.REPHRASED_THOUGHT)){
            String thoughts="(none)";
            if(Questions.getInstance().getQuestion(12).getQuestion_responses_selected().size()>0)
                thoughts=Questions.getInstance().getQuestion(12).getQuestion_responses_selected().get(0);
            ((TextView) rootView.findViewById(R.id.textViewThoughtTitle)).setText(Questions.REPHRASED_THOUGHT);
            ((TextView) rootView.findViewById(R.id.textViewThoughtDesc)).setText(thoughts);
        }
    }

    void initializeUI(ViewGroup rootView){
        question.setPrompt_time(DateTime.getDateTime());
        setThoughts(rootView);
        setQuestionText(rootView, question);
    }

    void setQuestionText(ViewGroup rootView, Question question) {
        ((TextView) rootView.findViewById(R.id.textViewDescription)).setText(question.getQuestion_text());
    }

    public void updateNext(boolean answered){
        Log.d(TAG,"updateNext("+answered+")");
        if(menu!=null)
        menu.findItem(R.id.action_next).setEnabled(answered);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu=menu;
        Log.d(TAG,"onCreateOptionsMenu: updateNext(false)");
        updateNext(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_previous:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected View addView() {
        View view = new View(this.getActivity());
        view.setLayoutParams(new LinearLayout.LayoutParams(100, 10));
        return view;
    }
    void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
