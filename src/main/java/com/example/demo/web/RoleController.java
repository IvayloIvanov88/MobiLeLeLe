package com.example.demo.web;


import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/roles")
public class RoleController {


    private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/change")
//    @PreAuthorize("hasAuthorities('ROLE_ADMIN')")
//        @PreAuthorize("hasRole('ADMIN')")
    private String change(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return "redirect:/users/login";
        }

        model.addAttribute("names", userService.findAllUsernames());
        return "role-change";
    }

    @PostMapping("/change")
    private String changeConfirm(@RequestParam String username,
                                 @RequestParam String role) {

        userService.changeRole(username, UserRoleEnum.valueOf(role.toUpperCase()));

        return "redirect:/";
    }
}
