package com.packtpub.mmj.booking.common.openfeign;

import com.packtpub.mmj.booking.domain.valueobject.UserVO;
import java.util.Collection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("user-service")
public interface UserFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = "/v1/user")
  Collection<UserVO> getUser(@RequestParam("name") String name) throws Exception;

  @RequestMapping(method = RequestMethod.POST, value = "/v1/user")
  UserVO postUser(@RequestBody UserVO user) throws Exception;

  @RequestMapping(method = RequestMethod.PUT, value = "/v1/user/{id}")
  void putUser(@PathVariable("id") long id,  @RequestBody UserVO user) throws Exception;

  @RequestMapping(method = RequestMethod.DELETE, value = "/v1/user/{id}")
  void deleteUser(@PathVariable("id") long id) throws Exception;
}
