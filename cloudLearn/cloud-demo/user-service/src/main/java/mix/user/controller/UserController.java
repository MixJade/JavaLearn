package mix.user.controller;

import mix.user.pojo.User;
import mix.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 路径： /api/user/1
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Integer id) {
        return userService.queryById(id);
    }
}
