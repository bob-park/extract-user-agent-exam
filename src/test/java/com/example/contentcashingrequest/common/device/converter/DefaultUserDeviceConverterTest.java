package com.example.contentcashingrequest.common.device.converter;

import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

import com.example.contentcashingrequest.common.device.model.UserDevice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultUserDeviceConverterTest {

    @ParameterizedTest
    @MethodSource("inputData")
    void convert(String userAgent, String browserName) {

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        mockRequest.addHeader(HttpHeaders.USER_AGENT, userAgent);

        DefaultUserDeviceConverter defaultUserDeviceConverter = new DefaultUserDeviceConverter();

        UserDevice userDevice = defaultUserDeviceConverter.convert(mockRequest);

        assertThat(userDevice.getClientName()).isEqualTo(browserName);

    }

    private static Stream<Arguments> inputData() {
        return Stream.of(
            Arguments.of(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
                "Chrome"),
            Arguments.of(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.59",
                "Edg"),
            Arguments.of(
                "PostmanRuntime/7.31.1", "PostmanRuntime"),
            Arguments.of(
                "Mozilla/5.0 (Linux; Android 9; Mi A2 Lite) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Mobile Safari/537.36",
                "Chrome"),
            Arguments.of(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 13_5_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.1 Mobile/15E148 Safari/604.1",
                "Safari")
        );
    }
}