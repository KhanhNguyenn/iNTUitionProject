package iNTuition.ternary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Decode
 */
@WebServlet("/Decode")
@MultipartConfig(location = "C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\uploadImage", maxFileSize = 10485760L)
public class Decode extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MultipartMap map= new MultipartMap(request,this);
		HttpSession session= request.getSession();
		
		String key= request.getParameter("key").trim();
		if(key.length()==0) {
			response.sendRedirect("decode.jsp");
		}
		File file=null;
		try {
			file= map.getFile("image");
		}
		catch(Exception e) {
			response.sendRedirect("decode.jsp");
		}
		
		//format fileName
		String imagePath= file.getPath();
		String temp="";
		for(int i=0;i<imagePath.length();i++) {
			char ch= imagePath.charAt(i);
			if(ch=='\\'){
				temp+="\\\\";
			}
			temp+=ch;
		}
		imagePath=temp;
		
		String textPath= "C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\decrypted-text\\script.txt";
		try {
			File tempFile=new File(textPath);
			if(tempFile.exists())
				tempFile.delete();
			ProcessBuilder pb= new ProcessBuilder("python","C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\python-script\\decoder-main.py",imagePath,key,textPath);
			Process p=pb.start();
			
			while(!tempFile.exists());
			
			session.setAttribute("textPath", textPath);
			session.setAttribute("imagePath", imagePath);
			response.sendRedirect("decode2.jsp");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
