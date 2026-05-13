import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/saveNote")
public class SaveNoteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        String note = req.getParameter("note");

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Note Saved</title>");
        out.println("<style>");
        out.println("  *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }");
        out.println("  body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: #f5f4f0; color: #1a1a18; min-height: 100vh; padding: 2rem 1rem; }");
        out.println("  .container { max-width: 680px; margin: 0 auto; }");
        out.println("  .app-header { display: flex; align-items: center; gap: 12px; margin-bottom: 2rem; }");
        out.println("  .app-icon { width: 40px; height: 40px; border-radius: 10px; background: #ddeeff; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }");
        out.println("  .app-header h1 { font-size: 20px; font-weight: 500; color: #1a1a18; }");
        out.println("  .app-header p { font-size: 13px; color: #888780; margin-top: 2px; }");
        out.println("  .card { background: #ffffff; border: 0.5px solid rgba(0,0,0,0.12); border-radius: 14px; padding: 2rem 1.5rem; text-align: center; }");
        out.println("  .success-icon { width: 52px; height: 52px; border-radius: 50%; background: #eaf3de; display: flex; align-items: center; justify-content: center; margin: 0 auto 1rem; }");
        out.println("  .success-title { font-size: 17px; font-weight: 500; color: #1a1a18; margin-bottom: 6px; }");
        out.println("  .success-sub { font-size: 13px; color: #888780; margin-bottom: 1.5rem; }");
        out.println("  .note-preview { background: #f5f4f0; border-radius: 8px; padding: 12px 14px; text-align: left; font-size: 14px; color: #1a1a18; line-height: 1.6; margin-bottom: 1.5rem; border-left: 3px solid #c0dd97; }");
        out.println("  .btn-row { display: flex; gap: 10px; justify-content: center; flex-wrap: wrap; }");
        out.println("  .btn-primary { display: inline-flex; align-items: center; gap: 7px; padding: 9px 20px; font-size: 14px; font-weight: 500; color: #185fa5; background: #ddeeff; border: 0.5px solid rgba(24,95,165,0.3); border-radius: 8px; cursor: pointer; text-decoration: none; }");
        out.println("  .btn-primary:hover { background: #c5ddf7; }");
        out.println("  .btn-secondary { display: inline-flex; align-items: center; gap: 7px; padding: 9px 20px; font-size: 14px; font-weight: 500; color: #1a1a18; background: transparent; border: 0.5px solid rgba(0,0,0,0.25); border-radius: 8px; cursor: pointer; text-decoration: none; }");
        out.println("  .btn-secondary:hover { background: #ebe9e3; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='container'>");

        // Header
        out.println("<div class='app-header'>");
        out.println("  <div class='app-icon'>");
        out.println("    <svg width='20' height='20' viewBox='0 0 24 24' fill='none' stroke='#185fa5' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7'/><path d='M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z'/></svg>");
        out.println("  </div>");
        out.println("  <div><h1>My Notes</h1><p>Write. Save. Revisit.</p></div>");
        out.println("</div>");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO notes(content) VALUES(?)");
            ps.setString(1, note);
            ps.executeUpdate();

            // Success state
            out.println("<div class='card'>");
            out.println("  <div class='success-icon'>");
            out.println("    <svg width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='#3b6d11' stroke-width='2.5' stroke-linecap='round' stroke-linejoin='round'><polyline points='20 6 9 17 4 12'/></svg>");
            out.println("  </div>");
            out.println("  <p class='success-title'>Note saved!</p>");
            out.println("  <p class='success-sub'>Your note has been stored successfully.</p>");
            if (note != null && !note.trim().isEmpty()) {
                out.println("  <div class='note-preview'>" + note + "</div>");
            }
            out.println("  <div class='btn-row'>");
            out.println("    <a href='index.html' class='btn-primary'>");
            out.println("      <svg width='14' height='14' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2.5' stroke-linecap='round' stroke-linejoin='round'><path d='M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7'/><path d='M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z'/></svg>");
            out.println("      Write another");
            out.println("    </a>");
            out.println("    <a href='getNotes' class='btn-secondary'>");
            out.println("      <svg width='14' height='14' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2.5' stroke-linecap='round' stroke-linejoin='round'><polyline points='1 4 1 10 7 10'/><path d='M3.51 15a9 9 0 1 0 .49-3.5'/></svg>");
            out.println("      View all notes");
            out.println("    </a>");
            out.println("  </div>");
            out.println("</div>");

        } catch (Exception e) {
            e.printStackTrace();

            // Error state
            out.println("<div class='card'>");
            out.println("  <div class='success-icon' style='background:#fcebeb;'>");
            out.println("    <svg width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='#a32d2d' stroke-width='2.5' stroke-linecap='round' stroke-linejoin='round'><circle cx='12' cy='12' r='10'/><line x1='12' y1='8' x2='12' y2='12'/><line x1='12' y1='16' x2='12.01' y2='16'/></svg>");
            out.println("  </div>");
            out.println("  <p class='success-title' style='color:#a32d2d;'>Something went wrong</p>");
            out.println("  <p class='success-sub'>Your note could not be saved. Please try again.</p>");
            out.println("  <div class='btn-row'>");
            out.println("    <a href='javascript:history.back()' class='btn-secondary'>Go back</a>");
            out.println("  </div>");
            out.println("</div>");
        }

        out.println("</div>"); // container
        out.println("</body></html>");
    }
}