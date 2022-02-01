package net.java.usermanagement.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import net.java.usermanagement.dao.UserDAO;
import net.java.usermanagement.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO;   
    
    public UserServlet() {
        this.userDAO=new UserDAO();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		this.doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	String action=request.getServletPath();
	
	switch(action) {
	case "/new":
		showNewForm(request,response);
		break;
	case "/insert":
		try {
			insertUser(request,response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		break;
	case "/delete":
		try {
			deleteUser(request,response);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		break;
	case "/edit":
		try {
			showEditForm(request,response);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		break;
	case "/update":
		try {
			updateUser(request,response);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		break;
		default:
		try {
			listUser(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			break;
	}}
	private void listUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException, ServletException{
		List<User> listUser=userDAO.selectAllUser();
		request.setAttribute("listUser",listUser);
		RequestDispatcher dispatcher=request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
		
	}
	private void updateUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException{
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String country=request.getParameter("country");
		User book=new User(id,name,email,country);
		userDAO.updateUser(book);
		response.sendRedirect("list");
	}
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException{
		int id=Integer.parseInt(request.getParameter("id"));
		userDAO.deleteUser(id);
		response.sendRedirect("list");
	}
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException, ServletException{
		int id=Integer.parseInt(request.getParameter("id"));
		User existingUser=userDAO.selectUser(id);
		RequestDispatcher dispatcher=request.getRequestDispatcher("user-form-jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
	}
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		RequestDispatcher dispatcher=request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}private void insertUser(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException{
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String country=request.getParameter("country");
		User newUser=new User(name,email,country);
		userDAO.insertUser(newUser);
		response.sendRedirect("list");
	
	}

	
	
}
