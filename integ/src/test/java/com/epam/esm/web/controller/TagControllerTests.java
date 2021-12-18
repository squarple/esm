package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.web.config.TestApplicationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestApplicationConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
class TagControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TagServiceImpl tagService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getTags_ReturnListOfTags() throws Exception {
        when(tagService.getAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void givenWac_whenServletContext_thenItProvidesTagController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("tagController"));
    }

    @Test
    void deleteTag() throws Exception {
        Tag tag = Tag.builder().setName("name").build();
        Tag expectedTag = Tag.builder().setId(1L).setName("name").build();
        when(tagService.save(tag)).thenReturn(expectedTag);
        mockMvc.perform(delete("/api/tags/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getTag_ReturnTag() throws Exception {
        Tag expectedTag = Tag.builder().setId(1L).setName("name").build();
        when(tagService.get(1L)).thenReturn(expectedTag);
        mockMvc.perform(get("/api/tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", org.hamcrest.core.Is.is("name")));
    }
}
