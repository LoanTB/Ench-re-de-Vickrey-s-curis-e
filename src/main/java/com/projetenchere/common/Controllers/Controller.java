package com.projetenchere.common.Controllers;

public abstract class Controller {
    public void waitSynchro(int ms){
        synchronized (this) {
            try {
                wait(ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
