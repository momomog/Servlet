import java.sql.*;

public class DBAction {
    private String login = "postgres";
    private String password = "1234";
    private String url = "jdbc:postgresql://localhost:5432/data";

    public void addToDB(String data)  {
        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            Class.forName("org.postgresql.Driver");
            DataForServlet dfs = new DataForServlet();
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users (name, technology, skill, used, commentary) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, dfs.getName());
            preparedStatement.setString(2, dfs.getTechnology());
            preparedStatement.setString(3, dfs.getSkill());
            preparedStatement.setString(4, dfs.getUsed());
            preparedStatement.setString(5, dfs.getCommentary());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
