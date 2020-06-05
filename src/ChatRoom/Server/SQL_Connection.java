package ChatRoom.Server;

import java.sql.*;

/**
 * Class SQL_Connection connects to DB and sends
 * messages to store it.
 *
 * @author Daniel Barbakadze.
 * @author Valera Liparteliani.
 */
public class SQL_Connection {
    private Connection connection;

    /**
     * Variables user and password is used to
     * connect to DataBase.
     *
     * @param user is DataBase's username.
     * @param password is DataBase's password.
     */
    public SQL_Connection(String user, String password) {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/Javaproject",
                    user,
                    password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method insertExample creates query using
     * Clients username, message and data to store
     * the information into DataBase Table.
     *
     * @param username is Client's username.
     * @param message is Client's message.
     * @param date is Client's date.
     */
    public void insertExample(String username, String message, String date) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO chat(username, message, date) VALUES(?, ?, ?)")) {

            statement.setString(1, username);
            statement.setString(2, message);
            statement.setString(3, date);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method selectExample would be used to
     * get every information from DataBase.
     * But this method currently doesn't exist.
     *
     */
    public void selectExample() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM chat")) {

            System.out.println(" Username | Message | DateTime");

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String message = resultSet.getString("message");
                String date = resultSet.getString("date");

                System.out.println(String.format("%10s | %10s | %20s", date, username, message));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
