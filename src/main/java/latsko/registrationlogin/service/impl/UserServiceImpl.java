package latsko.registrationlogin.service.impl;

import latsko.registrationlogin.dto.UserDto;
import latsko.registrationlogin.entity.Role;
import latsko.registrationlogin.entity.User;
import latsko.registrationlogin.repository.RoleRepository;
import latsko.registrationlogin.repository.UserRepository;
import latsko.registrationlogin.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null) {
            role = checkIfRoleExists();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .toList();
    }

    private Role checkIfRoleExists() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    private UserDto mapToUserDto(User user) {
        String[] fullName = user.getName().split(" ");
        return new UserDto(
                user.getId(),
                fullName[0],
                fullName[1],
                user.getEmail(),
                user.getPassword());
    }
}
