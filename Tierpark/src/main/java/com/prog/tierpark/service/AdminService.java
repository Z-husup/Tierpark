package com.prog.tierpark.service;

import com.prog.tierpark.model.Admin;
import com.prog.tierpark.repository.AdminRepository;

public class AdminService {

    private final AdminRepository adminRepository = new AdminRepository();

    /**
     * Logs in an admin by checking credentials.
     *
     * @param username The username.
     * @param password The password (plain text).
     * @return Admin object if login successful, null otherwise.
     */
    public Admin login(String username, String password) {
        Admin admin = adminRepository.getAdminByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }

        return null;
    }
}

