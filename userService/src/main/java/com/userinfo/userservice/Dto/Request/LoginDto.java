package com.userinfo.userservice.Dto.Request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String paswwd;
}
