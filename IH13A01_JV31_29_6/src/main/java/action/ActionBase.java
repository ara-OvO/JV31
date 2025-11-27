package action;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ActionBase extends HttpServlet {
    
    /**
     * 変数も
     */
    private String action;
    
    /**
     * 共通メソッド、アクション名のセット・ゲット
     */
    protected void setAction(String action) {
	if (action == null || action.isEmpty()) {
	    action = "list";
	}
	this.action = action;
    }
    protected String getAction() {
	return this.action;
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	setAction(request.getParameter("action"));
	// 子クラスに処理を委譲
	this.doGetInternal(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	setAction(request.getParameter("action"));
	this.doPostInternal(request, response);
    }
    
    protected void doGetInternal(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }

    protected void doPostInternal(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }
    
    protected void dispatcher(HttpServletRequest request, HttpServletResponse response, String URL) throws ServletException, IOException {
        RequestDispatcher dispatcherAdd = request.getRequestDispatcher(URL);
        dispatcherAdd.forward(request, response);
    }
}
