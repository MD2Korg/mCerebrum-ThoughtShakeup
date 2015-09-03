package org.md2k.thoughtshakeup;


import org.md2k.utilities.Report.Log;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
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

public class Question implements Serializable {
    private static final String TAG = Question.class.getSimpleName();
    private int question_id;
    private String question_type;
    private String question_text;
    private ArrayList<String> question_responses;
    private ArrayList<String> condition;
    private ArrayList<String> question_responses_selected;
    private long prompt_time;
    private String thoughts;
    private String shorten;
    boolean hasResponseSelected(String response){
        if(question_responses_selected==null) return false;
        for(int i=0;i<question_responses_selected.size();i++)
            if(question_responses_selected.get(i).equals(response)) return true;
        return false;
    }

    public Question(int question_id, String question_type, String question_text, ArrayList<String> question_responses, ArrayList<String> condition, String thoughts, String shorten) {
        this.question_id = question_id;
        this.question_type = question_type;
        this.question_text = question_text;
        this.question_responses = question_responses;
        this.condition = condition;
        this.question_responses_selected = new ArrayList<>();
        prompt_time = -1;
        this.thoughts=thoughts;
        this.shorten=shorten;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public ArrayList<String> getQuestion_responses() {
        return question_responses;
    }
    public boolean isType(String TYPE){
        if(question_type==null) return false;
        if(question_type.equals(TYPE)) return true;
        return false;
    }

    public void setQuestion_responses(ArrayList<String> question_responses) {
        this.question_responses = question_responses;
    }

    public ArrayList<String> getCondition() {
        return condition;
    }

    public void setCondition(ArrayList<String> condition) {
        this.condition = condition;
    }

    public ArrayList<String> getQuestion_responses_selected() {
        return question_responses_selected;
    }

    public void setQuestion_responses_selected(ArrayList<String> question_responses_selected) {
        this.question_responses_selected = question_responses_selected;
    }


    public long getPrompt_time() {
        return prompt_time;
    }

    public void setPrompt_time(long prompt_time) {
        this.prompt_time = prompt_time;
    }
    boolean isResponseExist(String option) {
        if(question_responses_selected==null) return false;
        for (int i = 0; i < question_responses_selected.size(); i++)
            if (question_responses_selected.get(i).equals(option)) return true;
        return false;
    }
    boolean isValidCondition(Question[] questions) {
        if (condition == null) return true;
        for(int i=0;i<condition.size();i++) {
            String[] separated = condition.get(i).split(":");
            int qid = Integer.valueOf(separated[0]);
            if (questions[qid].hasResponseSelected(separated[1])) return true;
        }
        return false;
    }
    boolean isValid() {
        Log.d(TAG,"isValid: question_type="+question_type+" selected="+question_responses_selected);
        if(question_responses_selected!=null)
            Log.d(TAG,"isValid: "+question_responses_selected.toString());

        if (question_type == null) return true;

        if(question_type.equals(Questions.MULTIPLE_SELECT_SPECIAL)){
            if(question_responses_selected==null)
                return false;
            else if(question_responses_selected.size()==4) return true;
            else return false;
        }
        if (question_type.equals(Questions.MULTIPLE_CHOICE)){
            if(question_responses_selected==null) return false;
            else if(question_responses_selected.size()==1) return true;
            else return false;
        }
        if (question_type.equals(Questions.MULTIPLE_SELECT)){
            if(question_responses_selected==null) return false;
            else if(question_responses_selected.size()>0) return true;
            else return false;
        }
        return true;
    }
    public void clear(){
        setQuestion_responses_selected(new ArrayList<String>());
        setPrompt_time(-1);
    }
}


