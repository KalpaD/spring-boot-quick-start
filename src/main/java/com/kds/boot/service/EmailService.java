package com.kds.boot.service;

import com.kds.boot.web.model.Greeting;

import java.util.concurrent.CompletableFuture;

/**
 * Created by KDS on 1/31/2017.
 */
public interface EmailService {

    Boolean send(Greeting greeting);

    void sendAsync(Greeting greeting);

    CompletableFuture<Boolean> sendAsyncWithResults(Greeting greeting);
}
