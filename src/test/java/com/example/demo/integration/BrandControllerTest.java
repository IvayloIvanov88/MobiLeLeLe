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
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllBrandsShouldRedirectToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brands/all")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(username = "admin@example.bg", roles = {"ADMIN"})
    public void getAllBrandsShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brands/all")).
                andExpect(status().isOk()).
                andExpect(view().name("brands")).
                andExpect(model().attributeExists("brands"));
    }


    @Test
    @WithMockUser(username = "admin@example.bg", roles = {"ADMIN"})
    public void getAddBrandShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brands/add")).
                andExpect(status().isOk()).
                andExpect(view().name("brand-add"));
    }

    @Test
    @WithMockUser(username = "admin@example.bg", roles = {"ADMIN"})
    public void getBrandsDetailsShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brands/1/brand-details")).
                andExpect(status().isOk()).
                andExpect(view().name("brand-details"));

    }

    @Test
    @WithMockUser(username = "admin@example.bg", roles = {"ADMIN"})
    public void getBrandsDetailsShouldRedirectWhenBrandDidNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brands/10000/brand-details")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/brands/add"));
    }

    @Test
    @WithMockUser(username = "admin@example.bg", roles = {"ADMIN"})
    public void getUpdateOfferShouldWork() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brands/brands/1/update")).
                andExpect(status().isOk()).
                andExpect(view().name("brand-update"));

    }
}
