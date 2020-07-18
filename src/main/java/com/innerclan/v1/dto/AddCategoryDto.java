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

    @NotNull
    String name;

    @NotNull
    String gender;


    List<String> information;
}
