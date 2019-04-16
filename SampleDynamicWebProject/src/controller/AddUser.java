package controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhuser;
import service.DbUser;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddUser() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String nextUrl = "/error.jsp";

		long userid = 0;
		String action = "";

		// This page does not require user to be logged on

		// Get the entered data
		String userName = "Test Value";//request.getParameter("userName");
		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");
		String userMotto = request.getParameter("userMotto");

		Bhuser user = DbUser.getUserByEmail(userEmail);

		if (user == null) {
			user = new Bhuser();
			user.setUsername(userName);
			user.setUseremail(userEmail);
			user.setUserpassword(userPassword);
			user.setMotto(userMotto);
			user.setJoindate(Calendar.getInstance().getTime());

			DbUser.insert(user);
			nextUrl = "/home.jsp";
		}

		else {
			String message = "You already have an account";

			request.setAttribute("message", message);

			nextUrl = "/login.jsp";
		}

		// Add the user to session
		session.setAttribute("user", user);

		// Redirect
		getServletContext().getRequestDispatcher(nextUrl).forward(request, response);

	}

}
