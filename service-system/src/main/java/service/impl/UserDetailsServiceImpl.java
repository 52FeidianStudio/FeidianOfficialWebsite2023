package service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feidian.bo.LoginUser;
import com.feidian.mapper.MenuMapper;
import com.feidian.mapper.RoleMenuMapper;
import com.feidian.mapper.UserMapper;
import com.feidian.mapper.UserRoleMapper;
import com.feidian.po.SysMenu;
import com.feidian.po.SysRoleMenu;
import com.feidian.po.SysUser;
import com.feidian.po.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser user = userMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }

        //测试写法
        //List<String> list = new ArrayList<>(Arrays.asList("test","admin"));

        //查询对应的权限信息并将其封装成一个list集合
        Long userId = user.getId();
        List<String> authenticationList = new ArrayList<>();
        // 查询用户角色
        LambdaQueryWrapper<SysUserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> userRoles = userRoleMapper.selectList(userRoleWrapper);

        List<Long> roleIds = userRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());

        if (!roleIds.isEmpty()) {
            // 查询角色权限
            LambdaQueryWrapper<SysRoleMenu> roleMenuWrapper = new LambdaQueryWrapper<>();
            roleMenuWrapper.in(SysRoleMenu::getRoleId, roleIds);
            List<SysRoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuWrapper);

            List<Long> menuIds = roleMenus.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toList());

            // 查询权限菜单
            LambdaQueryWrapper<SysMenu> menuWrapper = new LambdaQueryWrapper<>();
            menuWrapper.in(SysMenu::getId, menuIds);
            List<SysMenu> menus = menuMapper.selectList(menuWrapper);

            // 用户权限菜单
            for (SysMenu menu : menus) {
                authenticationList.add(menu.getMenuName());
            }
        }

        //把数据封装成UserDetails返回
        LoginUser loginUser = new LoginUser(user, authenticationList);
        return loginUser;
    }
}