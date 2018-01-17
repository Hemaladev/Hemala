package com.org.validation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SigninValidation
 */
@WebServlet("/SigninValidation")
public class SigninValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SigninValidation() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("newpassword");
		String repassword = request.getParameter("newpass");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dummy", "root", "");
			PreparedStatement ps;
			ps = con.prepareStatement("update register set password= ? where user=?;");
			if (!password.equals(repassword)) {
				out.println("<h2>Password Not Match...<h2>");
				throw new Exception();
			}
			ps.setString(1, password);
			ps.setString(2, username);
			int i = ps.executeUpdate();

			if (i <= 0) {
				out.println(i);
				out.println("<h2>Password doesnot Change..Try Again…</h2>");
			} else {
				out.println("<h2>Your password Change Successfully<h2>");
			}
		} catch (Exception e) {

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dummy", "root", "");
			PreparedStatement preparedStatement;
			boolean flag = false;
			String query = "select * from register";
			preparedStatement = con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString(1).equals(username) && resultSet.getString(2).equals(password)) {
					out.println("<br><h1>Logged in</h1>");
					flag = true;
				}
			}
			if (flag == false)
				out.println("<h2>Username/Password invalid...  :(<h2>");

		} catch (Exception e) {
			out.println(e);
		}

	}

}
