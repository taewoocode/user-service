package com.example.user_service.user.service;

import com.example.member_service.member.dto.MemberRegisterInfo;

public interface UserService {

	MemberRegisterInfo.MemberRegisterResponse saveMember(MemberRegisterInfo.MemberRegisterRequest request);
}
