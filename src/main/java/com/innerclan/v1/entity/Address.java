package com.innerclan.v1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {


    String houseNumber="";

    String landmark="";

    @Column(length = 6)
    String pincode="";

    String location="";


    String city="";


    String state="";


    String customerPhone="";


    String customerName="";



}
