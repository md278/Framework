package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
    public static final String ACCOUNTS_PAGE_TITLE = "My Account";
    public static final String LOGIN_PAGE_TITLE = "Account Login";
    public static final String LOGIN_PAGE_URL = "account/login";
    public static final String ACCOUNTS_PAGE_URL = "account/account";
    public static final int DEFAULT_SHORT_WAIT = 5;
    public static final int DEFAULT_MEDIUM_WAIT = 10;
    public static final int DEFAULT_LONG_WAIT = 20;
    public static final int DEFAULT_POLLING_WAIT = 2;
    public static final int ACCOUNTS_PAGE_HEADER_COUNT = 4;
    public static final List<String> ACCOUNTS_PAGE_HEADER_LIST = Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter");
    public static final String REGISTRATION_SUCCESS_MESSAGE = "Your Account Has Been Created!";
}
