package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhpost;
import service.DbPost;

/**
 * Servlet implementation class Newsfeed
 */
@WebServlet("/Newsfeed")
public class Newsfeed extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Newsfeed() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// users can get to this servlet through a get request so handle it here
		// With a get request the parameters are part of the url.
		// We already handle everything in doPost so just call that.
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

		long filterByUserID = 0;
		String searchtext = "";

		// Get posts based on parameter, if no posts, then show all posts

		List<Bhpost> posts = null;

		if (request.getParameter("userid") != null && !request.getParameter("userid").isEmpty()) {
			filterByUserID = Integer.parseInt(request.getParameter("userid").toString());
			posts = DbPost.postsofUser(filterByUserID);
		} else if (request.getParameter("searchtext") != null && !request.getParameter("searchtext").isEmpty()) {
			searchtext = request.getParameter("searchtext").toString();
			posts = DbPost.searchPosts(searchtext);

		} else {
			posts = DbPost.bhPost();
		}
		
		//Add posts to request
		request.setAttribute("posts", posts);
		
		nextUrl = "/newsfeed.jsp";
		
		//Go to next Servlet
		getServletContext().getRequestDispatcher(nextUrl).forward(request, response);

	}

}
