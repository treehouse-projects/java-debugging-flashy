package com.teamtreehouse.flashy.services;

import com.teamtreehouse.flashy.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User findByUsername(String username);
}
