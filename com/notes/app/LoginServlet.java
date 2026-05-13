package com.notes.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM Users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("user_id", rs.getInt("user_id"));
                res.sendRedirect("notes.html");
            } else {
                PreparedStatement insert = con.prepareStatement(
                    "INSERT INTO Users VALUES(user_seq.NEXTVAL, ?, ?)"
                );
                insert.setString(1, username);
                insert.setString(2, password);
                insert.executeUpdate();

                res.sendRedirect("notes.html");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}