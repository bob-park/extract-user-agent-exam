package com.example.contentcashingrequest.common.session;

public interface ClientSession {

    String sessionId();

    String platform();

    String platformVersion();

    String clientName();

    String clientVersion();


}
