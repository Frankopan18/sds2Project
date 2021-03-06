package com.task.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.task.dao.DAOProvider;
import com.task.entity.users.Student;

@WebServlet(urlPatterns = { "/index.jsp" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		int password = req.getParameter("password").hashCode();
		Student student = DAOProvider.getDAO().getStudentByEmail(email);
		// authentication check
		if (password == student.getStudentPassword()) {
			req.getSession().setAttribute("current.user.mail", student.getEmail());
			if (student.getSemesterType().equals("Autumn")) {
				String contextPath = req.getContextPath();
				resp.sendRedirect(contextPath + "/autumnCourses.jsp");
				return;

			} else if (student.getSemesterType().equals("Spring")) {
				String contextPath = req.getContextPath();
				resp.sendRedirect(contextPath + "/springCourses.jsp");
				return;
			}
		}
		resp.sendRedirect("/task/index.jsp");
	}

}
