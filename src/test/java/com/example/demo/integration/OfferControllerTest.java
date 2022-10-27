package com.example.demo.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTest {

    private static final String REDIRECT_USER_LOGIN ="/users/login";
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Ivo@mail.bg", roles = {"ADMIN", "USER", "MODERATOR"})
    public void testGetAllOffersShouldWork() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/offers/all")).
                andExpect(status().isOk()).
                andExpect(view().name("offers")).
                andExpect(model().attributeExists("offers"));

    }

    @Test
    public void testGetAllOffersShouldRedirectToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/all")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(username = "user@gmail.com", roles = {"USER"})
    public void testGetOfferDetailsShouldWork() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/offers/1/details")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("offer")).
                andExpect(view().name("details"));
    }
}
