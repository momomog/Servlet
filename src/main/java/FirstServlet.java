
import JsonParser.OperationDefiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



public class FirstServlet extends javax.servlet.http.HttpServlet {

//    public void doOptions(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//        resp.setHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
//        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
//        resp.setHeader("Access-Control-Max-Age", "86400");
//        resp.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
//        System.out.println("reg");
//    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doOptions(request, response);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String data = request.getParameter("data");

        PrintWriter out = response.getWriter();
        out.println(new OperationDefiner().takeCurrentOperation(data));
        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
