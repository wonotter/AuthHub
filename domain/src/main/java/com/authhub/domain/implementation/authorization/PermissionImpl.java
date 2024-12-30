package com.authhub.domain.implementation.authorization;

import java.util.ArrayList;
import java.util.List;

import com.authhub.domain.interfaces.authentication.User;
import com.authhub.domain.interfaces.authorization.Permission;
import com.authhub.domain.interfaces.authorization.Role;

public class PermissionImpl implements Permission {
    private final String name;
    private final String description;
    private final List<User> users;
    private final List<Role> roles;

    public PermissionImpl(String name, String description) {
        this.name = name;
        this.description = description;
        this.users = new ArrayList<>();
        this.roles = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    /**
     * 권한에 새로운 사용자를 추가
     * @param user 추가할 사용자 객체
     */
    public void addUser(User user){
        this.users.add(user);
    }

    /**
     * 권한에 새로운 역할을 추가
     * @param role 추가할 역할 객체
     */
    public void addRole(Role role){
        this.roles.add(role);
    }
}
