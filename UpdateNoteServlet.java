import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/updateNote")
public class UpdateNoteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String note = req.getParameter("note");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE notes SET content=? WHERE id=?"
            );
            ps.setString(1, note);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        res.sendRedirect("getNotes");
    }
}