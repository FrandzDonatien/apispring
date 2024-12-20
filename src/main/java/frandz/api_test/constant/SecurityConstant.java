package frandz.api_test.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000;
    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String TOKEN_HEADER ="Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED ="Token Can not be verified";
    public static final String GET_ARRAYS_LLC ="Get Arrays, LLC";
    public static final String GET_ARRAYS_ADMINISTRATION ="User Management Portal";

    public static final String AUTHORITIES ="Authorities";
    public static final String FORBIDDEN_MESSAGE ="You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE ="You do not have permission to access this page";
    public static final String OPTION_HTTP_METHOD ="OPTIONS";
    public static final String[] PUBLIC_URLS ={ "/api/v1/auth/**"};
    public static  final String APP_BASE_URL = "/api/v1";
}
