package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.Connection;

import AppService.JsonService;
import AppService.ResponseService;
import DbModel.Star;
import DbContext.dbStars;

/**
 * Servlet implementation class InsertStar
 */
@WebServlet("/InsertStar")
public class InsertStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertStar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Star star = (Star)JsonService.GetObjectFromJson(request.getParameter("star"), Star.class);
		dbStars dbs = new dbStars("master");

		int statusCheck = dbs.InsertStar(star);
		if (statusCheck != -1)
			ResponseService.SendJson(response, "success");
		else
			ResponseService.SendJson(response, "fail");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
