package cn.tedu.baking.controller;

import cn.tedu.baking.mapper.UserMapper;
import cn.tedu.baking.pojo.dto.UserLoginDTO;
import cn.tedu.baking.pojo.dto.UserRegDTO;
import cn.tedu.baking.pojo.dto.UserUpdateDTO;
import cn.tedu.baking.pojo.entity.User;
import cn.tedu.baking.pojo.vo.UserVO;
import cn.tedu.baking.response.JsonResult;
import cn.tedu.baking.response.StatusCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;

@RestController
@RequestMapping("/v1/users/")
public class UserController {
    @Autowired
    UserMapper mapper;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("reg")
    public JsonResult reg(@RequestBody UserRegDTO userRegDTO) {
        UserVO userVO = mapper.selectByUserName(userRegDTO.getUserName());
        if (userVO != null) {
            return new JsonResult(StatusCode.USERNAME_ALREADY_EXISTS);
        }
        User user = new User();
        BeanUtils.copyProperties(userRegDTO, user);
        user.setCreateTime(new Date());
        //Password encryption
        user.setPassword(encoder.encode(user.getPassword()));
        mapper.insert(user);

        return JsonResult.ok();
    }

    @Autowired
    AuthenticationManager manager;

    @PostMapping("login")
    public JsonResult login(@RequestBody UserLoginDTO userLoginDTO) {
        System.out.println("userLoginDTO = " + userLoginDTO);
        //Start the certification process
        Authentication result = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getUserName(), userLoginDTO.getPassword()));
        //Save authentication results to the Security context
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(result);
        //Respond to the client with successfully authenticated user information
        return JsonResult.ok(result.getPrincipal());
    }

    @GetMapping("logout")
    public JsonResult logout() {
        SecurityContextHolder.clearContext();

        return JsonResult.ok();
    }

    @PostMapping("update")
    public JsonResult update(@RequestBody UserUpdateDTO userUpdateDTO) {
        //Determine whether you need to change your avatar
        if (userUpdateDTO.getImgUrl() != null) {
            //Get the original image path
//            String imgUrl = mapper.selectImgUrlById(userUpdateDTO.getId());
            //Delete the corresponding image file
//            new File("d:/files" + imgUrl).delete();
        }

        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO, user);
        mapper.update(user);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public JsonResult list() {
        return JsonResult.ok(mapper.select());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{id}/delete")
    public JsonResult delete(@PathVariable Long id) {
        mapper.deleteById(id);
        return JsonResult.ok();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{id}/{isAdmin}/change")
    public JsonResult change(@PathVariable Long id,
                             @PathVariable Integer isAdmin) {
        User user = new User();
        user.setId(id);
        user.setIsAdmin(isAdmin);
        mapper.update(user);

        return JsonResult.ok();
    }
}
