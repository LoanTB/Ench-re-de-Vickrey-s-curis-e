package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.IManagerUserInterfaceFactory;

public class ManagerUserInterfaceFactory implements IManagerUserInterfaceFactory {

    @Override
    public IManagerUserInterface createManagerUserInterface() {
        ManagerAppLoader.launchApp();
        return new ManagerGraphicalUserInterface();
    }
}
