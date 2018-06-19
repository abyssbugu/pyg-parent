package com.pyg.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pyg.manager.service.SellerService;
import com.pyg.pojo.TbSeller;

/**
 * 自定义权限认证
 *
 * @author hubin 1，根据用户名查询数据库 2，判断用户名，密码是否匹配
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    // 注入商家服务对象
    private SellerService sellerService;

    public SellerService getSellerService() {
        return sellerService;
    }

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1，根据用户名查询数据库
        TbSeller seller = sellerService.findOne(username);

        // 判断用户是否存在
        if (seller != null) {
            // 判断商家是否审核通过
            if (seller.getStatus().equals("1")) {

                // 匹配角色
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                // 有权限
                return new User(username, seller.getPassword(), authorities);
            }
        }

        return null;
    }

}
