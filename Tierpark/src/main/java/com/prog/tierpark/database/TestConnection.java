package com.prog.tierpark.database;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DatabaseManager.getConnection()) {
            System.out.println("✅ MSSQL соединение установлено!");
        } catch (SQLException e) {
            System.out.println("❌ Ошибка подключения:");
            e.printStackTrace();
        }
    }
}

