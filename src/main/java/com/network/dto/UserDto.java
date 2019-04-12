package com.network.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.network.model.Photo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonView
public class UserDto {

    private int userId;
    private String userName;
    private String userSurname;
    private Photo avatar;
}
