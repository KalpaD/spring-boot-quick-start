package com.kds.boot.web.api;

import com.kds.boot.service.GreetingService;
import com.kds.boot.web.AbstractControllerTest;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by KDS on 1/31/2017.
 */
@Transactional
public class GreetingControllerTest extends AbstractControllerTest {

    @Autowired
    private GreetingService greetingService;

    @Before
    public void setUp() {
        super.setUp();
        greetingService.evictCache(); // clean the cache
    }

    @Test
    public void testGreetings() throws Exception {
        String uri = "/api/greetings";

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)           // GET
                        .accept(MediaType.APPLICATION_JSON)) // Accept header
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(200, status);
    }
}
