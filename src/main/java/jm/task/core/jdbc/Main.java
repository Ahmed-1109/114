package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl uService = new UserServiceImpl();
        // реализуйте алгоритм здесь
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            uService.createUsersTable(connection);

            uService.saveUser("Nikita", "Petrov", (byte) 20);
            uService.saveUser("Denis", "Ivanov", (byte) 22);
            uService.saveUser("Oleg", "Fedorov", (byte) 20);
            uService.saveUser("Marina", "Naumova", (byte) 36);

            uService.removeUserById(1);
            uService.getAllUsers();
            uService.cleanUsersTable();
            uService.dropUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
