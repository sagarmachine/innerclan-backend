package com.innerclan.v1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryDto {

    long id;

    @NotNull(message = "category name can't be empty")
    String name;

    @NotNull(message = "choose a category gender")
    String gender;


    List<String> information;
}
