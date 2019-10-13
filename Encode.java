package iNTuition.ternary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;

import org.python.core.PyInstance;

/**
 * Servlet implementation class Encode
 */
@WebServlet("/Encode")
@MultipartConfig(location = "C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\upload", maxFileSize = 10485760L)
public class Encode extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//create encode table for user based on user_name
		HttpSession session= request.getSession();
		String user_name= (String)session.getAttribute("user_name");
		UserEncodeDAO userEncodeTable= new UserEncodeDAO(user_name);
		try {
			userEncodeTable.setUp();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		MultipartMap map = new MultipartMap(request, this);

		String script_text = request.getParameter("script_text");
		File file=null;
		String key = request.getParameter("key").trim();
		try {
			file = map.getFile("script_file");
		}
		catch(Exception e) {
			response.sendRedirect("encode2.jsp");
		}
		
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;
		int range = max - min;

		if (script_text.length() == 0) {
			if (file == null) {
				response.sendRedirect("encode2.jsp");
			}
//			File temp= new File("C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\upload\\script_text("
//					+ Math.abs(HashCodeText.getName(file))+").txt");
//			file.renameTo(temp);
			
		} else {
			if (file != null) {
				response.sendRedirect("encode2.jsp");
			} else {
				// write string script_text to file
				String absoluteFilePath = "C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\upload\\your_text_"
						+ Math.abs((int)(Math.random()*range)+min)+".txt";
				file = new File(absoluteFilePath);
//				while (!file.createNewFile()) {
//					// if exist-generate new file name
//					absoluteFilePath = "C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\upload\\script_text_"
//							+ Math.abs((int) (Math.random() * range) + min)+".txt";
//					file= new File(absoluteFilePath);
//				}
				file.createNewFile();
				BufferedWriter writer= new BufferedWriter(new FileWriter(file.getPath()));
				writer.write(script_text);
				writer.close();
				
			}
		}
		
		//format fileName before storing
		String fileName= file.getPath();
		String temp="";
		for(int i=0;i<fileName.length();i++) {
			char ch= fileName.charAt(i);
			if(ch=='\\'){
				temp+="\\\\";
			}
			temp+=ch;
		}
		fileName=temp;
		
		String imageName= ""+HashCodeText.getName(file)+"_"+key;
		try {
			ProcessBuilder pb = new ProcessBuilder("python",
					"C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\python-script\\encoder-main.py",
					fileName, key,imageName);
			Process p = pb.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			System.out.println("Save image successfully");
			
			//foramt imagePath before storing
			String imagePath= "C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\encrypted-image\\"+imageName+".png";
			String temp2="";
			for(int i=0;i<imagePath.length();i++) {
				char ch1= imagePath.charAt(i);
				if(ch1=='\\') {
					temp2+="\\\\";
				}
				temp2+=ch1;
			}
			imagePath=temp2;
			
			//insert record into user encode table
			userEncodeTable.insert(fileName, imagePath, Integer.parseInt(key));
			
			response.sendRedirect("encode3.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
