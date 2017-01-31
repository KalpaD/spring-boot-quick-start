package com.kds.boot.web.api;

import com.kds.boot.service.EmailService;
import com.kds.boot.service.GreetingService;
import com.kds.boot.web.AbstractControllerTest;
import com.kds.boot.web.model.Greeting;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by KDS on 1/31/2017.
 */

/**
 * This demonstrate how to use mockito to mock the objects for unit testing
 * when testing REST apis.
 */
@Transactional
public class GreetingControllerMockTest extends AbstractControllerTest {

    @Mock
    private EmailService emailService;

    @Mock
    private GreetingService greetingService;

    @InjectMocks
    private GreetingController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        setUp(controller);
    }

    @Test
    public void testGetGreetings() throws Exception {

        String uri = "/api/greetings";

        Collection<Greeting> list = getGreetingListForStub();

        Mockito.when(greetingService.findAll()).thenReturn(list);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)                            // GET
                        .accept(MediaType.APPLICATION_JSON)) // Accept header
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(200, status);
    }

    private Collection<Greeting> getGreetingListForStub() {
        Greeting greeting = new Greeting();
        greeting.setId(1L);
        greeting.setText("Hello World");

        return Arrays.asList(greeting);
    }

}
