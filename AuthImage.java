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
 * Servlet implementation class AuthImage
 */
@WebServlet("/AuthImage")
public class AuthImage extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		String user_name= (String)session.getAttribute("user_name");
		
		MyWebCam webcam= new MyWebCam(user_name);
		String authImage= webcam.takePhoto();
		
		String temp="";
		for(int i=0;i<authImage.length();i++) {
			char ch= authImage.charAt(i);
			if(ch=='\\') {
				temp+="\\\\";
			}
			temp+=ch;
		}
		authImage=temp;
		
		AuthenticateDAO auth= new AuthenticateDAO();
		try {
			auth.insertImage(user_name, authImage);
			response.sendRedirect("welcome.jsp");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
