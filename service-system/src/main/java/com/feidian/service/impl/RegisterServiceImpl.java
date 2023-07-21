package com.feidian.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.dto.RegisterDTO;
import com.feidian.mapper.RegisterMapper;
import com.feidian.po.Register;
import com.feidian.util.SecurityUtils;
import feidian.responseResult.ResponseResult;
import feidian.util.RedisCache;
import feidian.util.serviceUtil.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.feidian.service.RegisterService;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;



@Service("registerService")
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, Register> implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private RedisCache redisCache;

    private static final int DESIRE_DEPARTMENT_ID_MAX_LENGTH = 11;
    private static final int RESUME_MAX_LENGTH = 512;
    private static final int DIRECTION_MAX_LENGTH = 512;
    private static final int ARRANGEMENT_MAX_LENGTH = 512;
    private static final int REASON_MAX_LENGTH = 1024;

    @Override
    public ResponseResult submitRegister(RegisterDTO registerDTO) {

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
        if(submitImageResponseResult.getCode() == 400){
            return submitImageResponseResult;
        }
        String key = SecurityUtils.getLoginUser().getUsername();
        redisCache.setCacheObject(key, submitImageResponseResult.getData());

        return ResponseResult.successResult(200,"上传图片文件成功");
    }


    //一大堆查询操作
    @Override
    public ResponseResult formalView(Long registerId) {
        //查询报名表
        LambdaQueryWrapper<Register> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Register::getId,registerId);
        Register register = registerMapper.selectById(queryWrapper);

        //更改状态为已查看
        if (register != null) {
            LambdaUpdateWrapper<Register> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Register::getId, registerId)
                         .set(Register::getStatus, "1");
            registerMapper.update(null, updateWrapper);

            register.setStatus("1"); // 更新register对象中的状态字段
        }else {
            return ResponseResult.errorResult(400,"报名表不存在");
        }

        return ResponseResult.successResult(register);
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
