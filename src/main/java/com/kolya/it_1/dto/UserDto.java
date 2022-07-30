package com.kolya.it_1.dto;

import com.kolya.it_1.annotations.EmailNotExist;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    @EmailNotExist
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    private String status;

    private Date registrationDate;

    private Date loginDate;

    // https://github.com/mapstruct/mapstruct/pull/2222 this is bug of version 1.4.2 ,
    // btw I tried to fix this problem , but old version didn't work
}
