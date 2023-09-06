package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "age INT)");

            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица НЕ создана!");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.setAutoCommit(false);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица НЕ удалена!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            connection.setAutoCommit(false);
            System.out.println("User " + name + " " + lastName + " добавлен");
        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("User " + name + " " + lastName + " не может быть добален в таблицу!");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE ID = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.setAutoCommit(false);
            System.out.println("User с ID = " + id + " удален");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User с ID = " + id + " не может быть удален!");
        }
    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        Connection connection = Util.getConnection();
        try {
            String sql = "SELECT * FROM users";
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sql);
            connection.setAutoCommit(true);
            while (res.next()) {
                User user = new User(
                        res.getString("name"),
                        res.getString("lastName"),
                        (byte) res.getInt("age")
                );
                user.setId(res.getLong("id")); // устанавливаю id-шники, полученные из хранилища
                listUser.add(user); // добавляю юзера в лист
                System.out.println("Данные из таблицы получены");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка чтения из таблицы!");
        }
        return listUser; // возвращаю лист с юзерами
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM users"; // создаю запроc
            statement.executeUpdate(sql); // отправляю запрос на удаление
            connection.setAutoCommit(false);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица не может быть очищена!");
        }
    }
}
