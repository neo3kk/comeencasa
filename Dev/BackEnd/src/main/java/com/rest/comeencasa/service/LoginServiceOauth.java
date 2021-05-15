package com.rest.comeencasa.service;

import java.net.URL;
import java.util.Map;

public interface LoginServiceOauth {
    URL getGoogleRedirectURL() throws Exception;
    String getAccessToken(String code) throws Exception;
    Map<String, String> getUserDetails(String accessToken) throws Exception;
}
