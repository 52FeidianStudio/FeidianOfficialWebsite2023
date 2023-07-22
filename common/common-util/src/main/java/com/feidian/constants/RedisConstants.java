package com.feidian.constants;

public class RedisConstants {
    // 登录验证码的 Redis Key
    public static final String LOGIN_CODE_KEY = "login:code:";

    // 登录验证码的过期时间（单位：秒）
    public static final Long LOGIN_CODE_TTL = 2L;

    // 登录用户的 Redis Key
    public static final String LOGIN_USER_KEY = "login:token:";

    // 登录用户的过期时间（单位：秒）
    public static final Long LOGIN_USER_TTL = 36000L;

    // 缓存空值的过期时间（单位：秒）
    public static final Long CACHE_NULL_TTL = 2L;


    // 用户签名的 Redis Key 前缀
    public static final String USER_SIGN_KEY = "sign:";
}