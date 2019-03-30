package PersonalDB;

import JsonParser.DataForServlet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;
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

            ArrayList<Integer> idList = new ArrayList<>();
            ArrayList<Integer> technologyList = new ArrayList<>();
            ArrayList<Integer> nameList = new ArrayList<>();
            ArrayList<Integer> skillList = new ArrayList<>();
            ArrayList<Integer> usedList = new ArrayList<>();
            ArrayList<String> commentaryList = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement("select * from personal");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idList.add(resultSet.getInt("id"));
                nameList.add(resultSet.getInt("name"));
                technologyList.add(resultSet.getInt("technology"));
                skillList.add(resultSet.getInt("skill"));
                usedList.add(resultSet.getInt("used"));
                commentaryList.add(resultSet.getString("commentary"));
            }
            sb.append("{\"personals\":[");
            for (int i = 0; i < idList.size(); i++) {
                map.put("id", (idList.get(i)).toString());

                preparedStatement = connection.prepareStatement("select name from users where id = ?");
                preparedStatement.setInt(1, nameList.get(i));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    map.put("name", resultSet.getString("name"));
                }

                preparedStatement = connection.prepareStatement("select name from technologies where id = ?");
                preparedStatement.setInt(1, technologyList.get(i));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    map.put("technology", resultSet.getString("name"));
                }

                preparedStatement = connection.prepareStatement("select name from skills where id = ?");
                preparedStatement.setInt(1, skillList.get(i));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    map.put("skill", resultSet.getString("name"));
                }

                preparedStatement = connection.prepareStatement("select name from useds where id = ?");
                preparedStatement.setInt(1, usedList.get(i));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    map.put("used", resultSet.getString("name"));
                }

                map.put("commentary", commentaryList.get(i));
                sb.append(mapper.writeValueAsString(map)).append(",");
                map.clear();
            }
            sb.setLength(sb.length() - 1);
            sb.append("], \"success\": true,\"message\": \"Данные обновлены!\" }");
            System.out.println(sb.toString());
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
            PreparedStatement preparedStatement = connection.prepareStatement("select * from personal");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nameFromDB = resultSet.getString("name");
                String techFromDB = resultSet.getString("technology");
                if (dfs.getName().equals(nameFromDB) && dfs.getTechnology().equals(techFromDB)) {
                    return "{\"success\": false,\"message\": \"Данная технология для пользователя уже зарегестрирована!\"}";
                }
            }

            preparedStatement = connection.prepareStatement("insert into personal (name, technology, skill, used, commentary) VALUES (?,?,?,?,?)");
            preparedStatement.setInt(1, Integer.parseInt(dfs.getName()));
            preparedStatement.setInt(2, Integer.parseInt(dfs.getTechnology()));
            preparedStatement.setInt(3, Integer.parseInt(dfs.getSkill()));
            preparedStatement.setInt(4, Integer.parseInt(dfs.getUsed()));
            preparedStatement.setString(5, dfs.getCommentary());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"success\": true,\"message\": \"Пользователь добавлен!\"}";
    }

//    public String addPersonalToDB(String data) {
//        try {
//            dfs.dataInitilization(data);
//            PreparedStatement preparedStatement = connection.prepareStatement("select * from personals");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                String nameFromDB = resultSet.getString("name");
//                String techFromDB = resultSet.getString("technology");
//                if (dfs.getName().equals(nameFromDB) && dfs.getTechnology().equals(techFromDB)) {
//                    return "{\"success\": false,\"message\": \"Данная технология для пользователя уже зарегестрирована!\"}";
//                }
//            }
//            preparedStatement = connection.prepareStatement("insert into personals (name, technology, skill, used, commentary) VALUES (?,?,?,?,?)");
//            preparedStatement.setString(1, dfs.getName());
//            preparedStatement.setString(2, dfs.getTechnology());
//            preparedStatement.setString(3, dfs.getSkill());
//            preparedStatement.setString(4, dfs.getUsed());
//            preparedStatement.setString(5, dfs.getCommentary());
//            preparedStatement.executeUpdate();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return "{\"success\": true,\"message\": \"Пользователь добавлен!\"}";
//    }

    public String deletePersonalFromDB(String data) {
        try {
            dfs.dataInitilization(data);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from personal * where id = ?");
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
            PreparedStatement preparedStatement = connection.prepareStatement("update personal set technology = ?, skill = ?, used = ?, commentary = ? where id = ?");
            preparedStatement.setInt(1, Integer.parseInt(dfs.getTechnology()));
            preparedStatement.setInt(2, Integer.parseInt(dfs.getSkill()));
            preparedStatement.setInt(3, Integer.parseInt(dfs.getUsed()));
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
