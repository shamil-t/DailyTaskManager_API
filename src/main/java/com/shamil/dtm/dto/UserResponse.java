package com.shamil.dtm.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private int id;
    private String name;
    private String email;
}
