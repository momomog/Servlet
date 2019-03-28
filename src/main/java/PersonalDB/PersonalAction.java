package PersonalDB;

import JsonParser.DataForServlet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class PersonalAction {
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

    public String personalsUpdate(String data) throws NullPointerException {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from personals");
            ResultSet resultSet = preparedStatement.executeQuery();
            sb.append("{\"personals\":[");
            while (resultSet.next()) {
                map.put("id", String.valueOf(resultSet.getInt("id")));
                map.put("name", resultSet.getString("name"));
                map.put("technology", resultSet.getString("technology"));
                map.put("skill", resultSet.getString("skill"));
                map.put("used", resultSet.getString("used"));
                map.put("commentary", resultSet.getString("commentary"));
                sb.append(mapper.writeValueAsString(map)).append(",");
                map.clear();
            }
            sb.setLength(sb.length() - 1);
            sb.append("], \"success\": true,\"message\": \"Данные обновлены!\" }");
            connection.close();
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        if (sb.toString().contains("id")) {
            return sb.toString();
        } else {
            return "{\"success\": true,\"message\": \"Данные обновлены!\"}";
        }
    }

    public String addPersonalToDB(String data) {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from personals");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nameFromDB = resultSet.getString("name");
                String techFromDB = resultSet.getString("technology");
                if (dfs.getName().equals(nameFromDB) && dfs.getTechnology().equals(techFromDB)) {
                    return "{\"success\": false,\"message\": \"Данная технология для пользователя уже зарегестрирована!\"}";
                }
            }
            preparedStatement = connection.prepareStatement("insert into personals (name, technology, skill, used, commentary) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, dfs.getName());
            preparedStatement.setString(2, dfs.getTechnology());
            preparedStatement.setString(3, dfs.getSkill());
            preparedStatement.setString(4, dfs.getUsed());
            preparedStatement.setString(5, dfs.getCommentary());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"success\": true,\"message\": \"Пользователь добавлен!\"}";
    }

    public String deletePersonalFromDB(String data) {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from personals * where id = ?");
            preparedStatement.setInt(1, dfs.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"success\": true,\"message\": \"Пользователь удален!\"}";
    }

    public String updatePersonaldataToDB(String data) {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("update personals set technology = ?, skill = ?, used = ?, commentary = ? where id = ?");
            preparedStatement.setString(1, dfs.getTechnology());
            preparedStatement.setString(2, dfs.getSkill());
            preparedStatement.setString(3, dfs.getUsed());
            preparedStatement.setString(4, dfs.getCommentary());
            preparedStatement.setInt(5, dfs.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"success\": true,\"message\": \"Данные изменены!\"}";
    }
}
