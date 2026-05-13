import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/getNotes")
public class GetNotesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>My Notes</title>");
        out.println("<style>");
        out.println("  *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }");
        out.println(
                "  body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: #f5f4f0; color: #1a1a18; min-height: 100vh; padding: 2rem 1rem; }");
        out.println("  .container { max-width: 680px; margin: 0 auto; }");
        out.println("  .app-header { display: flex; align-items: center; gap: 12px; margin-bottom: 2rem; }");
        out.println(
                "  .app-icon { width: 40px; height: 40px; border-radius: 10px; background: #ddeeff; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }");
        out.println("  .app-header h1 { font-size: 20px; font-weight: 500; color: #1a1a18; }");
        out.println("  .app-header p { font-size: 13px; color: #888780; margin-top: 2px; }");
        out.println(
                "  .card { background: #ffffff; border: 0.5px solid rgba(0,0,0,0.12); border-radius: 14px; padding: 1.25rem 1.5rem; margin-bottom: 1.25rem; }");
        out.println("  .card-label { font-size: 13px; font-weight: 500; color: #888780; margin-bottom: 14px; }");
        out.println(
                "  .note-item { background: #f5f4f0; border-radius: 8px; padding: 12px 14px; margin-bottom: 10px; }");
        out.println("  .note-item:last-child { margin-bottom: 0; }");
        out.println(
                "  .note-meta { display: flex; align-items: center; justify-content: space-between; margin-bottom: 6px; }");
        out.println(
                "  .note-id { font-size: 11px; font-weight: 500; color: #185fa5; background: #ddeeff; padding: 2px 8px; border-radius: 99px; }");
        out.println("  .note-date { font-size: 11px; color: #b4b2a9; }");
        out.println("  .note-content { font-size: 14px; color: #1a1a18; line-height: 1.6; }");
        out.println(
                "  .back-btn { display: inline-flex; align-items: center; gap: 6px; padding: 8px 16px; font-size: 13px; font-weight: 500; color: #1a1a18; background: transparent; border: 0.5px solid rgba(0,0,0,0.25); border-radius: 8px; cursor: pointer; text-decoration: none; margin-bottom: 1.5rem; }");
        out.println("  .back-btn:hover { background: #ebe9e3; }");
        out.println("  .empty-state { text-align: center; padding: 2rem 0; color: #b4b2a9; font-size: 14px; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='container'>");

        // Header
        out.println("<div class='app-header'>");
        out.println("  <div class='app-icon'>");
        out.println(
                "    <svg width='20' height='20' viewBox='0 0 24 24' fill='none' stroke='#185fa5' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7'/><path d='M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z'/></svg>");
        out.println("  </div>");
        out.println("  <div><h1>My Notes</h1><p>Write. Save. Revisit.</p></div>");
        out.println("</div>");

        // Back button
        out.println("<a href='javascript:history.back()' class='back-btn'>");
        out.println(
                "  <svg width='13' height='13' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2.5' stroke-linecap='round' stroke-linejoin='round'><polyline points='15 18 9 12 15 6'/></svg>");
        out.println("  Back");
        out.println("</a>");

        // Notes card
        out.println("<div class='card'>");
        out.println("  <p class='card-label'>All saved notes</p>");

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM notes ORDER BY created_at DESC");

            boolean hasNotes = false;

            while (rs.next()) {
                hasNotes = true;
                out.println("  <div class='note-item'>");

                out.println("    <div class='note-meta'>");
                out.println("      <span class='note-id'>ID " + rs.getInt("id") + "</span>");
                out.println("      <span class='note-date'>" + rs.getTimestamp("created_at") + "</span>");
                out.println("    </div>");

                out.println("    <p class='note-content'>" + rs.getString("content") + "</p>");

                // BUTTON CONTAINER
                out.println("    <div style='display:flex; gap:8px; margin-top:8px;'>");

                // EDIT BUTTON
                out.println("    <form action='editNote' method='get'>");
                out.println("        <input type='hidden' name='id' value='" + rs.getInt("id") + "'/>");
                out.println(
                        "        <button type='submit' style='padding:6px 12px; font-size:12px; border:none; border-radius:6px; background:#ddeeff; color:#185fa5; cursor:pointer;'>Edit</button>");
                out.println("    </form>");

                // DELETE BUTTON
                out.println("    <form action='deleteNote' method='post'>");
                out.println("        <input type='hidden' name='id' value='" + rs.getInt("id") + "'/>");
                out.println(
                        "        <button type='submit' style='padding:6px 12px; font-size:12px; border:none; border-radius:6px; background:#fcebeb; color:#a32d2d; cursor:pointer;'>Delete</button>");
                out.println("    </form>");

                out.println("    </div>"); // close button container
                out.println("  </div>"); // close note-item

            }

            if (!hasNotes) {
                out.println("  <div class='empty-state'>No notes yet. Go write your first one!</div>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("  <div class='empty-state'>Could not load notes. Please try again.</div>");
        }

        out.println("</div>"); // card
        out.println("</div>"); // container
        out.println("</body></html>");
    }
}