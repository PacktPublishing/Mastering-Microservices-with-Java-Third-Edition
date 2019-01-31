package com.packtpub.mmj.user.resources;

import com.packtpub.mmj.user.common.DuplicateUserException;
import com.packtpub.mmj.user.common.InvalidUserException;
import com.packtpub.mmj.user.common.UserNotFoundException;
import com.packtpub.mmj.user.domain.model.entity.User;
import com.packtpub.mmj.user.domain.service.UserService;
import com.packtpub.mmj.user.domain.valueobject.UserVO;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sourabh Sharma
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

  /**
   *
   */
  protected static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

  /**
   *
   */
  protected UserService userService;

  /**
   * @param userService
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Fetch users with the specified name. A partial case-insensitive match is supported. So
   * <code>http://.../user/rest</code> will find any users with upper or lower case 'rest' in their
   * name.
   *
   * @param name
   * @return A non-null, non-empty collection of users.
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Collection<User>> findByName(@RequestParam("name") String name)
      throws Exception {
    logger.info("findByName() invoked for {} ", name);
    name = name.trim().toLowerCase();
    Collection<User> users;
    try {
      users = userService.findByName(name);
    } catch (UserNotFoundException ex) {
      logger.error("Exception raised findByName REST Call", ex);
      throw ex;
    } catch (Exception ex) {
      logger.error("Exception raised findByName REST Call", ex);
      throw ex;
    }
    return users.size() > 0 ? new ResponseEntity<>(users, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Fetch users with the given id. <code>http://.../v1/users/{id}</code> will return user with
   * given id.
   *
   * @param id
   * @return A non-null, non-empty collection of users.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<User> findById(@PathVariable("id") String id) throws Exception {
    logger.info("findById() invoked for {} ", id);
    id = id.trim();
    User user;
    try {
      user = userService.findById(id);
    } catch (Exception ex) {
      logger.error("Exception raised findById REST Call {0}", ex);
      throw ex;
    }
    return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Add user with the specified information.
   *
   * @param userVO
   * @return A non-null user.
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<User> add(@RequestBody UserVO userVO) throws Exception {
    logger.info("add() invoked for {}", userVO);
    User user = User.getDummyUser();
    BeanUtils.copyProperties(userVO, user);
    try {
      userService.add(user);
    } catch (DuplicateUserException | InvalidUserException ex) {
      logger.error("Exception raised add User REST Call {0}", ex);
      throw ex;
    } catch (Exception ex) {
      logger.error("Exception raised add User REST Call {0}", ex);
      throw ex;
    }
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  /**
   * Update existing user with the specified information.
   *
   * @param userVO
   */
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody UserVO userVO)
      throws Exception {
    logger.info("update() invoked for user Id {}", id);
    logger.info(userVO.toString());
    User user = User.getDummyUser();
    BeanUtils.copyProperties(userVO, user);
    try {
      userService.update(id, user);
    } catch (Exception ex) {
      logger.error("Exception raised update User REST Call {0}", ex);
      throw ex;
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Update name of existing user.
   *
   * @param id
   * @param value
   */
  @PatchMapping("/{id}/name")
  public ResponseEntity<Void> patch(@PathVariable("id") String id,
      @RequestParam("value") String value) throws Exception {
    logger.info("patch() invoked for user Id {} and value {}", id, value);
    try {
      User user = userService.findById(id);
      user.setName(value);
      userService.update(id, user);
    } catch (UserNotFoundException ex) {
      logger.error("Exception raised findByName REST Call", ex);
      throw ex;
    } catch (Exception ex) {
      logger.error("Exception raised patch User REST Call {0}", ex);
      throw ex;
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Delete existing user
   *
   * @param id
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable("id") String id) throws Exception {
    logger.info("delete() invoked for user Id {} ", id);
    try {
      userService.delete(id.trim());
    } catch (Exception ex) {
      logger.error("Exception raised delete User REST Call {0}", ex);
      throw ex;
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
