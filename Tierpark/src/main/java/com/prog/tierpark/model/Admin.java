package com.prog.tierpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Admin {
    private int id;

    private String username;
    private String password;
}
