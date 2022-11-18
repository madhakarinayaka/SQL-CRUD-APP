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

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	 private final static String query = "delete from student where id = ?;";
	 
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        //get PrintWriter
	        PrintWriter pw = resp.getWriter();
	        //set content type
	        resp.setContentType("text/html");
	        //link the bootstrap
	        pw.println("<link rel='stylesheet' href='delete.css'></link>");
	        //get the values
	        int id = Integer.parseInt(req.getParameter("id"));
	        //load the JDBC driver
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        }catch(Exception e) {
	            e.printStackTrace();
	        }
	        //generate the connection
	        try(Connection con = DriverManager.getConnection("jdbc:mysql:///student","root","root");
	                PreparedStatement ps = con.prepareStatement(query);){
	            //set the values
	            ps.setInt(1, id);
	            //execute the query
	            int count = ps.executeUpdate();
	            pw.println("<div>");
	            if(count==1) {
	                pw.println("<h2>Record Deleted Successfully</h2>");
	            }else {
	                pw.println("<h2>Record Not Deleted</h2>");
	            }
	        }catch(SQLException se) {
	            pw.println("<center><h2>"+se.getMessage()+"</h2></center>");
	            se.printStackTrace();
	        }catch(Exception e) {
	            e.printStackTrace();
	        }
	        pw.println("<br /><br />");
	        pw.println("<center><button class='show'><a href='showdata'>Show Users</a></button></center>");
	        pw.println("</div>");
	        //close 
	        pw.close();
	    }
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	        doGet(req,res);
	    }
}
