package servlet;

import java.io.IOException;

import action.Action;
import action.ActionBase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShainMaintenance extends ActionBase {

    private static final Action action = new Action();

    @Override
    protected void doGetInternal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	
        String getAction = getAction();

        switch (getAction) {
            case "add":
                // 新規登録 初期表示
        	dispatcher(request, response, "/WEB-INF/shain_touroku.jsp");
                break;

            case "edit":
        	// 更新 初期表示
        	action.shainView(request);
	        dispatcher(request, response, "/WEB-INF/shain_edit.jsp");
                break;

            case "delete":
            	// 削除 初期表示
        	action.shainView(request);
	        dispatcher(request, response, "/WEB-INF/shain_delete.jsp");
                break;

            default:
        	// 一覧表示
        	action.listView(request);
	        dispatcher(request, response, "/WEB-INF/shain_list.jsp");
                break;
        }
    }

    @Override
    protected void doPostInternal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        String getAction = getAction();
        
        switch (getAction) {
            case "add":
                // 新規登録 保存処理
        	action.addShain(request, response);
                //TODO ここは更新画面にリダイレクトするのもあり
                response.sendRedirect(request.getContextPath() + "/maintenance?action=list");
                break;

            case "update":
                // 更新 保存処理
        	action.updateShain(request, response);
                response.sendRedirect(request.getContextPath() + "/maintenance?action=list");
                break;

            case "delete":
                // 削除 保存処理
        	action.deleteShain(request);
                response.sendRedirect(request.getContextPath() + "/maintenance?action=list");
                break;

            case "search": {
        	// 検索 保存処理
        	action.searchShain(request);
                request.getRequestDispatcher("/WEB-INF/shain_search.jsp").forward(request, response);
                return; 
            }
            
            default:
            	//一覧表示 リダイレクト(TODO:絞り込み検索する場合、GETとPOSTを同じメソッドで行うと良い)
                response.sendRedirect(request.getContextPath() + "/maintenance");
                break;
        }
    }
}
