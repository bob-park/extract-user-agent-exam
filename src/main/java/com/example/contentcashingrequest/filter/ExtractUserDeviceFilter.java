package com.example.contentcashingrequest.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.contentcashingrequest.common.device.converter.DefaultUserDeviceConverter;
import com.example.contentcashingrequest.common.device.converter.UserDeviceConverter;
import com.example.contentcashingrequest.common.device.model.UserDevice;
import com.example.contentcashingrequest.common.session.ClientSession;
import com.example.contentcashingrequest.common.session.DefaultClientSession;
import com.example.contentcashingrequest.common.session.service.ClientSessionService;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExtractUserDeviceFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME_SESSION_ID = "Session-Id";

    private final UserDeviceConverter userDeviceConverter = new DefaultUserDeviceConverter();
    private final ClientSessionService clientSessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String requestSessionId = extractSessionId(request);

        UserDevice userDevice = userDeviceConverter.convert(request);

        ClientSession clientSession =
            clientSessionService.findById(requestSessionId)
                .orElseGet(() ->
                    DefaultClientSession.builder()
                        .platform(userDevice.getPlatformName())
                        .platformVersion(userDevice.getPlatformVersion())
                        .clientName(userDevice.getClientName())
                        .clientVersion(userDevice.getClientVersion())
                        .build());

        clientSessionService.save(clientSession);

        filterChain.doFilter(request, response);
    }


    private String extractSessionId(HttpServletRequest request) {
        return request.getHeader(HEADER_NAME_SESSION_ID);
    }
}
