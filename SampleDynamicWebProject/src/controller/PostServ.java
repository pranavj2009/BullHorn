package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhpost;
import model.Bhuser;
import service.DbPost;

/**
 * Servlet implementation class PostServ
 */
@WebServlet("/PostServ")
public class PostServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PostServ() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String posttext = request.getParameter("posttext");
		String nextUrl = "/error.jsp";

		// Get Session
		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			session.invalidate();
			nextUrl = "/login.jsp";
		}

		else {
			Bhuser bhuser = (Bhuser) session.getAttribute("user");

			// Insert the post
			Bhpost bhPost = new Bhpost();
			bhPost.setBhuser(bhuser);
			Date postdate = Calendar.getInstance().getTime();// today's date
			bhPost.setPostdate(postdate);
			bhPost.setPosttext(posttext);

			DbPost.insert(bhPost);

			nextUrl = "/Newsfeed";// go to newsfeed servlet to show all posts
		}

		// Go to next Servlet
		getServletContext().getRequestDispatcher(nextUrl).forward(request, response);
	}

}
