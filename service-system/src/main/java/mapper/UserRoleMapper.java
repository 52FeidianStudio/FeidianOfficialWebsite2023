package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feiidan.po.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleMapper  extends BaseMapper<UserRole> {
}
