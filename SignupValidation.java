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
 * Servlet implementation class SignupValidation
 */
@WebServlet("/SignupValidation")
public class SignupValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignupValidation() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phoneNo = request.getParameter("phoneNo");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dummy", "root", "");
			PreparedStatement ps, preparedStatement;

			String query = "select * from register";
			preparedStatement = con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString(1).equals(username)) {
					out.println("<br><h1>Username already exists</h1>");
				}
			}
			if (password.length() < 6) {
				out.println("<br><h1>Password length must be greater than 5</h1>");
				throw new Exception();
			}
			ps = con.prepareStatement("insert into register values(?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			ps.setString(4, phoneNo);

			int i = ps.executeUpdate();

			if (i != 0) {
				out.println("<br><h2>Record has been inserted... :)<h2>");
			} else {
				out.println("<h2>Failed to insert the data...  :(<h2>");
			}
		} catch (Exception e) {
			out.println(e);
		}

	}

}
