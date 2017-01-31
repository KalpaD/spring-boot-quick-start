package com.kds.boot.repository;

import com.kds.boot.web.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by KDS on 1/31/2017.
 */
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {


}
