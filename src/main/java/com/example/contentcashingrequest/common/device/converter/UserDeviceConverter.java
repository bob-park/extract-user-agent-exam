package com.example.contentcashingrequest.common.device.converter;

import jakarta.servlet.http.HttpServletRequest;

import com.example.contentcashingrequest.common.device.model.UserDevice;

public interface UserDeviceConverter {

    UserDevice convert(HttpServletRequest request);

}
