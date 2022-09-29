package ru.ivanov.springCalc.Calculator.model;

public class Calc {
    private String in;

    private String result;

    public Calc(String in, String result) {
        this.in = in;
        this.result = result;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
