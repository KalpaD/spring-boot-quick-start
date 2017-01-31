package com.kds.boot.service;

import com.kds.boot.repository.GreetingRepository;
import com.kds.boot.web.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by KDS on 1/31/2017.
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS,
        readOnly = true)
public class GreetingServiceBean implements GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    @Override
    public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        return greeting;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            readOnly = false)
    public Greeting create(Greeting greeting) {
        if (greeting.getId() != null) {
            return null;
        }
        Greeting savedGreeting = greetingRepository.save(greeting);

        //simulating rollback
        if(savedGreeting.getId() == 4L) {
            throw new RuntimeException("Roll me back");
        }
        return savedGreeting;
    }

    @Override
    public Greeting update(Greeting greeting) {
        Greeting greetingPersisted = findOne(greeting.getId());
        if (greetingPersisted == null) {

        }
        Greeting updatedGreeting = greetingRepository.save(greeting);
        return updatedGreeting;
    }

    @Override
    public void delete(Long id) {
        greetingRepository.delete(id);
    }
}
