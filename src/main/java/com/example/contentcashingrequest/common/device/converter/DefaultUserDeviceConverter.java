package com.example.contentcashingrequest.common.device.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import com.example.contentcashingrequest.common.device.model.DefaultUserDevice;
import com.example.contentcashingrequest.common.device.model.Platform;

import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;

public class DefaultUserDeviceConverter implements UserDeviceConverter {

    private static final Pattern USER_AGENT_PATTERN =
        Pattern.compile("\\((?<info>.*?)\\)(\\s|$)|(?<name>.*?)\\/(?<version>.*?)(\\s|$)");
    private static final Pattern PLATFORM_VERSION_PATTERN = Pattern.compile("([\\d._-]+)");

    private static final String MATCHER_GROUP_INFO = "info";
    private static final String MATCHER_GROUP_NAME = "name";
    private static final String MATCHER_GROUP_VERSION = "version";

    private static final String OS_MAC = "Mac";
    private static final String OS_WINDOWS = "Windows";
    private static final String OS_LINUX = "Linux";
    private static final String OS_ANDROID = "Android";

    private static final List<String> OS_LIST = List.of(OS_MAC, OS_WINDOWS, OS_LINUX, OS_ANDROID);

    @Override
    public DefaultUserDevice convert(HttpServletRequest request) {

        Platform platform = null;
        List<Browser> browsers = new ArrayList<>();

        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        Matcher matcher = USER_AGENT_PATTERN.matcher(userAgent);

        while (matcher.find()) {
            String info = matcher.group(MATCHER_GROUP_INFO);
            String name = matcher.group(MATCHER_GROUP_NAME);
            String version = matcher.group(MATCHER_GROUP_VERSION);

            if (hasText(info) && platform == null) {
                platform = extractPlatform(info);
            }

            browsers.add(new Browser(name, version));
        }

        Browser browser = selectBrowser(browsers);

        return DefaultUserDevice.builder()
            .platform(platform)
            .clientName(browser.name())
            .clientVersion(browser.version())
            .build();
    }

    private Platform extractPlatform(String info) {

        String[] devices = info.split(";");

        String platformName = null;
        String version = null;

        for (String device : devices) {
            for (String os : OS_LIST) {
                if (startsWithIgnoreCase(device, os)) {
                    platformName = os;
                }
            }

            Matcher versionMatcher = PLATFORM_VERSION_PATTERN.matcher(device);

            if (versionMatcher.find()) {
                version = versionMatcher.group(1);
            }

        }

        if (!hasText(platformName)) {
            platformName = devices[0];
        }

        return new Platform(platformName, version);
    }

    private Browser selectBrowser(List<Browser> browsers) {

        if (browsers.isEmpty()) {
            return Browser.etc();
        }

        Browser browser = browsers.get(browsers.size() - 1);

        if (browser.name().contains("Safari")) {
            Browser chrome =
                browsers.stream().filter(item -> "Chrome".equals(item.name()))
                    .findAny()
                    .orElse(null);

            if (chrome != null) {
                return chrome;
            }

            Browser version =
                browsers.stream().filter(item -> "Version".equals(item.name()))
                    .findAny()
                    .orElse(null);

            if (version != null) {
                return new Browser(browser.name(), version.version());
            }
        }

        return browser;
    }

    private record Browser(String name, String version) {

        public static Browser etc() {
            return new Browser("ETC", null);
        }
    }

}
