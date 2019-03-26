import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;

public class DataForServlet {

    private String name;
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

    public String getName() {
        return dfs.name;
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
