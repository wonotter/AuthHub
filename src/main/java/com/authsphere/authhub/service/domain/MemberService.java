package com.authsphere.authhub.service.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authsphere.authhub.domain.Member;
import com.authsphere.authhub.domain.oauth2.OAuth2UserInfo;
import com.authsphere.authhub.exception.commonexception.DuplicateException;
import com.authsphere.authhub.exception.commonexception.NotFoundException;
import com.authsphere.authhub.exception.commonexception.UnauthorizedException;
import com.authsphere.authhub.exception.domain.ExceptionType;
import com.authsphere.authhub.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getOrElseThrow(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException(ExceptionType.MEMBER_NOT_FOUND));
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(ExceptionType.MEMBER_NOT_FOUND));
    }

    public void findByEmail(String email) {
        memberRepository.findByEmail(email)
            .ifPresent(member -> {
                throw new DuplicateException(ExceptionType.DUPLICATE_MEMBER);
            });
    }

    public Member findByEmailWithRolesAndGroups(String email) {
        return memberRepository.findByEmailWithRolesAndGroups(email)
            .orElseThrow(() -> new NotFoundException(ExceptionType.MEMBER_NOT_FOUND));
    }


    public void verifyPassword(Member member, String password) {
        if (!member.matchPassword(password, passwordEncoder)) {
            throw new UnauthorizedException(ExceptionType.INVALID_PASSWORD);
        }
    }

    @Transactional
    public Member registerSocialMember(OAuth2UserInfo oAuth2UserInfo) {
        Member member = Member.fromUserInfo(oAuth2UserInfo);
        return memberRepository.save(member);
    }

    @Transactional
    public Member registerMember(Member member) {
        findByEmail(member.getEmail());
        member.encodePassword(passwordEncoder);
        return memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
