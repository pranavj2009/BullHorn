package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhuser;
import service.DbUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Validates Login of user based on the credentials and the session time-out", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nextUrl = "/error.jsp";

		String userEmail = request.getParameter("email");
		String userPassword = request.getParameter("password");

		String action = request.getParameter("action");
		String remember = request.getParameter("remember");

		// Get an instance of the session
		HttpSession session = request.getSession();

		// Add user to Session only if it is valid
		if (action.equals("logout")) {
			session.invalidate();
			nextUrl = "/login.jsp";
		} else if (DbUser.isValidUser(userEmail, userPassword)) {
			// Add user to the session
			Bhuser user = DbUser.getUserByEmail(userEmail);
			session.setAttribute("user", user);

			// Get his gravatar profile
			int gravatarImageWidth = 30;
			String gravatarURL = DbUser.getGravatarURL(userEmail, gravatarImageWidth);
			session.setAttribute("gravatarURL", gravatarURL);

			nextUrl = "/home.jsp";
		} else {
			nextUrl = "/login.jsp";
		}

		// Go to next page based on the value of the nextUrl
		getServletContext().getRequestDispatcher(nextUrl).forward(request, response);
	}

}
