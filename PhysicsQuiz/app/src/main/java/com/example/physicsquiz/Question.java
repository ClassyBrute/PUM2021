package com.example.physicsquiz;

public class Question {
    private int textId;
    private boolean answer;
    private boolean answered;

    public Question(int textId, boolean answer, boolean answered){
        this.textId = textId;
        this.answer = answer;
        this.answered = answered;
    }

    public int getTextId() {
        return textId;
    }

    public boolean isAnswer() {
        return answer;
    }

    public boolean isAnswered(){
        return answered;
    }
}
