package com.paymybuddy.paymybuddybackend.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void whenLogin_shouldSucces() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .servletPath("/api/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "m.jackson@gmail.com")
                        .param("password", "1234")
                )
                .andExpect(jsonPath( "$.access_token").exists())
                .andExpect(jsonPath( "$.refresh_token").exists())
                .andExpect(status().isOk());
    }

    @Test
    public void whenLogin_withWrongEmail_shouldUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .servletPath("/api/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization","Bearer eyJ0eXAiO")
                        .param("email", "m.jackson777@gmail.com")
                        .param("password", "1234")
                )
                .andExpect(jsonPath( "$.access_token").doesNotExist())
                .andExpect(jsonPath( "$.refresh_token").doesNotExist())
                .andExpect(jsonPath( "$.error").exists())
                .andExpect(jsonPath( "$.error").value("401"))
                .andExpect(jsonPath( "$.message").exists())
                .andExpect(jsonPath( "$.message").value("Votre identifiant est incorrect"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenLogin_withWrongPassword_shouldUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .servletPath("/api/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization","Bearer eyJ0eXAiO")
                        .param("email", "m.jackson@gmail.com")
                        .param("password", "1234AZERTY")
                )
                .andExpect(jsonPath( "$.access_token").doesNotExist())
                .andExpect(jsonPath( "$.refresh_token").doesNotExist())
                .andExpect(jsonPath( "$.error").exists())
                .andExpect(jsonPath( "$.error").value("401"))
                .andExpect(jsonPath( "$.message").exists())
                .andExpect(jsonPath( "$.message").value("Mot de passe incorrect."))
                .andExpect(status().isUnauthorized());
    }
}
