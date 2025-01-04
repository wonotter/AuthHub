package com.authhub.domain.implementation.authorization;

import java.util.List;

import com.authhub.domain.interfaces.authentication.User;
import com.authhub.domain.interfaces.authorization.Group;
import com.authhub.domain.interfaces.authorization.Role;

public class GroupImpl implements Group {
    private final String name;
    private final List<Role> roles;
    private final List<User> users;

    public GroupImpl(String name, List<Role> roles, List<User> users) {
        this.name = name;
        this.roles = roles;
        this.users = users;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }
}
