package com.example.demo.service.impl;

import com.example.demo.model.entity.UserEntity;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.entity.service.RoleServiceModel;
import com.example.demo.model.service.UserLoginServiceModel;
import com.example.demo.model.service.UserRegisterServiceModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.valueOf;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
//    private final UserDetailsService appUserDetailsService;
    private final String adminPass;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           @Lazy PasswordEncoder passwordEncoder,
//                           UserDetailsService appUserDetailsService,
                           ModelMapper modelMapper, RoleService roleService, @Value("${app.default.admin.password}") String adminPass) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
//        this.appUserDetailsService = appUserDetailsService;
        this.adminPass = adminPass;
    }


    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setRole(UserRoleEnum.MODERATOR);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRoleEnum.USER);

            adminRole = userRoleRepository.save(adminRole);
            moderatorRole = userRoleRepository.save(moderatorRole);
            userRole = userRoleRepository.save(userRole);

            initAdmin(List.of(adminRole, moderatorRole));
            initModerator(List.of(moderatorRole));
            initUser(List.of(userRole));
        }
    }

    private void initAdmin(List<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity().
                setUserRoles(roles).
                setFirstName("Ivo").
                setLastName("Ivanov").
                setUsername("admin").
                setEmail("admin@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity().
                setUserRoles(roles).
                setFirstName("Moderator").
                setLastName("Moderatorov").
                setUsername("Moderator").
                setEmail("moderator@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRoleEntity> roles) {
        UserEntity user = new UserEntity().
                setUserRoles(roles).
                setFirstName("User").
                setLastName("Userov").
                setUsername("user").
                setEmail("user@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(user);
    }


    @Override
    public boolean passwordsCheck(UserLoginServiceModel loginServiceModel) {
        Optional<UserEntity> optUser = userRepository.findByUsername(loginServiceModel.getUsername());

        if (optUser.isEmpty()) return false;
        UserEntity user = optUser.get();

        return passwordEncoder.matches(loginServiceModel.getPassword(), user.getPassword());
    }

    //    @Override
//    public void registerAndLogin(UserRegisterServiceModel userRegisterServiceModel) {
//        UserEntity newUser =
//                new UserEntity().
//                        setEmail(userRegisterServiceModel.getEmail()).
//                        setFirstName(userRegisterServiceModel.getFirstName()).
//                        setLastName(userRegisterServiceModel.getLastName()).
//                        setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));
//
//        userRepository.save(newUser);
//
//        UserDetails userDetails =
//                appUserDetailsService.loadUserByUsername(newUser.getEmail());
//
//        Authentication auth =
//                new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        userDetails.getPassword(),
//                        userDetails.getAuthorities()
//                );
//
//        SecurityContextHolder.
//                getContext().
//                setAuthentication(auth);
//    }
    @Override
    public void register(UserRegisterServiceModel userRegisterServiceModel) {

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        UserEntity newUser = new UserEntity()
                .setFirstName(userRegisterServiceModel.getFirstName())
                .setLastName(userRegisterServiceModel.getLastName())
                .setUsername(userRegisterServiceModel.getUsername())
                .setEmail(userRegisterServiceModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()))
                .setActive(true)
                .setUserRoles(List.of(userRole));

        userRepository.save(newUser);
    }

    @Override
    public boolean isUsernameFree(String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        return optUser.isEmpty();
    }

    @Override
    public UserEntity getOrCreateUser(String email) {

        Objects.requireNonNull(email);

        Optional<UserEntity> userEntityOpt = userRepository.findByEmail(email);

        return userEntityOpt.orElseGet(() -> createUser(email));
    }


    private UserEntity createUser(String email) {
        log.info("Creating a new user with email [GDPR]");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        UserRoleEntity userRole = new UserRoleEntity().setRole(UserRoleEnum.USER);

        userEntity.setUserRoles(List.of(userRole));
        return userRepository.save(userEntity);
    }

    @Override
    public List<String> findAllUsernames() {
        return userRepository.findAllUsernames();
    }

    @Override
    public void changeRole(String username, UserRoleEnum valueOf) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        List<UserRoleEntity> roles = user.getUserRoles();
        UserRoleEntity newRole = roleService.findByName(valueOf).getRole();

        Stream<UserRoleEntity> userRoleEntityStream = user.getUserRoles().stream().filter(r -> r.getRole().equals(valueOf));
        if (userRoleEntityStream.findAny().isPresent()) {
            roles.clear();
        }
        roles.add(newRole);
        userRepository.save(user.setUserRoles(roles));
    }

}
