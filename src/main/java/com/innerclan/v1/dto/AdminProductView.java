package com.innerclan.v1.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductView {

    long id;

    String productName;

    double productPrice;

    double actualPrice;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date createdOn;

    @JsonFormat(pattern = "yyyy-mm-dd")
    Date updatedOn;

    String comment;

    int views;

    int sale;

    byte[] defaultImage;


}
