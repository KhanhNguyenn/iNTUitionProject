package iNTuition.ternary;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckValidSignUp
 */
@WebServlet("/CheckValidSignUp")
public class CheckValidSignUp extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		String password=(String)session.getAttribute("password");
		if(password.trim().length()==0) {
			response.sendRedirect("signup2.jsp");
		}
		else {
			response.sendRedirect("welcome.jsp");
		}
	}


}
