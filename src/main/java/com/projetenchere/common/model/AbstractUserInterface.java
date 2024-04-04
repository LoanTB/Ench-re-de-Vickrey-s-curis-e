package com.projetenchere.common.model;

import java.util.Scanner;

public abstract class AbstractUserInterface {

    public static final Scanner scanner = new Scanner(System.in);

    public void showMessage(String message) {
        System.out.println(message);
    }

    protected String readMessage() {
        return scanner.nextLine();
    }
}
