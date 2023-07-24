package com.feidian.service.impl;


import com.feidian.dto.RegisterDTO;
import com.feidian.mapper.RegisterMapper;
import com.feidian.mapper.UserMapper;
import com.feidian.mapper.UserRoleMapper;
import com.feidian.po.Register;
import com.feidian.po.User;
import com.feidian.po.UserRole;
import com.feidian.responseResult.ResponseResult;
import com.feidian.util.RedisCache;
import com.feidian.util.SecurityUtils;
import com.feidian.util.serviceUtil.FileUploadUtil;
import com.feidian.vo.CompleteRegisterVO;
import com.feidian.vo.SectionalRegisterVO;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feidian.service.RegisterService;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;


@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisCache redisCache;

    private static final int DESIRE_DEPARTMENT_ID_MAX_LENGTH = 11;
    private static final int RESUME_MAX_LENGTH = 512;
    private static final int DIRECTION_MAX_LENGTH = 512;
    private static final int ARRANGEMENT_MAX_LENGTH = 512;
    private static final int REASON_MAX_LENGTH = 1024;


    @Override
    public ResponseResult submitRegister(RegisterDTO registerDTO) {
        Long userId = SecurityUtils.getUserId();

        Register tempRegister = registerMapper.selectRegisterByUserId(userId);

        if (tempRegister != null) {
            return ResponseResult.errorResult(400, "用户已经提交过报名表了，如果想要更改信息，请选择修改。");
        }


        ResponseResult validateResponseResult = validate(registerDTO);
        if (validateResponseResult.getCode() == 400) {
            return validateResponseResult;
        }

        String key = SecurityUtils.getLoginUser().getUsername();
        String imageUrl = redisCache.getCacheObject(key);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        //默认刚开始状态为 0 已提交
        Register register = new Register(0L, SecurityUtils.getUserId(), registerDTO.getResume(),
                registerDTO.getDesireDepartmentId(), registerDTO.getDirection(), registerDTO.getArrangement(),
                "0", registerDTO.getReason(), imageUrl, 0L, currentTimestamp,
                SecurityUtils.getLoginUser().getUsername(), currentTimestamp, SecurityUtils.getLoginUser().getUsername());

        registerMapper.insertRegister(register);

        return ResponseResult.successResult(200, "提交报名表成功!");
    }

    @Override
    public ResponseResult submitImage(MultipartFile imageFile) {
        ResponseResult submitImageResponseResult = FileUploadUtil.uploadAvatar(imageFile);
        if (submitImageResponseResult.getCode() == 400) {
            return submitImageResponseResult;
        }
        String key = SecurityUtils.getLoginUser().getUsername();
        redisCache.setCacheObject(key, submitImageResponseResult.getData());

        return ResponseResult.successResult(200, "上传图片文件成功");
    }

    @Override
    public ResponseResult editRegister(RegisterDTO registerDTO) {
        //根据registerId查询当前报名用户
        User registerUser = userMapper.selectUserByRegisterId(registerDTO.getRegisterId());
        //比对编辑用户是否是报名表的提交者
        User tempUser = SecurityUtils.getLoginUser().getUser();
        if (registerUser.getId() != tempUser.getId()) {
            return ResponseResult.errorResult(408, "您没有编辑这份报名表的权限");
        }

        //根据用户查询报名表
        Register tempRegister = registerMapper.selectRegisterByUserId(tempUser.getId());


        if (tempRegister == null) {
            return ResponseResult.errorResult(400, "用户尚未提交过报名表，无法修改。");
        }

        if ("1".equals(tempRegister.getStatus()) || "2".equals(tempRegister.getStatus()) || "3".equals(tempRegister.getStatus())) {
            return ResponseResult.errorResult(400, "该报名表已被审核，无法修改。");
        }

        //更新报名表信息
        tempRegister.setResume(registerDTO.getResume());
        tempRegister.setDesireDepartmentId(registerDTO.getDesireDepartmentId());
        tempRegister.setDirection(registerDTO.getDirection());
        tempRegister.setArrangement(registerDTO.getArrangement());
        tempRegister.setReason(registerDTO.getReason());
        tempRegister.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        tempRegister.setUpdateBy(tempUser.getUsername());
        //TODO 检验修改是否合法
        registerMapper.updateContent(tempRegister);

        return ResponseResult.successResult(200, "修改报名表成功!");
    }


    //审核报名表
    @Override
    public ResponseResult examineRegister(Long registerId) {
        //查询审核人(即查询当前正在审核的User)
        User auditor = selectCurrentAuditor();

        //查询被查看的报名表和报名人
        CompleteRegisterVO completeRegisterVO = registerMapper.selectCompleteRegisterVOByRegisterId(registerId);

        if (completeRegisterVO == null) {
            return ResponseResult.errorResult(400, "用户未注册或报名表不存在");
        } else if ("2".equals(completeRegisterVO.getRegister().getStatus()) || "3".equals(completeRegisterVO.getRegister().getStatus())) {
            //如果这张报名表已经被审核过了，直接返回审核后的报名表，不用更新报名表状态
            return ResponseResult.successResult(completeRegisterVO);
        } else {
            //未审核过则更新状态为1 已查看并更新update_by等信息
            completeRegisterVO.getRegister().setStatus("1");// 更新register对象中的状态字段
            completeRegisterVO.getRegister().setUpdateBy(auditor.getUsername());
            completeRegisterVO.getRegister().setUpdateTime(new Timestamp(System.currentTimeMillis()));
            registerMapper.updateStatus(completeRegisterVO.getRegister());

            return ResponseResult.successResult(completeRegisterVO);
        }
    }

    @Override
    public ResponseResult isApproved(Long registerId, String isApprovedFlag) {
        //查询审核人(即查询当前正在审核的User)
        User auditor = selectCurrentAuditor();

        //查询被查询的报名表相关信息
        CompleteRegisterVO completeRegisterVO = registerMapper.selectCompleteRegisterVOByRegisterId(registerId);
        User user = completeRegisterVO.getUser();
        Register register = completeRegisterVO.getRegister();

        if (user == null && register == null) {
            return ResponseResult.errorResult(400, "用户未注册或报名表不存在");
        }

        if ("2".equals(isApprovedFlag)) {
            //更改状态为2 已通过
            register.setStatus("2");// 更新register对象中的状态字段
            register.setUpdateBy(auditor.getUsername());
            register.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            registerMapper.updateStatus(register);

            //将user设置为预备成员
            UserRole userRole = new UserRole();
            userRole.setUserId(register.getUserId());
            userRole.setRoleId(5L);
            userRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            userRole.setUpdateBy(auditor.getUsername());

            userRoleMapper.updateUserRole(userRole);

            return ResponseResult.successResult(200, "成功修改报名表状态为已通过，并将其添加至预备成员。");
        } else if ("3".equals(isApprovedFlag)) {
            //更改状态为3 未通过
            register.setStatus("3");// 更新register对象中的状态字段
            register.setUpdateBy(auditor.getUsername());
            register.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            registerMapper.updateStatus(register);

            //不用修改UserRole，默认roleId为6 未加入

            return ResponseResult.successResult(200, "成功修改报名表状态为未通过");
        }
        return ResponseResult.errorResult(400, "修改报名表状态失败!");
    }


    //一大堆查询操作
    @Override
    public ResponseResult viewRegister(Long registerId) {
        //查询被查看的报名表和报名人
        CompleteRegisterVO completeRegisterVO = registerMapper.selectCompleteRegisterVOByRegisterId(registerId);
        if (completeRegisterVO == null) {
            return ResponseResult.errorResult(400, "用户未注册或报名表不存在");
        }
        return ResponseResult.successResult(completeRegisterVO);
    }

    @Override
    public ResponseResult selectByGradeName(String gradeName) {
        //根据年级查询用户
        List<User> userList = userMapper.selectUserListByGradeName(gradeName);
        List<SectionalRegisterVO> sectionalRegisterVOList = registerMapper.selectSectionalRegisterVOByUser(userList);
        return ResponseResult.successResult(sectionalRegisterVOList);
    }

    @Override
    public ResponseResult selectBySubjectId(Long subjectId) {
        //根据专业查询用户
        List<User> userList = userMapper.selectUserListBySubjectId(subjectId);
        List<SectionalRegisterVO> sectionalRegisterVOList = registerMapper.selectSectionalRegisterVOByUser(userList);
        return ResponseResult.successResult(sectionalRegisterVOList);
    }

    @Override
    public ResponseResult selectByDesireDepartmentId(Long desireDepartmentId) {
        //根据申请组别查询报名表
        List<Register> registerList = registerMapper.selectByDesireDepartmentId(desireDepartmentId);
        List<SectionalRegisterVO> sectionalRegisterVOList = registerMapper.selectSectionalRegisterVOByRegister(registerList);
        return ResponseResult.successResult(sectionalRegisterVOList);
    }

    @Override
    public ResponseResult selectByStatus(String status) {
        //根据报名表状态查询报名表
        List<Register> registerList = registerMapper.selectByStatus(status);
        List<SectionalRegisterVO> sectionalRegisterVOList = registerMapper.selectSectionalRegisterVOByRegister(registerList);
        return ResponseResult.successResult(sectionalRegisterVOList);
    }

    private User selectCurrentAuditor() {
        //查询审核人(即查询当前正在审核的User)
        User auditor = userMapper.selectUserByUserId(SecurityUtils.getUserId());
        return auditor;
    }

    private ResponseResult validate(RegisterDTO registerDTO) {
        if (registerDTO == null) {
            return ResponseResult.errorResult(400, "注册信息不能为空");
        }

        if (StringUtils.isBlank(registerDTO.getResume()) ||
                registerDTO.getDesireDepartmentId() == null ||
                StringUtils.isBlank(registerDTO.getDirection()) ||
                StringUtils.isBlank(registerDTO.getArrangement()) ||
                StringUtils.isBlank(registerDTO.getReason())) {
            return ResponseResult.errorResult(400, "有必填信息未填");
        }

        if (registerDTO.getDesireDepartmentId() != null && String.valueOf(registerDTO.getDesireDepartmentId()).length() > DESIRE_DEPARTMENT_ID_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "想要申请的组别超出长度限制");
        }

        if (registerDTO.getResume() != null && registerDTO.getResume().length() > RESUME_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "个人简介超出长度限制");
        }

        if (registerDTO.getDirection() != null && registerDTO.getDirection().length() > DIRECTION_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "发展方向超出长度限制");
        }

        if (registerDTO.getArrangement() != null && registerDTO.getArrangement().length() > ARRANGEMENT_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "大学四年的整体规划超出长度限制");
        }

        if (registerDTO.getReason() != null && registerDTO.getReason().length() > REASON_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "对你所选方向的了解以及为什么想选择此方向超出长度限制");
        }

        return ResponseResult.successResult(200, "注册信息验证通过");
    }

}
