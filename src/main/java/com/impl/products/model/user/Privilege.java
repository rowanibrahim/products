package com.impl.products.model.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
