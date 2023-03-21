package com.example.contentcashingrequest.common.session.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import com.example.contentcashingrequest.common.session.ClientSession;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class InMemoryClientSessionService implements ClientSessionService {

    private final List<ClientSession> sessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public ClientSession save(ClientSession clientSession) {

        ClientSession prevClientSession =
            sessions.stream()
                .filter(session -> session.sessionId().equals(clientSession.sessionId()))
                .findAny()
                .orElse(null);

        if (prevClientSession == null) {
            sessions.add(clientSession);

            log.debug("added session. ({})", clientSession);

            return clientSession;
        }

        return prevClientSession;
    }

    @Override
    public Optional<ClientSession> findById(String sessionId) {

        if (!hasText(sessionId)) {
            return Optional.empty();
        }

        return sessions.stream()
            .filter(session -> session.sessionId().equals(sessionId))
            .findAny();
    }
}
