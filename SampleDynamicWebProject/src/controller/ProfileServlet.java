package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhuser;
import service.DbUser;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProfileServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
		String nextUrl = "/error.jsp";
		HttpSession session = request.getSession();
		// If the user is invalid/null, get him to login page, kill the session

		if (session.getAttribute("user") == null) {
			session.invalidate();

			nextUrl = "/login.jsp";

			// Get him to login page
			response.sendRedirect(request.getContextPath() + nextUrl);

			return;
			// return prevents error
		}

		// if user wants to update profile, update the profile
		long userId;
		String action = "";
		try {
			userId = Long.parseLong(request.getParameter("userid"));

			action = request.getParameter("action");

			// 2 Bhusers because if user goes on the profile and if the profile is of
			// himself he'll be able to update
			// else it will be read only for him

			Bhuser profileUser = null;
			Bhuser loggedInUser = null;

			if (action.equals("updateprofile")) {
				profileUser = DbUser.getUser(userId);
				profileUser.setMotto(request.getParameter("usermotto"));
				profileUser.setUseremail(request.getParameter("useremail"));
				DbUser.update(profileUser);
			}

			profileUser = DbUser.getUser(userId);

			loggedInUser = (Bhuser) session.getAttribute("user");

			if (profileUser.getBhuserid() == loggedInUser.getBhuserid()) {
				session.setAttribute("editprofile", true);
			} else {
				session.setAttribute("editprofile", false);
			}

			// populate the data in the attributes
			int imgSize = 120;
			SimpleDateFormat sdf = new SimpleDateFormat("MMM  d, yyyy");
			String joindate = sdf.format(profileUser.getJoindate());
			request.setAttribute("userid", profileUser.getBhuserid());
			request.setAttribute("userimage", DbUser.getGravatarURL(profileUser.getUseremail(), imgSize));
			request.setAttribute("username", profileUser.getUsername());
			request.setAttribute("useremail", profileUser.getUseremail());
			request.setAttribute("usermotto", profileUser.getMotto());
			request.setAttribute("userjoindate", joindate);
			nextUrl = "/profile.jsp";

		}

		catch (Exception e) {
			// print the exception so we can see it
			// while testing the application
			// in production it isn't a good idea to
			// print to the console since it
			// consumes resources and will not be seen
			System.out.println(e);
		}
		
		//Redirect
		getServletContext().getRequestDispatcher(nextUrl).forward(request, response);
	}

}
