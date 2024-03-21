package latsko.registrationlogin.service;

import latsko.registrationlogin.dto.UserDto;
import latsko.registrationlogin.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
