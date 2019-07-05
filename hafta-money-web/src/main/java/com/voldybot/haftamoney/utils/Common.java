package com.voldybot.haftamoney.utils;

public class Common {
    
    public static final String CONTENT_TYPE = "content-type";
    public static final String APPLICATION_JSON = "application/json";
    
    public class RestEndPoint{
        public static final String AND ="&";
        public static final String Q ="?";
        
        public class Exposed {
            public static final String UPCOMING_IPOS = "/api/haftamoney/ipos/upcoming-ipos";
            public static final String TEST_GET = "/api/testing/articles/article/:id";
        }
        public class IDEXClient {
            public static final String BASE_URL="https://cloud.iexapis.com";
            public static final String BASE_URL_TEST="https://sandbox.iexapis.com";
            public static final String TOKEN = "token=pk_12de4c82457a44758e6dc7d0c9715200";
            public static final String TOKEN_TEST = "token=Tpk_c60442243c234943b784066bf94b2432";
            public static final String VERSION = "/stable";
            
            public static final String UPCOMING_IPOS = "/stock/market/upcoming-ipos";
            
        }
    }
}
