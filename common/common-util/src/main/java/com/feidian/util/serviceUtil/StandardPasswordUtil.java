package com.feidian.util.serviceUtil;

import java.util.regex.Pattern;

public class StandardPasswordUtil {
    private static final Pattern pattern = Pattern.compile("[a-zA-Z0-9]+[\\.]{0,1}[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+");

    /**
     * 检查给定的密码是否符合标准要求。
     *
     * @param password 要检查的密码。
     * @return 如果密码符合标准要求则返回 true；否则返回 false。
     */
    public static boolean isPasswordStandardized(String password) {
        // 用于检查密码不同标准的正则表达式
        String REG_NUMBER = ".*\\d+.*";
        String REG_UPPERCASE = ".*[A-Z]+.*";
        String REG_LOWERCASE = ".*[a-z]+.*";

        // 检查密码是否为 null 或长度小于 8 个字符
        if (password == null || password.length() < 8) {
            return false;
        }

        // 统计密码满足的标准数量
        int criteriaCount = 0;
        if (pattern.matcher(password).matches()) criteriaCount++;
        if (password.matches(REG_NUMBER)) criteriaCount++;
        if (password.matches(REG_LOWERCASE)) criteriaCount++;
        if (password.matches(REG_UPPERCASE)) criteriaCount++;

        // 检查密码是否满足至少 3 个标准
        return criteriaCount >= 3;
    }
}