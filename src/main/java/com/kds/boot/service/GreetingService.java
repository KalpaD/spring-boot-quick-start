package com.kds.boot.service;

import com.kds.boot.web.model.Greeting;

import java.util.Collection;

/**
 * Created by KDS on 1/31/2017.
 */
public interface GreetingService {

    Collection<Greeting> findAll();

    Greeting findOne(Long id);

    Greeting create(Greeting greeting);

    Greeting update(Greeting greeting);

    void delete(Long id);

}
