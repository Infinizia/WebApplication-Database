package AppService;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseService {
	private static final String LayoutUrl = "/WEB-INF/View/Shared/Layout.jsp";
	
	public static Boolean SendResponse(HttpServletRequest request, HttpServletResponse response){
		try {
            RequestDispatcher rd = request.getRequestDispatcher(ResponseService.LayoutUrl);
            rd.forward(request, response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}
	public static void SendJson(HttpServletResponse response, Object vm1){
       try {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write(JsonService.ToJson(vm1));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void SendJson(HttpServletResponse response, Object vm1, Object vm2){
	       try {
	            response.setContentType("application/json");
	            PrintWriter out = response.getWriter();
	            out.write(JsonService.ToJson(vm1));
	            out.write(JsonService.ToJson(vm2));
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
}
