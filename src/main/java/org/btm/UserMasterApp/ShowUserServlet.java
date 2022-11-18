package org.btm.UserMasterApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/showdata")
public class ShowUserServlet extends HttpServlet{
	private final static String query = "select id,firstName,lastName,email,mobile from student ";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	PrintWriter pw = resp.getWriter();
    	resp.setContentType("text/html");
    	pw.println("<link rel='stylesheet' href='show.css'>");
    	pw.println("<center><h1 class='text'>User Master Data</h1></center>");
    	
    	//load mysql
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	try(Connection con=DriverManager.getConnection("jdbc:mysql:///student","root","root");
    		PreparedStatement ps = con.prepareStatement(query);){
    		//resultSet
    		ResultSet rs = ps.executeQuery();
    		pw.println("<div>");
    		pw.println("<center>");
    		pw.println("<table class='nm'>");
            pw.println("<tr>");
            pw.println("<th>ID</th>");
            pw.println("<th>Name</th>");
            pw.println("<th>Email</th>");
            pw.println("<th>Mobile No</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");
            while(rs.next()) {
                pw.println("<tr>");
                pw.println("<td>"+rs.getInt(1)+"</td>");
                pw.println("<td>"+rs.getString(2)+" "+rs.getString(3)+"</td>");
                pw.println("<td>"+rs.getString(4)+"</td>");
                pw.println("<td>"+rs.getString(5)+"</td>");
                pw.println("<td><a href='editurl?id="+rs.getInt(1)+"'><button>Edit</button></a></td>");
                pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'><button>Delete</button></a></td>");
                pw.println("</tr>");
            }
    		pw.println("</table>");
    		pw.println("</center>");
    		
    	}catch(SQLException se) {
    		pw.println("<center><h2>"+se.getMessage()+"</h2></center>");
    		se.printStackTrace();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	pw.println("<br /><br />");
    	pw.println("<center><button class='show'><a href='home.html' class='create'>Create New Records</a></button></center>");
    	pw.println("</div>");
    	pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }
}
