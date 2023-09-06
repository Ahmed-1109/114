package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl uService = new UserServiceImpl();
        uService.createUsersTable();

        uService.saveUser("Nikita", "Petrov", (byte) 20);
        uService.saveUser("Denis", "Ivanov", (byte) 22);
        uService.saveUser("Oleg", "Fedorov", (byte) 20);
        uService.saveUser("Marina", "Naumova", (byte) 36);

        uService.removeUserById(1);
        uService.getAllUsers();
        uService.cleanUsersTable();
        uService.dropUsersTable();
    }
}
