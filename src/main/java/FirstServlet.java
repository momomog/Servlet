
import JsonParser.DataForServlet;
import UsersDB.UsersAction;

import java.io.IOException;
import java.io.PrintWriter;

public class FirstServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String data = request.getParameter("data");
        DataForServlet dataForServlet = new DataForServlet();
        dataForServlet.dataInitilization(data);
        String responce = null;
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        switch (dataForServlet.getDataBase()) {
            case "users": {
                if (dataForServlet.getOperation().equals("usersUpdate")) {
                    responce = new UsersAction().usersUpdate(data);
                } else if (dataForServlet.getOperation().equals("addNewUser")) {
                    responce = new UsersAction().addUserToDB(data);
                } else {
                    responce = new UsersAction().deleteUserFromDB(data);
                }
                break;
            }
            case "technology": {
            }
        }


        out.println(responce);
        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


    }
}
