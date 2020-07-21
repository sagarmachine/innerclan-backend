package com.innerclan.v1.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.innerclan.v1.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddClientDto {


    @NotNull(message = "First name can't be empty")
    @Size(max = 20,message = "firstName can't be larger than 20 alphabets")
    String firstName;

    @NotNull(message = "Last name can't be empty")
    @Size(max = 20,message = "firstName can't be larger than 20 alphabets")
    String lastName;


    @NotNull(message = "email can't be empty")
    @Email(message = "invalid email format")
    String email;


    @NotNull(message = "password can't be empty")
    @Size(min = 6, max = 30,message = "Password length must be between 6 and 30")
    String password;

    @Digits(message = "Phone number can contain digits only and length should be 10", integer = 10, fraction =0 )
    String phone;




}
