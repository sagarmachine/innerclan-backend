package com.innerclan.v1.dto;

import com.innerclan.v1.entity.Category;
import com.innerclan.v1.entity.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductFullView {

    long id;

    String productName;

    double productPrice;

    double actualPrice;

    byte[] image;

    long sale;

    long view;

    String comment;

    Date createdOn;

    Date updatedOn;

    Category category;

    List<Color> colors;






}
