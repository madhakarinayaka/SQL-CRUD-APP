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
@WebServlet("/edit")
public class EditRecordServlet extends HttpServlet {
	 private final static String query = "update student set firstName=?,lastName=?,email=?,mobile=? where id=?";
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        //get PrintWriter
	        PrintWriter pw = resp.getWriter();
	        //set content type
	        resp.setContentType("text/html");
	        //link the bootstrap
	        pw.println("<link rel='stylesheet' href='edit.css' />");
	        //get the values
	        int id = Integer.parseInt(req.getParameter("id"));
	        String firstname = req.getParameter("firstName");
	        String lastname = req.getParameter("lastName");
	        String email = req.getParameter("email");
	        String mobile = req.getParameter("mobile");
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
	            ps.setString(1, firstname);
	            ps.setString(2, lastname);
	            ps.setString(3, email);
	            ps.setString(4, mobile);
	            ps.setInt(5, id);
	            //execute the query
	            int count = ps.executeUpdate();
	            pw.println("<div>");
	            if(count==1) {
	                pw.println("<center><h2>Record Edited Successfully</h2></center>");
	            }else {
	                pw.println("<center><h2>Record Not Edited</h2></center>");
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
	        //close the stream
	        pw.close();
	    }
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	        doGet(req,res);
	    }
}
