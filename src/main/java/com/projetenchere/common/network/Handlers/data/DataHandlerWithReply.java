package com.projetenchere.common.network.Handlers.data;

public abstract class DataHandlerWithReply implements IDataHandler {

    @Override
    public boolean generatesReply() {return true;}
}
