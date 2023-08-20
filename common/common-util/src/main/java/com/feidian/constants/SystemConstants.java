package com.feidian.constants;

import lombok.Data;

@Data
public class SystemConstants {
    public static final String TEST_URL = "rxs4g2ziu.hn-bkt.clouddn.com";

    // 逻辑删除中的已删除
    public static final Long DELETE = 1L;
    public static final Long NOT_DELETED = 0L;

    public static final Long DEFAULT_DEPARTMENT_ID =5L;
}
