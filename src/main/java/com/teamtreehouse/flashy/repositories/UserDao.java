package com.teamtreehouse.flashy.repositories;

import com.teamtreehouse.flashy.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
  User findByUsername(String username);
}
