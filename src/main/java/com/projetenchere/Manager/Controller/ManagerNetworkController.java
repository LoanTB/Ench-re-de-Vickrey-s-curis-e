package com.projetenchere.Manager.Controller;

import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ManagerNetworkController {
    private String ManagerIp;
    private int ManagerPort = 2468;

    public String getManagerIp() {
        return ManagerIp;
    }

    public void setManagerIp(String managerIp) {
        ManagerIp = managerIp;
    }

    public ManagerNetworkController() throws UnknownHostException {
        String ManagerIp = NetworkUtil.getMyIP();
        setManagerIp(ManagerIp);
    }

    /*
    public ObjectSender createObjectSender() {
        //TODO :
    }
    */
}