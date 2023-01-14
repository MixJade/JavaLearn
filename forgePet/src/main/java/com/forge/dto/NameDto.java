package com.forge.dto;

/**
 * 部分下拉框查询名字与对应id
 */
public class NameDto {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色姓名
     */
    private String roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
