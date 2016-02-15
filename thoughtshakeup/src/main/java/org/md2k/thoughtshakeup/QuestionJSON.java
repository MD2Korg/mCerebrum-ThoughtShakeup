package org.md2k.thoughtshakeup;

import android.os.Parcel;
import android.os.Parcelable;

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
public class QuestionJSON implements Parcelable{
    int question_id;
    String question_type;
    String question_text;
    ArrayList<String> response_option;
    ArrayList<String> response;
    ArrayList<String> condition;
    long prompt_time;
    long completion_time;
    QuestionJSON(Question question){
        question_id=question.getQuestion_id();
        question_type=question.getQuestion_type();
        question_text=question.getQuestion_text();
        response_option=question.getQuestion_responses();
        response=question.getQuestion_responses_selected();
        condition=question.getCondition();
        prompt_time=question.getPrompt_time();
        completion_time=question.getCompletion_time();
    }

    protected QuestionJSON(Parcel in) {
        question_id = in.readInt();
        question_type = in.readString();
        question_text = in.readString();
        response_option = in.createStringArrayList();
        response = in.createStringArrayList();
        condition = in.createStringArrayList();
        prompt_time = in.readLong();
        completion_time = in.readLong();
    }

    public static final Creator<QuestionJSON> CREATOR = new Creator<QuestionJSON>() {
        @Override
        public QuestionJSON createFromParcel(Parcel in) {
            return new QuestionJSON(in);
        }

        @Override
        public QuestionJSON[] newArray(int size) {
            return new QuestionJSON[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(question_id);
        dest.writeString(question_type);
        dest.writeString(question_text);
        dest.writeStringList(response_option);
        dest.writeStringList(response);
        dest.writeStringList(condition);
        dest.writeLong(prompt_time);
        dest.writeLong(completion_time);
    }
}
