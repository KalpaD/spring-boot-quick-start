package com.kds.boot.service;

import com.kds.boot.web.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by KDS on 1/31/2017.
 */
@Service
public class EmailServiceBean implements EmailService {

    private Logger logger = LoggerFactory.getLogger(EmailServiceBean.class);


    @Override
    public Boolean send(Greeting greeting) {
        logger.info("> send");

        Boolean success = Boolean.FALSE;

        long pause = 5000;

        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Processing time was {} seconds.", pause / 1000);

        success = Boolean.TRUE;

        logger.info("< send");
        return success;
    }

    @Async
    @Override
    public void sendAsync(Greeting greeting) {
        logger.info("> sendAsync");
        try {
            send(greeting);
        } catch (Exception e) {
            logger.warn("Exception caught sending asynchronous mail", e);
        }
        logger.info("< sendAsync");
    }

    @Async
    @Override
    public CompletableFuture<Boolean> sendAsyncWithResults(Greeting greeting) {
        logger.info("> sendAsyncWithResults");

        CompletableFuture<Boolean> response = new CompletableFuture<>();

        try {
            Boolean success = send(greeting);
            response.complete(success);
        } catch (Exception e) {
            logger.warn("Exception caught sedning asynchronous mail", e);
            response.completeExceptionally(e);
        }

        logger.info("< sendAsyncWithResults");
        return response;
    }
}
