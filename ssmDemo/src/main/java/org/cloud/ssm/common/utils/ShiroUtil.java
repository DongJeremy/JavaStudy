package org.cloud.ssm.common.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.cloud.ssm.entity.User;
import org.cloud.ssm.entity.UserRole;

public class ShiroUtil {
    /**
     * 获取当前用户对象
     */
    public static User getSubject() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
    
    /**
     * 获取当前用户角色列表
     */
    public static List<UserRole> getSubjectRoles() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        // 如果用户为空，则返回空列表
        if (user == null) {
            user = new User();
        }

        return user.getUserRoles();
    }
}
