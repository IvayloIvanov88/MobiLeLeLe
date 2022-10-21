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

//    @Test
    @WithMockUser(username = "user", roles = {"USER",})
    public void testGetOfferDetailsShouldWork() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/offers/1/details")).
                andExpect(status().is3xxRedirection()).
                andExpect(model().attributeExists("offer")).
                andExpect(view().name("details"));
    }
}
