package ChatRoom.Server;

import java.sql.*;


public class SQL_Connection {
    private Connection connection;

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

//    public static void main(String[] args) {
//        SQL_Test sql_test = new SQL_Test();
//
//        sql_test.insertExample("Admin", "Hello world!", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
//        sql_test.selectExample();
//    }

}
