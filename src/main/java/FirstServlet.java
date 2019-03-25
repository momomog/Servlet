import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.PrintWriter;


public class FirstServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String data = request.getParameter("data");
        System.out.println(data);
        System.out.println(data.length());
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(data);

            JSONObject jsonObject = (JSONObject) obj;

            String fname = (String) jsonObject.get("fname");
            String lname = (String) jsonObject.get("lname");
            String email = (String) jsonObject.get("email");


            System.out.println("Name: " + fname);
            System.out.println("Author: " + lname);
            System.out.println("Author: " + email);


        } catch (Exception e) {
            e.printStackTrace();
        }
//        Gson gson = new Gson();
//        DataForServlet dfs = gson.fromJson(data, DataForServlet.class);
//        System.out.println(dfs);
//        System.out.println("fname=>" + dfs.getFname());
//        System.out.println("lname=>" + dfs.getLname());
//        System.out.println("email=>" + dfs.getEmail());

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
      response.setHeader("Access-Control-Max-Age", "86400");
        JsonObject myObj = new JsonObject();

            myObj.addProperty("success", true);
            myObj.addProperty("message", "Cograts registration success!");

        out.println(myObj.toString());
        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        doPost(request, response);

    }
}
