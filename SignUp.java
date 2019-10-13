package iNTuition.ternary;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_name= request.getParameter("user_name");
		String password= request.getParameter("password");
		String email= request.getParameter("email");
		
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
			if(auth.insert(user_name, password, email)) {
				HttpSession session= request.getSession();
				session.setAttribute("user_name", user_name);
				session.setAttribute("password", password);
				response.sendRedirect("takeAuthImage.jsp");
			}
			else {
				response.sendRedirect("signup.jsp");
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
