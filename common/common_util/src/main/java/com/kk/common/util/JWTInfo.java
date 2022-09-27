package com.kk.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTInfo {
    private String id;
    private String nickname;
    private String avatar;
}
