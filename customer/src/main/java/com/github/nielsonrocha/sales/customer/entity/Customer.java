package com.github.nielsonrocha.sales.customer.entity;

import com.github.nielsonrocha.sales.customer.infra.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Customer extends BaseEntity {

    private String name;

    private String email;

    private String cellPhone;

    private LocalDate birth;

    private String addressLine;

    private String addressLine2;

    private String zipCode;

    private String district;

    private String state;

    private String country;

}
