package com.userinfo.userservice.Dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UpdateDto {
    private String passwd;
    private String name;
}