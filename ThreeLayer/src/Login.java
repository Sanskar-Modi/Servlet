

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String pass = request.getParameter("p");
		String email = request.getParameter("e");
		RequestDispatcher rd = null;
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}

		//Step 2 : create the connection
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sanskar","sans123");
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		//Step 3 : Creating statement
		try {
			ps = con.prepareStatement("Select *  from gmail where email =? and pass = ?");
			ps.setString(1,email);
			ps.setString(2,pass);
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		//Step 4 : execute query
		ResultSet rs = null;
		try {
				rs = ps.executeQuery();
			if(rs.next())
			{
				rd = request.getRequestDispatcher("home.html");
				rd.forward(request, response);
			}
			else
			{
					out.print("Please Register");
					rd = request.getRequestDispatcher("form.html");
					rd.include(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		// Step 5: closing connection 
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	}

