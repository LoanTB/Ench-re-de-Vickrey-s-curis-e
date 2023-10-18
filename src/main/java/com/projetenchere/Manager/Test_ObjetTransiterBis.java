package com.projetenchere.Manager;

import java.io.Serializable;

public class Test_ObjetTransiterBis implements Serializable {
    private String signal;

    private int[] paloJSP;

    public Test_ObjetTransiterBis(String signal, int[] paloJSP) {
        this.signal = signal;
        this.paloJSP = paloJSP;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public int[] getPaloJSP() {
        return paloJSP;
    }

    public void setPaloJSP(int[] paloJSP) {
        this.paloJSP = paloJSP;
    }
}
