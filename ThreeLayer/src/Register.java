
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String uname = request.getParameter("u");
		String pass = request.getParameter("p");
		String email = request.getParameter("e");
		RequestDispatcher rd = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Step 2 : create the connection
			Connection con = null;
			PreparedStatement ps = null;
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sanskar", "sans123");
			ps = con.prepareStatement("Insert into gmail values(?,?,?)");
			ps.setString(1, uname);
			ps.setString(2, pass);
			ps.setString(3, email);
			// Step 4 : execute query
			int a = ps.executeUpdate();
			if (a > 0) {
				rd = request.getRequestDispatcher("login.html");
				rd.forward(request, response);
			} else {
				out.print("Please Register");
				rd = request.getRequestDispatcher("form.html");
				rd.include(request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
