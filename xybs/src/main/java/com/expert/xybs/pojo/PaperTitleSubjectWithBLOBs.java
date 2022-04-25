package com.expert.xybs.pojo;

public class PaperTitleSubjectWithBLOBs extends PaperTitleSubject {
    private String questions;

    private String a;

    private String b;

    private String c;

    private String d;

    private String e;

    private String f;

    private String da;

    private String analysis;

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions == null ? null : questions.trim();
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a == null ? null : a.trim();
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b == null ? null : b.trim();
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c == null ? null : c.trim();
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d == null ? null : d.trim();
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e == null ? null : e.trim();
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f == null ? null : f.trim();
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da == null ? null : da.trim();
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis == null ? null : analysis.trim();
    }
}