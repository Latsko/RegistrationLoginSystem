package latsko.registrationlogin.service.impl;

import latsko.registrationlogin.dto.UserDto;
import latsko.registrationlogin.entity.Role;
import latsko.registrationlogin.entity.User;
import latsko.registrationlogin.repository.RoleRepository;
import latsko.registrationlogin.repository.UserRepository;
import latsko.registrationlogin.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(userDto.getPassword());

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null) {
            role = checkIfRoleExists();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    private Role checkIfRoleExists() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
