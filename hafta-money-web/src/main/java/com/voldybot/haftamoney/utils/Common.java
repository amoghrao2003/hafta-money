package com.voldybot.haftamoney.utils;

public class Common {

    public static final String CONTENT_TYPE = "content-type";
    public static final String APPLICATION_JSON = "application/json";
    public class RestEndPoint{
        public class Exposed {
            public static final String UPCOMING_IPOS = "/api/haftamoney/ipos/upcoming-ipos";
            public static final String TEST_GET = "/api/testing/articles/article/:id";
        }
        public class IDEXClient {
            public static final String UPCOMING_IPOS = "https://cloud.iexapis.com/stable/tops?token=pk_12de4c82457a44758e6dc7d0c9715200&symbols=aapl";
            
        }
    }
}
