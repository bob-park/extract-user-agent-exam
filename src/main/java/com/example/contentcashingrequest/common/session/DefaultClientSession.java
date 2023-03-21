package com.example.contentcashingrequest.common.session;

import java.util.UUID;

import lombok.Builder;
import lombok.ToString;

import org.springframework.util.StringUtils;

@ToString
public class DefaultClientSession implements ClientSession {

    private final String sessionId;
    private final String platform;
    private final String platformVersion;
    private final String clientName;
    private final String clientVersion;

    @Builder
    private DefaultClientSession(String sessionId, String platform, String platformVersion, String clientName,
        String clientVersion) {
        this.sessionId = StringUtils.hasText(sessionId) ? sessionId : UUID.randomUUID().toString();
        this.platform = platform;
        this.platformVersion = platformVersion;
        this.clientName = clientName;
        this.clientVersion = clientVersion;
    }

    @Override
    public String sessionId() {
        return this.sessionId;
    }

    @Override
    public String platform() {
        return this.platform;
    }

    @Override
    public String platformVersion() {
        return this.platformVersion;
    }

    @Override
    public String clientName() {
        return this.clientName;
    }

    @Override
    public String clientVersion() {
        return this.clientVersion;
    }
}
