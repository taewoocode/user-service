package com.example.user_service.user.service;

import com.example.user_service.user.dto.UserRegisterInfo;

public interface UserService {

	UserRegisterInfo.UserRegisterResponse saveUser(UserRegisterInfo.UserRegisterRequest request);
}
