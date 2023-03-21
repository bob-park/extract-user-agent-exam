package com.example.contentcashingrequest.common.device.model;

public record Platform(String name, String version) {

    public static Platform etc() {
        return new Platform("ETC", null);
    }

}