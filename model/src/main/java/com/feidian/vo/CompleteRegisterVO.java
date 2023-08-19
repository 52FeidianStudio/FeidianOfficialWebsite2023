package com.feidian.vo;

import com.feidian.bo.CompleteRegisterBO;
import com.feidian.po.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteRegisterVO {

    private Long registerId;
    private Long userId;

    private String name;
    private String username;
    private String sex;
    private String nationality;

    private Long facultyId;
    private String facultyName;
    private Long subjectId;
    private String subjectName;
    private String gradeName;
    private String className;
    private Long studentId;

    private String nickname;
    private String birthday;

    private String avatarUrl;

    private String phone;
    private String email;
    private String qq;


    //大头照
    private String imgUrl;
    //个人简介
    private String resume;
    //想要申请的组别
    private Long desireDepartmentId;
    private String desireDepartmentName;
    //发展方向
    private String direction;
    //大学四年整体规划
    private String arrangement;
    //申请状态(0表示已提交，1表示已查看，2表示已通过，3表示未通过)
    private String status;
    //申请理由，对你所选择方向的了解以及为什么想选择此方向
    private String reason;

    //根据CompleteRegisterBO生成VO
    public CompleteRegisterVO(CompleteRegisterBO completeRegisterBO) {
        this.registerId = completeRegisterBO.getRegister().getId();
        this.userId = completeRegisterBO.getRegister().getUserId();

        this.name = completeRegisterBO.getUser().getName();
        this.username = completeRegisterBO.getUser().getUsername();
        this.sex = completeRegisterBO.getUser().getSex();
        this.nationality = completeRegisterBO.getUser().getNationality();

        this.facultyId = completeRegisterBO.getUser().getFacultyId();
        this.facultyName = completeRegisterBO.getFaculty().getFacultyName();
        this.subjectId = completeRegisterBO.getUser().getSubjectId();
        this.subjectName = completeRegisterBO.getSubject().getSubjectName();
        this.gradeName = completeRegisterBO.getUser().getGradeName();
        this.className = completeRegisterBO.getUser() .getClassName();
        this.studentId = completeRegisterBO.getUser().getStudentId();

        this.nickname = completeRegisterBO.getUser().getNickname();
        this.birthday = completeRegisterBO.getUser().getBirthday();
        this.avatarUrl = completeRegisterBO.getUser().getAvatarUrl();

        this.phone = completeRegisterBO.getUser().getPhone();
        this.email = completeRegisterBO.getUser().getEmail();
        this.qq = completeRegisterBO.getUser().getQq();

        this.imgUrl = completeRegisterBO.getRegister().getImgUrl();
        this.resume = completeRegisterBO.getRegister().getResume();
        this.desireDepartmentId = getDesireDepartmentId();
        this.desireDepartmentName = completeRegisterBO.getDepartment().getDepartmentName();
        this.direction = completeRegisterBO.getRegister().getDirection();
        this.arrangement = completeRegisterBO.getRegister().getArrangement();
        this.status = completeRegisterBO.getRegister().getStatus();
        this.reason = completeRegisterBO.getRegister().getReason();
    }
}
