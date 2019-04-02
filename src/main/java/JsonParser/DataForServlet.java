package JsonParser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;

public class DataForServlet {

    private String dataBase;
    private String operation;
    private String id;
    private String name;
    private String email;
    private String phone;
    private String technology;
    private String skill;
    private String used;
    private String commentary;
    private DataForServlet dfs;

    public void dataInitilization(String data) {
        StringReader reader = new StringReader(data);
        ObjectMapper mapper = new ObjectMapper();
        try {
            dfs = mapper.readValue(reader, DataForServlet.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDataBase() {
        return dfs.dataBase;
    }

    public String getOperation() {
        return dfs.operation;
    }

    public int getId() {
        return Integer.parseInt(dfs.id);
    }

    public String getName() {
        return dfs.name;
    }

    public String getEmail() {
        return dfs.email;
    }

    public String getPhone() {
        return dfs.phone;
    }

    public String getTechnology() {
        return dfs.technology;
    }

    public String getSkill() {
        return dfs.skill;
    }

    public String getUsed() {
        return dfs.used;
    }

    public String getCommentary() {
        return dfs.commentary;
    }

}
