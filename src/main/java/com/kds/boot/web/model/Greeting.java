package com.kds.boot.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by KDS on 1/26/2017.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Greeting {

    @Id
    @GeneratedValue
    private Long id;
    private String text;

}
