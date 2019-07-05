package com.voldybot.haftamoney.utils;

public class EnvData {

    private String baseURL;
    private String publicKey;

    public EnvData(boolean isTest) {
        this.baseURL = Common.RestEndPoint.IDEXClient.BASE_URL_TEST;
        this.publicKey = Common.RestEndPoint.IDEXClient.TOKEN_TEST;
        if (!isTest) {
            this.baseURL = Common.RestEndPoint.IDEXClient.BASE_URL;
            this.publicKey = Common.RestEndPoint.IDEXClient.TOKEN;
        }
    }
    public String getBaseURL() {
        return baseURL;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
