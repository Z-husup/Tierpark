package com.prog.tierpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an administrator of the zoo system.
 */
@Data
@AllArgsConstructor
public class Admin {

    /** Unique identifier for the admin. */
    private int id;

    /** Admin's login username. */
    private String username;

    /** Admin's login password. */
    private String password;
}

