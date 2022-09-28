package com.example.demo.web;


import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/roles")
public class RoleController {


    private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/change")
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
