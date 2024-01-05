package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;

public class ManagerUserInterfaceFactory implements IManagerUserInterfaceFactory {

    @Override
    public IManagerUserInterface createManagerUserInterface() {
        return new ManagerGraphicalUserInterface();
    }
}
