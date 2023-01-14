package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.LoginDto;
import com.forge.entity.Employee;
import com.forge.mapper.ClientMapper;
import com.forge.mapper.EmployeeMapper;
import com.forge.shiro.authentic.MyToken;
import com.forge.shiro.authentic.RealmName;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 登录与注册模块
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private EmployeeMapper employeeMapper;
    private ClientMapper clientMapper;

    @Autowired
    public void setEmployeeMapper(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Autowired
    public void setClientMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    /**
     * 管理员登录
     *
     * @param loginDto 用户名与密码与记住我
     * @param session  登录成功的状态
     * @return 登录成功与失败
     */
    @PostMapping("/admin")
    public Result employeeLogin(@RequestBody LoginDto loginDto, HttpSession session) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        boolean remember = loginDto.isRemember();
        // 获取subject
        Subject subject = SecurityUtils.getSubject();
        // 封装到token
        AuthenticationToken token = new MyToken(username, password, remember, RealmName.EmployeeRealm);
        // 进行验证
        return getResult(session, subject, token);
    }

    /**
     * 用户登录
     *
     * @param loginDto 用户名与密码与记住我
     * @param session  登录成功的状态
     * @return 登录成功与失败
     */
    @PostMapping("/user")
    public Result clientLogin(@RequestBody LoginDto loginDto, HttpSession session) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        boolean remember = loginDto.isRemember();
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new MyToken(username, password, remember, RealmName.ClientRealm);
        return getResult(session, subject, token);
    }

    /**
     * 管理员与用户登录的验证
     */
    @NotNull
    private Result getResult(HttpSession session, Subject subject, AuthenticationToken token) {
        try {
            subject.login(token);// 进行验证
            session.setAttribute("user", token.getPrincipal().toString());
            return Result.success("登录成功");
        } catch (IncorrectCredentialsException e) {
            return Result.error("登录失败，密码错误");
        } catch (UnknownAccountException e) {
            return Result.error("登录失败，账号不存在");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.error("登录失败");
        }
    }

    /**
     * 登录成功的管理员信息
     *
     * @return id，账号，名字，电话，照片
     */
    @GetMapping("/admin")
    @RequiresRoles("nurse")
    public Employee getAdmin() {
        try {
            String username = SecurityUtils.getSubject().getPrincipal().toString();
            return employeeMapper.selectAName(username);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * 登录成功的用户信息
     *
     * @return 名字
     */
    @GetMapping("/user")
    public String getUser() {
        try {
            String username = SecurityUtils.getSubject().getPrincipal().toString();
            return clientMapper.selectAName(username);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * 管理员修改自己的密码
     */
    @PutMapping("/admin")
    public Result updatePwd(@RequestBody Employee employee) {
        if (employee.getEmployeePassword().equals("")) {
            employee.setEmployeePassword(null);
        } else {
            Md5Hash md5Hash = new Md5Hash(employee.getEmployeePassword(), "pet");
            employee.setEmployeePassword(md5Hash.toHex());
        }
        if (employeeMapper.updateById(employee) > 0) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }
}
