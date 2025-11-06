package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Shain;
import model.ShainDAO;
import model.ShainSearch;

public class ShainMaintenance extends HttpServlet {

    private ShainDAO shainDAO = new ShainDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action==null?"list":action) {
            case "add":
            	// 新規登録 初期表示
	            RequestDispatcher dispatcherAdd = request.getRequestDispatcher("/WEB-INF/shain_touroku.jsp");
	            dispatcherAdd.forward(request, response);
                break;

            case "edit":
            	// 更新 初期表示
	            int editId = Integer.parseInt(request.getParameter("id"));
	            Shain editShain = shainDAO.findById(editId);
	            request.setAttribute("shain", editShain);
	            RequestDispatcher dispatcherEdit = request.getRequestDispatcher("/WEB-INF/shain_edit.jsp");
	            dispatcherEdit.forward(request, response);
                break;

            case "delete":
            	// 削除 初期表示
                int deleteId = Integer.parseInt(request.getParameter("id"));
                Shain deleteShain = shainDAO.findById(deleteId);
                request.setAttribute("shain", deleteShain);
                RequestDispatcher dispatcherDelete = request.getRequestDispatcher("/WEB-INF/shain_delete.jsp");
                dispatcherDelete.forward(request, response);
                break;
                
            case "search": {
                // 検索　初期表示
                request.setAttribute("searchResults", null);
                request.getRequestDispatcher("/WEB-INF/shain_search.jsp").forward(request, response);
                break;
            }

            default:
            	String name = request.getParameter("name");
            	String gender = request.getParameter("gender");
            	String note = request.getParameter("note");
            	String sortOrder = request.getParameter("SortOrder");
            	
            	ShainSearch shainSearch = new ShainSearch();
            	
            	shainSearch.setName(name);
            	shainSearch.setGender(gender);
            	shainSearch.setNote(note);
            	if ("asc".equals(sortOrder)) {
            		shainSearch.setSortMode("asc");
            		shainSearch.setSortColumn("name");
            	} else if ("desc".equals(sortOrder)) {
            		shainSearch.setSortMode("desc");
            		shainSearch.setSortColumn("name");
            	}
            	
            	
            	request.setAttribute("name", name);
            	request.setAttribute("gender", gender);
            	request.setAttribute("note", note);
            	request.setAttribute("sortOrder", sortOrder);
            	
            	// 一覧表示
                List<Shain> listShain = shainDAO.findAll(shainSearch);
                request.setAttribute("shainList", listShain);
                RequestDispatcher dispatcherList = request.getRequestDispatcher("/WEB-INF/shain_list.jsp");
                dispatcherList.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "list";
        }
    	String idStr = null;
        String name = null;
        String gender = null;
        String note = null;
        Shain shain = new Shain();

        switch (action) {
            case "add":
                // 新規登録 保存処理
                name = request.getParameter("shimei");
                gender = request.getParameter("seibetsu");
                note = request.getParameter("bikou");

                if (name == null || name.trim().isEmpty() ||
                    gender == null || gender.trim().isEmpty()) {
                    request.setAttribute("shimeiError", "氏名と性別は必須です");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/shain_touroku.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                shain = new Shain();
                shain.setName(name);
                shain.setGender(gender);
                shain.setNote(note);

                shainDAO.insert(shain);
                //TODO ここは更新画面にリダイレクトするのもあり
                response.sendRedirect(request.getContextPath() + "/maintenance?action=list");
                break;

            case "update":
                // 更新 保存処理
            	idStr = request.getParameter("id");
                name = request.getParameter("shimei");
                gender = request.getParameter("seibetsu");
                note = request.getParameter("bikou");
            	
                if (idStr == null || idStr.trim().isEmpty() ||
                    name == null || name.trim().isEmpty() ||
                    gender == null || gender.trim().isEmpty()) {
                    request.setAttribute("shimeiError", "氏名と性別は必須です");
	                RequestDispatcher dispatcherEdit = request.getRequestDispatcher("/WEB-INF/shain_edit.jsp");
	                dispatcherEdit.forward(request, response);
                    return;
                }

                shain = new Shain();
                shain.setId(Integer.parseInt(idStr));
                shain.setName(name);
                shain.setGender(gender);
                shain.setNote(note);

                shainDAO.update(shain);
                response.sendRedirect(request.getContextPath() + "/maintenance?action=list");
                break;

            case "delete":
                // 削除 保存処理
                int deleteId = Integer.parseInt(request.getParameter("id"));
                shainDAO.delete(deleteId);
                response.sendRedirect(request.getContextPath() + "/maintenance?action=list");
                break;

            case "search": {
            	 // 検索 保存処理
                String keyword = request.getParameter("keyword");
                List<Shain> list;
                if (keyword == null || keyword.isBlank()) {
                    // 空検索は0
                    list = java.util.List.of();
                } else {
                    list = shainDAO.findByNameLike(keyword);
                }
                request.setAttribute("searchResults", list);
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
