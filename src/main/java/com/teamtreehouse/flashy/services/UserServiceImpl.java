package com.teamtreehouse.flashy.services;

import com.teamtreehouse.flashy.domain.User;
import com.teamtreehouse.flashy.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Load the user from the database (throw exception if not found)
    User user = userDao.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    // Return the user object
    return user;
  }
}
