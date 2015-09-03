package org.md2k.thoughtshakeup;

import java.util.ArrayList;
import java.util.Arrays;


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

public class Questions {
    public static final String MULTIPLE_CHOICE = "multiple_choice";
    public static final String MULTIPLE_SELECT = "multiple_select";
    public static final String TEXT = "text";
    public static final String EDITTEXT = "edittext";
    public static final String EDITTEXT_SPECIAL = "edittext_special";
    public static final String SEEKBAR = "seekbar";
    public static final String THOUGHT = "Thought: ";
    public static final String ORIGINAL_THOUGHT = "Original Thought: ";
    public static final String REPHRASED_THOUGHT = "Rephrased Thought: ";
    public static final String TEXT_REVIEW = "text_review";
    public static final String COLOR = "color";

    public static final String MULTIPLE_SELECT_SPECIAL = "multiple_select_special";

    private static Questions instance = null;

    public static Questions getInstance() {
        if (instance == null)
            instance = new Questions();
        return instance;
    }

    void clear() {
        Question[] questions = getQuestions();
        for (int i = 0; i < questions.length; i++)
            questions[i].clear();
    }

    private Questions() {

    }

    public void destroy() {
        instance = null;
    }

    private Question[] thoughts = new Question[]{
            new Question(0, TEXT, "The unpleasant thoughts that cross our minds are often exaggerated or inaccurate.\n\nThe following exercise is designed to help you shakeup your thoughts. You can use it to evaluate your thoughts, examine how accurate they are, and replace them with more accurate thoughts.", null, null, null,null),
            new Question(1, EDITTEXT, "Think about an unpleasant thought you are currently having.\n\nPlease type that thought in the box below. You can still continue with the exercise even if you don't type anything.", null, null, null,"Here is the unpleasant thought you had in mind (answer)"),
            new Question(2, SEEKBAR, "How true do you believe this thought is?\n(Touch and drag)", null, null, THOUGHT, "You believe this thought is (answer) percent true"),
            new Question(3, TEXT, "On the following screens there are some questions regarding your thought.\n\nThere are no correct or incorrect answers. Please select the response that seems most correct for you.", null, null, null,null),
            new Question(4, MULTIPLE_CHOICE, "My thought is based on a \"black-and-white\", \"all-or-nothing\" view. For example, \"If I am not the absolute best, it means I'm worthless\".\n(Tap to Select)", new ArrayList<String>(Arrays.asList(new String[]{"Strongly Agree", "Agree", "Neither agree or disagree", "Disagree", "Strongly Disagree"})), null, THOUGHT,"you (answer) that your thought is based on a \"black-and-white\", \"all-or-nothing\" view."),
            new Question(5, MULTIPLE_CHOICE, "My thought is affected by my focus on the negative aspects of the situation rather than the whole picture.\n(Tap to Select)", new ArrayList<String>(Arrays.asList(new String[]{"Strongly Agree", "Agree", "Neither agree or disagree", "Disagree", "Strongly Disagree"})), null, THOUGHT,"you (answer) that your thought is affected by your focus on the negative aspects of the situation rather than the whole picture."),
            new Question(6, MULTIPLE_CHOICE, "My thought is affected by the idea that everything is related to me or caused by me. It doesn't take into account external circumstances or other people's responsibility.\n(Tap to Select)", new ArrayList<String>(Arrays.asList(new String[]{"Strongly Agree", "Agree", "Neither agree or disagree", "Disagree", "Strongly Disagree"})), null, THOUGHT,"you (answer) that your thought is affected by the idea that everything is related to you or caused by you; that it doesn't take into account external circumstances or other people's responsibility."),
            new Question(7, MULTIPLE_CHOICE, "My thought is affected by what I've decided others think about me, even if this is not what they really think.\n(Tap to Select)", new ArrayList<String>(Arrays.asList(new String[]{"Strongly Agree", "Agree", "Neither agree or disagree", "Disagree", "Strongly Disagree"})), null, THOUGHT,"you (answer) that your thought is affected by what you've decided others think about you, even if this is not what they really think."),
            new Question(8, MULTIPLE_CHOICE, "My thought does not take into account my successes or good qualities.\n(Tap to Select)", new ArrayList<String>(Arrays.asList(new String[]{"Strongly Agree", "Agree", "Neither agree or disagree", "Disagree", "Strongly Disagree"})), null, THOUGHT,"you (answer) that your thought does not take into account your successes or good qualities."),
            new Question(9, MULTIPLE_CHOICE, "My thought is influenced by the overly negative consequences I think certain events or conditions will have on my future.\n(Tap to Select)", new ArrayList<String>(Arrays.asList(new String[]{"Strongly Agree", "Agree", "Neither agree or disagree", "Disagree", "Strongly Disagree"})), null, THOUGHT,"you (answer) that your thought is influenced by the overly negative consequences you think certain events or conditions will have on your future."),
            new Question(10, EDITTEXT_SPECIAL, "In the box below, use the provided sentence starters to come up with reasons why your thought may be correct or incorrect.", null, null, THOUGHT,"You also noted the following reasons for why your thought may be correct or incorrect: (answer)"),
            new Question(11, TEXT_REVIEW, "Please take a moment to review your answers to the previous questions and reflect on how your thought might be phrased differently taking these answers into account.", null, null, null,null),
            new Question(12, EDITTEXT, "Try rephrasing your thought in a way that reflects reality as accurately as possible. The new wording does not need to be more positive, just more realistic.", null, null, ORIGINAL_THOUGHT,null),
            new Question(13, SEEKBAR, "How correct is your new thought\n(Touch and drag)", null, null, REPHRASED_THOUGHT,null),
            new Question(14, SEEKBAR, "Read your original thought again. How correct do you think it is?\n(Touch and drag)", null, null, ORIGINAL_THOUGHT,null),
            new Question(15, TEXT, "Our negative thoughts often make things seem worse than they really are. To reduce their impact it is important to evaluate our negative thoughts and replace them with accurate onces\n\nYou can use this exercise whenever you experience negative thoughts.", null, null,null,null),
            new Question(16, MULTIPLE_CHOICE, "Do you think this exercise could be useful in dealing with unpleasant thoughts? (Tap to select)", new ArrayList<String>(Arrays.asList(new String[]{"Yes", "No"})), null,null,null),
            new Question(17, TEXT, "Congratulations.\n\n You have finished \"Thought Shakeup\" Exercise", null, null,null,null),
    };

    public Question[] getQuestions() {
        return thoughts;
    }

    public Question getQuestion(int id) {
        return thoughts[id];
    }

}
