package com.projetenchere.Manager;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.View.IManagerUserInterfaceFactory;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerUserInterfaceFactory;

import static java.lang.Thread.sleep;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        System.out.println("test0");
        IManagerUserInterfaceFactory uiFactory = new ManagerUserInterfaceFactory();
        System.out.println("testLOAN");
        ManagerController controller = new ManagerController(uiFactory);
        System.out.println("test1");
        controller.displayHello();
        System.out.println("test2");
        controller.setSignatureConfig();
        System.out.println("test3");
        controller.init();
        System.out.println("test4");
    }
}
