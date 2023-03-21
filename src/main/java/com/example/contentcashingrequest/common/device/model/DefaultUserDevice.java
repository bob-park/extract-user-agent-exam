package com.example.contentcashingrequest.common.device.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import static org.springframework.util.ObjectUtils.isEmpty;

@ToString
@Getter
public class DefaultUserDevice implements UserDevice {

    private final Platform platform;
    private final String clientName;
    private final String clientVersion;

    @Builder
    private DefaultUserDevice(Platform platform, String clientName, String clientVersion) {
        this.platform = isEmpty(platform) ? Platform.etc() : platform;
        this.clientName = clientName;
        this.clientVersion = clientVersion;
    }

    @Override
    public String getPlatformName() {
        return getPlatform().name();
    }

    @Override
    public String getPlatformVersion() {
        return getPlatform().version();
    }

    @Override
    public String getClientName() {
        return this.clientName;
    }

    @Override
    public String getClientVersion() {
        return this.clientVersion;
    }
}
