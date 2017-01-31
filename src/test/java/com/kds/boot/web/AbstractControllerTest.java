package com.kds.boot.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kds.boot.web.api.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

/**
 * Created by KDS on 1/31/2017.
 */
public class AbstractControllerTest extends AbstractTest {

    protected MockMvc mockMvc; // capable of simulate http interactions

    @Autowired
    protected WebApplicationContext webApplicationContext;


    protected void setUp() {
        // submitting the web app context to mock mvc builder.
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Register only the given controller to web app context
     * @param controller
     */
    protected void setUp(BaseController controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }


}
