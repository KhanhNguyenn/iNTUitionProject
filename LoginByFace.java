package iNTuition.ternary;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginByFace
 */
@WebServlet("/LoginByFace")
public class LoginByFace extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		File file = new File(
				"C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\auth-image\\checking_user.jpg");
		if (file.delete()) {
			System.out.println("Delete successfully");
		} else {
			System.out.println("Doesn't exist");
		}
		//take photo
		MyWebCam webcam = new MyWebCam("checking_user");
		String image = webcam.takePhoto();

		//get image list in database
		AuthenticateDAO auth = new AuthenticateDAO();
		List<String> imageList = null;
		try {
			imageList = auth.getImageList();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(imageList);
		if (imageList == null || imageList.size() == 0) {
			response.sendRedirect("login2.jsp");
		}
		
		//check similarity of current image and image in imageList
		String user_name = null;
		for (int i = 0; i < imageList.size(); i++) {
			String currentImg = imageList.get(i);
//			System.out.println(currentImg);
			Process p=null;
			try {
				ProcessBuilder pb = new ProcessBuilder("python",
						"C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\python-script\\zface_sourcecode.py",
						currentImg);
				p = pb.start();
			}
			catch(Exception e) {
				System.out.println("Something went wrong");
				e.printStackTrace();
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line =in.readLine();
			
			double differ = Double.parseDouble(line);
//			 NEED TO CHECK MINIMAL SATISFACTION
			if (differ < 0.4) {
				try {
					user_name = auth.getNameFromImg(currentImg);
					System.out.println("user_name: " + user_name);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpSession session = request.getSession();
				session.setAttribute("user_name", user_name);
				break;

			}
		}

		if (user_name == null) {
			response.sendRedirect("login2.jsp");
		} else {
			response.sendRedirect("welcome.jsp");
		}

	}

}
