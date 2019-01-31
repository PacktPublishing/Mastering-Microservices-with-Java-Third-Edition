package com.packtpub.mmj.user.domain.service;

import com.packtpub.mmj.user.common.DuplicateUserException;
import com.packtpub.mmj.user.common.InvalidUserException;
import com.packtpub.mmj.user.domain.model.entity.Entity;
import com.packtpub.mmj.user.domain.model.entity.User;
import com.packtpub.mmj.user.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sourabh Sharma
 */
@Service("userService")
public class UserServiceImpl extends BaseService<User, String>
    implements UserService {

  private UserRepository<User, String> userRepository;

  /**
   * @param userRepository
   */
  @Autowired
  public UserServiceImpl(UserRepository<User, String> userRepository) {
    super(userRepository);
    this.userRepository = userRepository;
  }

  @Override
  public void add(User user) throws Exception {
    if (userRepository.containsName(user.getName())) {
      Object[] args = {user.getName()};
      throw new DuplicateUserException("duplicateUser", args);
    }
    if (user.getName() == null || "".equals(user.getName())) {
      Object[] args = {"User with null or empty name"};
      throw new InvalidUserException("invalidUser", args);
    }
    super.add(user);
  }

  /**
   * @param name
   */
  @Override
  public Collection<User> findByName(String name) throws Exception {
    return userRepository.findByName(name);
  }

  /**
   * @param user
   */
  @Override
  public void update(User user) throws Exception {
    userRepository.update(user);
  }

  /**
   * @param id
   */
  @Override
  public void delete(String id) throws Exception {
    userRepository.remove(id);
  }

  /**
   * @param id
   */
  @Override
  public Entity findById(String id) throws Exception {
    return userRepository.get(id);
  }

  /**
   * @param name
   */
  @Override
  public Collection<User> findByCriteria(Map<String, ArrayList<String>> name) throws Exception {
    throw new UnsupportedOperationException(
        "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
