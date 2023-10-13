package com.feidian.enums;

public enum HttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),

    USERNAME_EXIST(406,"用户名已存在"),
    PHONENUMBER_EXIST(406,"手机号已存在"),
    EMAIL_EXIST(406, "邮箱已存在"),
    REQUIRE_USERNAME(406, "必需填写用户名"),
    INVALID_PARAM(408,"无效参数"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    IMAGE_FORMAT_ERROR(505,"图片格式错误"),
    IMAGE_UPLOAD_ERROR(505,"图片上传失败"),
    USERNAME_NOT_NULL(400, "用户名不能为空"),
    PASSWORD_NOT_NULL(400, "密码不能为空"),
    NAME_NOT_NULL(400, "姓名不能为空"),
    BIRTHDAY_NOT_NULL(400, "生日不能为空"),
    INVALID_SEX(400, "无效性别"),
    INVALID_STUDENT_ID(400, "无效学号"),
    FACULTY_NOT_NULL(400, "学院不能为空"),
    DEPARTMENT_NOT_NULL(400, "部门不能为空"),
    AVATAR_URL_NOT_NULL(400, "头像URL不能为空"),
    NATIONALITY_NOT_NULL(400, "民族不能为空"),
    PHONE_NOT_NULL(400, "联系电话不能为空"),
    EMAIL_NOT_NULL(400, "邮箱不能为空"),
    QQ_NOT_NULL(400, "QQ号不能为空"),
    PASSWORD_NOT_STANDARDIZED(400,"密码不符合规范，应有大写字母和小写字母且密码长度在八个字符以上" ),
    PHONE_EXIST(400,"电话已经存在" ),
    EMAIL_NOT_FORMAT(400,"邮箱不符合规范" ),
    PHONE_NOT_FORMAT(400,"电话不符合规范" ),
    CODE_ERR(400,"验证码错误" ),
    FACULTYID_NOT_NULL(400, "学院ID不能为空"),
    SUBJECT_NOT_NULL(400, "专业不能为空"),
    DEPARTMENTID_NOT_NULL(400, "部门ID不能为空"),
    CITY_NOT_NULL(400, "城市不能为空"),
    COMPANY_NOT_NULL(400, "公司不能为空"),
    ISDELETED_NOT_NULL(400, "是否删除不能为空"),
    CREATETIME_NOT_NULL(400, "创建时间不能为空"),
    CREATEBY_NOT_NULL(400, "创建者不能为空"),
    DEPARTMENT_IS_DELETE(400,"没有该部门" ),
    NOT_GRADUATE(400,"没有该毕业生" ),
    USERNAME_EMAIL_NOT_MATCH(400,"用户名和邮箱不匹配"),
    CODE_NULL(400,"验证码不能为空"),
    REGISTRATION_DOWN(400,"报名通道已关闭");





    int code;
    String msg;

    HttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}