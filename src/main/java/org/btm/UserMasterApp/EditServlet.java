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
@WebServlet("/editurl")
public class EditServlet extends HttpServlet{
	 private final static String query = "select firstName,lastName,email,mobile from student where id=?";
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        //get PrintWriter
	        PrintWriter pw = resp.getWriter();
	        //set content type
	        resp.setContentType("text/html");

	        //get the id
	        //get the values
	        int id = Integer.parseInt(req.getParameter("id"));
	        //link the bootstrap
	        pw.println("<link rel='stylesheet' href='edit.css'></link>");
	        //load the JDBC driver
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        }catch(Exception e) {
	            e.printStackTrace();
	        }
	        //generate the connection
	        try(Connection con = DriverManager.getConnection("jdbc:mysql:///student","root","root");
	                PreparedStatement ps = con.prepareStatement(query);){
	            //set value 
	            ps.setInt(1, id);
	            //resultSet
	            ResultSet rs = ps.executeQuery();
	            rs.next();
	            pw.println("<div>");
	            pw.println("<form action='edit?id="+id+"' method='post'>");
	            pw.println("<h2>Edit Records</h2>");
	            pw.println("<table class='nm'>");
	            pw.println("<tr>");
	            pw.println("<td><h3>Firstname</h3></td>");
	            pw.println("<td><input type='text' name='firstName' value='"+rs.getString(1)+"'></td>");
	            pw.println("</tr>");
	            pw.println("<tr>");
	            pw.println("<td><h3>Lastname</h3></td>");
	            pw.println("<td><input type='text' name='lastName' value='"+rs.getString(2)+"'></td>");
	            pw.println("</tr>");
	            pw.println("<tr>");
	            pw.println("<td><h3>Email</h3></td>");
	            pw.println("<td><input type='email' name='email' value='"+rs.getString(3)+"'></td>");
	            pw.println("</tr>");
	            pw.println("<tr>");
	            pw.println("<td><h3>mobile</h3></td>");
	            pw.println("<td><input type='text' name='mobile' value='"+rs.getString(4)+"'></td>");
	            pw.println("</tr>");
	            pw.println("<tr>");
	            pw.println("<td><button class='button' type='submit'>Update</button></td>");
	            pw.println("<td><button class='button' type='reset'>Cancel</button></td>");
	            pw.println("</tr>");
	            pw.println("</table>");
	            pw.println("</form>");
	        }catch(SQLException se) {
	            pw.println("<center><h2>"+se.getMessage()+"</h2></center>");
	            se.printStackTrace();
	        }catch(Exception e) {
	            e.printStackTrace();
	        }
	        pw.println("<center><button class='show'><a href='showdata'>Show Users</a></button></center>");
	        pw.println("</div>");
	        //close the stream
	        pw.close();
	    }
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	        doGet(req,res);
	    }
}
