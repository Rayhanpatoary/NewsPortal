package com.example.utils;

public class Config {

    //put your admin panel url
    //public static final String ADMIN_PANEL_URL = "http://radio.spagreen.net/";

    //put your api key which obtained from admin panel
    //public static final String API_KEY = "cda11mVkinMjIOqxYtH8Cvys3cB1Wo0dwZlF2EKpGuLebDX9Qf";

    //Ads Configuration
    //set true to enable or set false to disable
    public static final boolean ENABLE_ADMOB_BANNER_ADS = true;
    public static final boolean ENABLE_ADMOB_INTERSTITIAL_ADS_ON_DRAWER_MENU = false;
    public static final boolean ENABLE_ADMOB_INTERSTITIAL_ADS_ON_SPLASH = false;
    public static final boolean ENABLE_ADMOB_INTERSTITIAL_ADS_ON_CATEGORY = true;
    public static final int ADMOB_INTERSTITIAL_ADS_INTERVAL = 3;

    //number of columns in a row category
    public static final int CATEGORY_COLUMN_COUNT = 3;

    //splash screen duration in millisecond
    public static final int SPLASH_DURATION = 3000;
    public static final int DELAY_DURATION= 1000;

    //set true if you want to enable RTL (Right To Left) mode, e.g : Arabic Language
    public static final boolean ENABLE_RTL_MODE = false;

    //set true if you want to display icon, app name and email on navigation drawer header menu
    public static final boolean ENABLE_DRAWER_INFO = true;

    //push notification handle when open url
    public static final boolean OPEN_NOTIFICATION_LINK_IN_EXTERNAL_BROWSER = false;

    //social link configuration
    public static final boolean ENABLE_MENU_WEBSITE = true;
    public static final boolean ENABLE_MENU_FACEBOOK = true;
    public static final boolean ENABLE_MENU_TWITTER = true;
    public static final boolean ENABLE_MENU_INSTAGRAM = false;
    public static final boolean ENABLE_MENU_EMAIL = true;

}
