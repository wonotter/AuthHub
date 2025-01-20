package com.authsphere.authhub.domain.oauth2;

import java.util.Map;

import com.authsphere.authhub.domain.Member;

public class DefaultMemberUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final Member member;

    public DefaultMemberUserInfo(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
        this.attributes.put("id", member.getId());
        this.attributes.put("username", member.getUsername());
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.DEFAULT;
    }
    @Override
    public String getAccessToken() {
        return null;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getId() {
        return member.getId().toString();
    }
    @Override
    public String getEmail() {
        return null;
    }
    @Override
    public String getName() {
        return member.getUsername();
    }
    @Override
    public String getFirstName() {
        return null;
    }
    @Override
    public String getLastName() {
        return null;
    }
    @Override
    public String getNickname() {
        return null;
    }
    @Override
    public String getProfileImageUrl() {
        return null;
    }
}
