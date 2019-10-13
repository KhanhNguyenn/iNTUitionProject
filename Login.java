package iNTuition.ternary;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_name= (String) request.getParameter("user_name");
		String password= (String) request.getParameter("password");
		AuthenticateDAO auth= new AuthenticateDAO();
		try {
			auth.setUp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(auth.check(user_name, password)) {
				HttpSession session= request.getSession();
				session.setAttribute("user_name", user_name);
				session.setAttribute("password", password);
				response.sendRedirect("welcome.jsp");
			}
			else {
				response.sendRedirect("loginByPass.jsp");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
