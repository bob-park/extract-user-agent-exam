package com.example.contentcashingrequest.common.session.service;

import java.util.Optional;

import com.example.contentcashingrequest.common.session.ClientSession;

public interface ClientSessionService {

    ClientSession save(ClientSession clientSession);

    Optional<ClientSession> findById(String sessionId);

}
