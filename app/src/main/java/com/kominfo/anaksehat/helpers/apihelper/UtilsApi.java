package com.kominfo.anaksehat.helpers.apihelper;

/**
 * Created by emrekabir on 2/08/2018.
 */

public class UtilsApi {

//    public static final String BASE_URL = "https://stunting.wiradipa.com/";
    public static final String BASE_URL = "https://stunting.wiradipa.com/";
    public static final String BASE_URL_API = BASE_URL+"api/";
    public static final String GOOGLE_API_KEY = "AIzaSyBn4jmksoouUB7xGrsVq3mCSZ-N0KZ47bc";
    public static final String BASE_YOUTUBE_IMAGE_START = "https://i.ytimg.com/vi/";
    public static final String YOUTUBE_IMAGE_STANDARD = "/sddefault.jpg";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
