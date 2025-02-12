package com.authsphere.authhub.domain.oauth2;

import java.util.HashMap;
import java.util.Map;

import com.authsphere.authhub.domain.Member;

public class DefaultMemberUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final Member member;

    public DefaultMemberUserInfo(Member member) {
        this.member = member;
        this.attributes = new HashMap<>();
        this.attributes.put("id", member.getId());
        this.attributes.put("email", member.getEmail());
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
        return member.getEmail();
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
