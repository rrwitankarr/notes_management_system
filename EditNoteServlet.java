import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/editNote")
public class EditNoteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String content = "";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT content FROM notes WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                content = rs.getString("content");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Edit Note</title>");
        out.println("<style>");
        out.println("  *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }");
        out.println("  body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: #f5f4f0; color: #1a1a18; min-height: 100vh; padding: 2rem 1rem; }");
        out.println("  .container { max-width: 680px; margin: 0 auto; }");
        out.println("  .app-header { display: flex; align-items: center; gap: 12px; margin-bottom: 2rem; }");
        out.println("  .app-icon { width: 40px; height: 40px; border-radius: 10px; background: #ddeeff; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }");
        out.println("  .app-header h1 { font-size: 20px; font-weight: 500; color: #1a1a18; }");
        out.println("  .app-header p { font-size: 13px; color: #888780; margin-top: 2px; }");
        out.println("  .back-btn { display: inline-flex; align-items: center; gap: 6px; padding: 8px 16px; font-size: 13px; font-weight: 500; color: #1a1a18; background: transparent; border: 0.5px solid rgba(0,0,0,0.25); border-radius: 8px; cursor: pointer; text-decoration: none; margin-bottom: 1.5rem; }");
        out.println("  .back-btn:hover { background: #ebe9e3; }");
        out.println("  .card { background: #ffffff; border: 0.5px solid rgba(0,0,0,0.12); border-radius: 14px; padding: 1.25rem 1.5rem; }");
        out.println("  .card-top { display: flex; align-items: center; gap: 10px; margin-bottom: 14px; }");
        out.println("  .card-label { font-size: 13px; font-weight: 500; color: #888780; }");
        out.println("  .note-id-badge { font-size: 11px; font-weight: 500; color: #185fa5; background: #ddeeff; padding: 2px 8px; border-radius: 99px; }");
        out.println("  textarea { width: 100%; resize: vertical; font-family: inherit; font-size: 15px; line-height: 1.6; color: #1a1a18; background: #f5f4f0; border: 0.5px solid rgba(0,0,0,0.12); border-radius: 8px; padding: 12px 14px; outline: none; transition: border-color 0.15s; display: block; margin-bottom: 12px; }");
        out.println("  textarea::placeholder { color: #b4b2a9; }");
        out.println("  textarea:focus { border-color: rgba(24,95,165,0.45); }");
        out.println("  .btn-row { display: flex; justify-content: flex-end; gap: 10px; }");
        out.println("  .btn-save { display: inline-flex; align-items: center; gap: 7px; padding: 8px 18px; font-size: 14px; font-weight: 500; color: #185fa5; background: #ddeeff; border: 0.5px solid rgba(24,95,165,0.3); border-radius: 8px; cursor: pointer; transition: background 0.15s; }");
        out.println("  .btn-save:hover:not(:disabled) { background: #c5ddf7; }");
        out.println("  .btn-save:disabled { opacity: 0.45; cursor: not-allowed; }");
        out.println("  .change-hint { font-size: 12px; color: #b4b2a9; align-self: center; margin-right: auto; }");
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

        // Back button
        out.println("<a href='javascript:history.back()' class='back-btn'>");
        out.println("  <svg width='13' height='13' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2.5' stroke-linecap='round' stroke-linejoin='round'><polyline points='15 18 9 12 15 6'/></svg>");
        out.println("  Back");
        out.println("</a>");

        // Edit card
        out.println("<div class='card'>");
        out.println("  <div class='card-top'>");
        out.println("    <p class='card-label'>Editing note</p>");
        out.println("    <span class='note-id-badge'>ID " + id + "</span>");
        out.println("  </div>");

        out.println("  <form action='updateNote' method='post'>");
        out.println("    <input type='hidden' name='id' value='" + id + "'/>");
        out.println("    <textarea id='noteArea' name='note' rows='6'>" + content + "</textarea>");
        out.println("    <div class='btn-row'>");
        out.println("      <span class='change-hint' id='hint'>No changes yet</span>");
        out.println("      <button id='saveBtn' class='btn-save' type='submit' disabled>");
        out.println("        <svg width='14' height='14' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2.5' stroke-linecap='round' stroke-linejoin='round'><polyline points='20 6 9 17 4 12'/></svg>");
        out.println("        Save changes");
        out.println("      </button>");
        out.println("    </div>");
        out.println("  </form>");
        out.println("</div>"); // card

        out.println("</div>"); // container

        // JS — functionality untouched, hint text added
        out.println("<script>");
        out.println("  let original = document.getElementById('noteArea').value;");
        out.println("  let btn = document.getElementById('saveBtn');");
        out.println("  let hint = document.getElementById('hint');");
        out.println("  document.getElementById('noteArea').addEventListener('input', function(){");
        out.println("    let changed = this.value !== original;");
        out.println("    btn.disabled = !changed;");
        out.println("    hint.textContent = changed ? 'Unsaved changes' : 'No changes yet';");
        out.println("    hint.style.color = changed ? '#ba7517' : '#b4b2a9';");
        out.println("  });");
        out.println("</script>");

        out.println("</body></html>");
    }
}