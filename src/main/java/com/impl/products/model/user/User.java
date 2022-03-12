package com.impl.products.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(unique = true, name = "user_name", nullable = false)
    private String userName;

    @Column(name="full_name", nullable = false)
    private String fullName;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();

}