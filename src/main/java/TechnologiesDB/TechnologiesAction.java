package TechnologiesDB;

import JsonParser.DataForServlet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class TechnologiesAction {
    private String login = "postgres";
    private String password = "1234";
    private String url = "jdbc:postgresql://localhost:5432/data";
    private Connection connection = null;
    private StringBuilder sb = null;
    private DataForServlet dfs;
    private ObjectMapper mapper = null;
    private Map<String, String> map;

    {
        try {
            connection = DriverManager.getConnection(url, login, password);
            Class.forName("org.postgresql.Driver");
            dfs = new DataForServlet();
            mapper = new ObjectMapper();
            map = new HashMap<>();
            sb = new StringBuilder();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String technologiesUpdate(String data) throws NullPointerException {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from technologies ORDER BY id");
            ResultSet resultSet = preparedStatement.executeQuery();
            sb.append("{\"technologies\":[");
            while (resultSet.next()) {
                map.put("id", String.valueOf(resultSet.getInt("id")));
                map.put("name", resultSet.getString("name"));
                sb.append(mapper.writeValueAsString(map)).append(",");
                map.clear();
            }
            sb.setLength(sb.length() - 1);
            sb.append("], \"success\": true,\"message\": \"Данные обновлены!\" }");
            connection.close();
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        if (sb.toString().contains("name")) {
            return sb.toString();
        } else {
            return "{\"success\": true,\"message\": \"Данные обновлены!\"}";
        }
    }

    public String addTechnologyToDB(String data) {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("select name from technologies");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String technologyFromDB = resultSet.getString("name");
                if (dfs.getName().equals(technologyFromDB)) {
                    return "{\"success\": false,\"message\": \"Данная технология уже зарегестрирована!\"}";
                }
            }
            preparedStatement = connection.prepareStatement("insert into technologies (name) VALUES (?)");
            preparedStatement.setString(1, dfs.getName());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"success\": true,\"message\": \"Технология добавлена!\"}";
    }

    public String deleteTechnologyFromDB(String data) {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from technologies * where id = ?");
            preparedStatement.setInt(1, dfs.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            return "{\"success\": false,\"message\": \"Вы не можете удалить данную технологию, так как она используется в таблице знаний сотрудников!\"}";
        }
        return "{\"success\": true,\"message\": \"Технология удалена!\"}";
    }

    public String updateTechnologydataToDB(String data) {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("update technologies set name = ? where id = ?");
            preparedStatement.setString(1, dfs.getName());
            preparedStatement.setInt(2, dfs.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"success\": true,\"message\": \"Данные изменены!\"}";
    }
}
