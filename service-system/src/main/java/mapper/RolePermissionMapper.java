package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feiidan.po.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
