package mix.user.service;

import mix.user.mapper.UserMapper;
import mix.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User queryById(Integer id) {
        return userMapper.findById(id);
    }
}