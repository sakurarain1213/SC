package com.example.hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hou.entity.LogUser;
import com.example.hou.entity.SysPermission;
import com.example.hou.entity.SysUser;
import com.example.hou.mapper.SysPermissionMapper;
import com.example.hou.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //接口是不能够直接创建Bean（mapper）的，实际上项目运行时SpringBoot底层使用了动态代理创建了这个Bean
    //这里不够严谨   就修饰一下@Autowired  用  (required=false)
    @Autowired (required=false)
    private SysPermissionMapper sysPermissionMapper;

    @Autowired (required=false)
    private SysUserMapper userMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //需要构造出 org.springframework.security.core.userdetails.User 对象并返回

        System.out.println("用户名："+username);
        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }

        //根据用户名查询用户
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().eq("account", username));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }


        List<String> permissionsList = new ArrayList<>();
            /*
        if (user != null) {
            //获取该用户所拥有的权限
            List<SysPermission> sysPermissions = sysPermissionMapper.selectPermissionList(user.getUserId());

            // 声明用户授权
            sysPermissions.forEach(sysPermission -> {
                permissionsList.add(sysPermission.getPermissionCode());

            });
        }
        */
        try {
            // 获取该用户所拥有的权限
            List<SysPermission> sysPermissions = sysPermissionMapper.selectPermissionList(user.getUserId());
            if (sysPermissions != null) {
                sysPermissions.stream()
                        .filter(Objects::nonNull)
                        .map(SysPermission::getPermissionCode)
                        .filter(Objects::nonNull)
                        .filter(code -> !code.trim().isEmpty())
                        .forEach(permissionsList::add);
            }
        } catch (Exception e) {
            // 日志记录异常，处理或抛出
            throw new RuntimeException("获取用户权限时发生异常", e);
        }

        //返回用户信息
        return new LogUser(user,permissionsList);

    }









    //加密算法，把加密后的密码update你用户表的数据库用户的密码上   可以测试一下
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }


}