package com.feidian.runner;

import com.feidian.bo.GraduatesBO;
import com.feidian.mapper.GraduatesMapper;
import com.feidian.util.BeanCopyUtils;
import com.feidian.util.RedisCache;
import com.feidian.vo.GraduatesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//项目启动时便加载的代码
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private GraduatesMapper graduatesMapper;

    //在启动时运行，且只会运行一次：将文章的浏览量存到redis里面去
    @Override
    public void run(String... args) throws Exception {
        //查询毕业生信息
        List<GraduatesBO> graduates = graduatesMapper.getGraduatesMessage();
        ArrayList<GraduatesVO> graduatesVOS = new ArrayList<>();
        for(GraduatesBO graduate:graduates){
            GraduatesVO vo = BeanCopyUtils.copyProperty(graduate, GraduatesVO.class);
            vo.setFaculty(graduate.getFaculty().getFacultyName());
            vo.setDepartment(graduate.getDepartment().getDepartmentName());
            vo.setSubject(graduate.getSubject().getSubjectName());
            graduatesVOS.add(vo);
        }

        redisCache.setCacheObject("graduates::message", graduatesVOS);
    }
}