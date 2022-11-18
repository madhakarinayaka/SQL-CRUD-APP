package org.btm.UserMasterApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	private final static String query = "insert into student(firstname,lastname,email,mobile) values(?,?,?,?) ";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	PrintWriter pw = resp.getWriter();
    	resp.setContentType("text/html");
    	pw.println("<link rel='stylesheet' href='delete.css'></link>");
    	//get values
    	String firstName = req.getParameter("firstName");
    	String lastName = req.getParameter("lastName");
    	String email = req.getParameter("email");
    	String mobile = req.getParameter("mobile");
    	
    	//load mysql
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	try(Connection con=DriverManager.getConnection("jdbc:mysql:///student","root","root");
    		PreparedStatement ps = con.prepareStatement(query);){
    		ps.setString(1, firstName);
    		ps.setString(2, lastName);
    		ps.setString(3, email);
    		ps.setString(4, mobile);
    		int count = ps.executeUpdate();
    		if(count==1) {
    			pw.println("<center><h2>Record Registered Successfully</h2></center>"); 
    		}else {
    			pw.println("<center><h2>Record Not Registered</h2></center>");
    		}
    		
    	}catch(SQLException se) {
    		pw.println("<center><h2>"+se.getMessage()+"</h2></center>");
    		se.printStackTrace();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	pw.println("<br /><br />");
    	pw.println("<center><button class='show'><a href='showdata'>Show Users</a></button></center>");
    	pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }
}
