package com.feidian.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feidian.service.RegisterService;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service("registerService")
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, Register> implements RegisterService {

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
        //根据用户查询报名表
        LambdaQueryWrapper<Register> registerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        registerLambdaQueryWrapper.eq(Register::getUserId, userId);
        Register tempRegister = registerMapper.selectById(registerLambdaQueryWrapper);
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

        registerMapper.insert(register);

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

    //审核报名表
    @Override
    public ResponseResult isApproved(Long registerId, String isApprovedFlag) {
        //查询审核人(即查询当前正在审核的User)
        User auditor = selectCurrentAuditor();

        //查询被查询的报名表相关信息
        CompleteRegisterVO completeRegisterVO = selectByRegisterIdGetCompleteRegister(registerId);
        User user = completeRegisterVO.getUser();
        Register register = completeRegisterVO.getRegister();

        if (user == null && register == null) {
            return ResponseResult.errorResult(400, "用户未注册或报名表不存在");
        }

        if ("2".equals(isApprovedFlag)) {
            //更改状态为2 已通过
            LambdaUpdateWrapper<Register> registerLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            registerLambdaUpdateWrapper.eq(Register::getId, registerId)
                    .set(Register::getStatus, "2")
                    .set(Register::getUpdateTime, new Timestamp(System.currentTimeMillis()))
                    .set(Register::getUpdateBy, auditor.getUsername());
            registerMapper.update(null, registerLambdaUpdateWrapper);

            register.setStatus("2"); // 更新register对象中的状态字段

            //将user设置为预备成员
            LambdaUpdateWrapper<UserRole> userRoleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            userRoleLambdaUpdateWrapper.eq(UserRole::getUserId, user.getId())
                    .set(UserRole::getRoleId, 5L)
                    .set(UserRole::getUpdateTime, new Timestamp(System.currentTimeMillis()))
                    .set(UserRole::getUpdateBy, auditor.getUsername());
            ;
            userRoleMapper.update(null, userRoleLambdaUpdateWrapper);

            return ResponseResult.successResult(200, "成功修改报名表状态为已通过，并将其添加至预备成员。");
        } else if ("3".equals(isApprovedFlag)) {
            //更改状态为3 未通过
            LambdaUpdateWrapper<Register> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Register::getId, registerId)
                    .set(Register::getStatus, "3")
                    .set(Register::getUpdateTime, new Timestamp(System.currentTimeMillis()))
                    .set(Register::getUpdateBy, auditor.getUsername());
            registerMapper.update(null, updateWrapper);

            register.setStatus("3"); // 更新register对象中的状态字段

            //不用修改UserRole，默认roleId为6 未加入

            return ResponseResult.successResult(200, "成功修改报名表状态为未通过");
        }
        return ResponseResult.errorResult(400, "修改报名表状态失败!");
    }

    @Override
    public ResponseResult editRegister(RegisterDTO registerDTO) {
        Long userId = SecurityUtils.getUserId();
        //根据用户查询报名表
        LambdaQueryWrapper<Register> registerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        registerLambdaQueryWrapper.eq(Register::getUserId, userId);
        Register tempRegister = registerMapper.selectById(registerLambdaQueryWrapper);
        //根据用户Id查询用户
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId, userId);
        User tempUser = userMapper.selectById(userLambdaQueryWrapper);

        if (tempRegister == null) {
            return ResponseResult.errorResult(400, "用户尚未提交过报名表，无法修改。");
        }

        if ("1".equals(tempRegister.getStatus()) || "2".equals(tempRegister.getStatus()) || "3".equals(tempRegister.getStatus())) {
            return ResponseResult.errorResult(400, "该报名表已被审核，无法修改。");
        }

        //未审核过则更新状态和update_by等信息
        LambdaUpdateWrapper<Register> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Register::getId, tempRegister.getId())
                .set(Register::getUpdateTime, new Timestamp(System.currentTimeMillis()))
                .set(Register::getUpdateBy, tempUser.getUsername())
                .set(Register::getUpdateTime, new Timestamp(System.currentTimeMillis()))
                .set(Register::getUpdateTime, new Timestamp(System.currentTimeMillis()))
                .set(Register::getUpdateBy, tempUser.getUsername());
        registerMapper.update(null, updateWrapper);

        return ResponseResult.successResult(200, "提交报名表成功!");
    }

    //一大堆查询操作
    @Override
    public ResponseResult formalView(Long registerId) {
        //查询审核人(即查询当前正在审核的User)
        User auditor = selectCurrentAuditor();

        //查询被查看的报名表和报名人
        CompleteRegisterVO completeRegisterVO = selectByRegisterIdGetCompleteRegister(registerId);

        if (completeRegisterVO == null) {
            return ResponseResult.errorResult(400, "用户未注册或报名表不存在");
        } else if ("2".equals(completeRegisterVO.getRegister().getStatus()) || "3".equals(completeRegisterVO.getRegister().getStatus())) {
            //如果这张报名表已经被审核过了，直接返回审核后的报名表，不用更新报名表状态
            return ResponseResult.successResult(completeRegisterVO);
        } else {
            //未审核过则更新状态和update_by等信息
            LambdaUpdateWrapper<Register> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Register::getId, registerId)
                    .set(Register::getStatus, "1")
                    .set(Register::getUpdateTime, new Timestamp(System.currentTimeMillis()))
                    .set(Register::getUpdateBy, auditor.getUsername());
            registerMapper.update(null, updateWrapper);

            completeRegisterVO.getRegister().setStatus("1"); // 更新register对象中的状态字段

            return ResponseResult.successResult(completeRegisterVO);
        }
    }

    @Override
    public ResponseResult selectByGradeName(String gradeName) {
        //根据年级查询用户
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getGradeName, gradeName);
        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);

        List<SectionalRegisterVO> sectionalRegisterVOList = selectByUserGetSectionalRegisterVO(userList);
        return ResponseResult.successResult(sectionalRegisterVOList);
    }

    @Override
    public ResponseResult selectBySubjectId(Long subjectId) {
        //根据专业查询用户
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getSubjectId, subjectId);
        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);

        List<SectionalRegisterVO> sectionalRegisterVOList = selectByUserGetSectionalRegisterVO(userList);

        return ResponseResult.successResult(sectionalRegisterVOList);
    }

    @Override
    public ResponseResult selectByDesireDepartmentId(Long desireDepartmentId) {
        //根据申请组别查询报名表
        LambdaQueryWrapper<Register> registerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        registerLambdaQueryWrapper.eq(Register::getDesireDepartmentId, desireDepartmentId);
        List<Register> registerList = registerMapper.selectList(registerLambdaQueryWrapper);

        List<SectionalRegisterVO> sectionalRegisterVOList = selectByRegisterGetSectionalRegisterVO(registerList);

        return ResponseResult.successResult(sectionalRegisterVOList);
    }

    @Override
    public ResponseResult selectByStatus(String status) {
        //根据报名表状态查询报名表
        LambdaQueryWrapper<Register> registerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        registerLambdaQueryWrapper.eq(Register::getStatus, status);
        List<Register> registerList = registerMapper.selectList(registerLambdaQueryWrapper);

        List<SectionalRegisterVO> sectionalRegisterVOList = selectByRegisterGetSectionalRegisterVO(registerList);

        return ResponseResult.successResult(sectionalRegisterVOList);
    }

    private User selectCurrentAuditor() {
        //查询审核人(即查询当前正在审核的User)
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId, SecurityUtils.getUserId());
        User auditor = userMapper.selectById(userLambdaQueryWrapper);
        return auditor;
    }

    private CompleteRegisterVO selectByRegisterIdGetCompleteRegister(Long registerId) {
        //查询报名表
        LambdaQueryWrapper<Register> registerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        registerLambdaQueryWrapper.eq(Register::getId, registerId);
        Register register = registerMapper.selectById(registerLambdaQueryWrapper);
        //查询用户
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId, register.getUserId());
        User user = userMapper.selectById(userLambdaQueryWrapper);

        if (register != null && user != null) {
            return new CompleteRegisterVO(user, register);
        } else {
            return null;
        }
    }

    private List<SectionalRegisterVO> selectByUserGetSectionalRegisterVO(List<User> userList) {
        List<SectionalRegisterVO> sectionalRegisterVOList = new ArrayList<>();
        for (User u : userList) {
            //根据用户查询报名表
            LambdaQueryWrapper<Register> registerLambdaQueryWrapper = new LambdaQueryWrapper<>();
            registerLambdaQueryWrapper.eq(Register::getUserId, u.getId());
            Register register = registerMapper.selectById(registerLambdaQueryWrapper);
            if (register != null) {
                SectionalRegisterVO sectionalRegisterVO = new SectionalRegisterVO(
                        u.getId(), register.getId(), u.getName(), u.getNickname(),
                        u.getAvatarUrl(), u.getSubjectId(), u.getGradeName(),
                        register.getDesireDepartmentId(), register.getStatus());
                sectionalRegisterVOList.add(sectionalRegisterVO);
            }
        }
        return sectionalRegisterVOList;
    }

    private List<SectionalRegisterVO> selectByRegisterGetSectionalRegisterVO(List<Register> registerList) {
        List<SectionalRegisterVO> sectionalRegisterVOList = new ArrayList<>();
        for (Register r : registerList) {
            //根据报名表查询用户
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getId, r.getUserId());
            User user = userMapper.selectById(userLambdaQueryWrapper);
            if (user != null) {
                SectionalRegisterVO sectionalRegisterVO = new SectionalRegisterVO(
                        user.getId(), r.getId(), user.getName(), user.getNickname(),
                        user.getAvatarUrl(), user.getSubjectId(), user.getGradeName(),
                        r.getDesireDepartmentId(), r.getStatus());
                sectionalRegisterVOList.add(sectionalRegisterVO);
            }
        }
        return sectionalRegisterVOList;
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
