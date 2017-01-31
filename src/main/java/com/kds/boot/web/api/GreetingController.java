package com.kds.boot.web.api;

import com.kds.boot.service.EmailService;
import com.kds.boot.service.GreetingService;
import com.kds.boot.web.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by KDS on 1/26/2017.
 */
@RestController
public class GreetingController extends BaseController {

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/api/greetings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings() {

        logger.info(" > GET /api/greetings");
        Collection<Greeting> greetings = greetingService.findAll();
        logger.info(" < GET /api/greetings");
        return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id) {

        logger.info(" > GET /api/greetings/{id}");
        Greeting greeting = greetingService.findOne(id);
        if (greeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }
        logger.info(" < GET /api/greetings/{id}");
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/greetings", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {

        logger.info(" > POST /api/greetings");
        Greeting savedGreeting = greetingService.create(greeting);
        logger.info(" < POST /api/greetings");
        return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/greetings",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {
        logger.info(" > PUT /api/greetings");
        Greeting updatedGreeting = greetingService.update(greeting);
        if (updatedGreeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info(" < PUT /api/greetings");
        return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/greetings/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") Long id,
                                                   @RequestBody Greeting greeting) {
        logger.info(" > DELETE /api/greetings/{id}");
        greetingService.delete(id);
        logger.info(" < DELETE /api/greetings/{id}");
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/api/greetings/{id}/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> sendGreeting(@PathVariable("id") Long id,
                                                 @RequestParam(value = "wait",
                                                         defaultValue = "false") boolean waitForAsyncResult) {
        logger.info("> sendGreeting");
        Greeting greeting = null;
        try {
            greeting = greetingService.findOne(id);
            if(greeting == null) {
                logger.info("< sendingGreeting");
                return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
            }

            if(waitForAsyncResult) {
                CompletableFuture<Boolean> asyncResponse =
                        emailService.sendAsyncWithResults(greeting);
                boolean emailSent = asyncResponse.get();
                logger.info("- greeting email sent? {}", emailSent);
            } else {
                emailService.sendAsync(greeting);
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("A problem occured seding the Greeting.", e);
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("< sendGreeting");
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);

    }
}
