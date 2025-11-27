package action;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Shain;
import model.ShainDAO;
import model.ShainSearch;

public class Action {
    
    private static final ShainDAO shainDAO = new ShainDAO();
       
    /**
     * get処理のアクション
     */
    public void shainView(HttpServletRequest request) {
	int getId = Integer.parseInt(request.getParameter("id"));
	Shain shain = shainDAO.findById(getId);
	request.setAttribute("shain", shain);
    }
     
    public void listView(HttpServletRequest request) {
	String name = request.getParameter("name");
    	String gender = request.getParameter("gender");
    	String note = request.getParameter("note");
    	String sortOrder = request.getParameter("sortOrder");
    	
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
    }
    
    /**
     * post処理のアクション
     * @throws IOException 
     * @throws ServletException 
     */
    public void addShain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Shain shain = new Shain();
	
	String name = request.getParameter("shimei");
	String gender = request.getParameter("seibetsu");
	String note = request.getParameter("bikou");

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
    }
    
    public void updateShain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Shain shain = new Shain();
	
	String idStr = request.getParameter("id");
	String name = request.getParameter("shimei");
	String gender = request.getParameter("seibetsu");
	String note = request.getParameter("bikou");
    	
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
    }
    
    public void deleteShain(HttpServletRequest request) {
	int deleteId = Integer.parseInt(request.getParameter("id"));
        shainDAO.delete(deleteId);
    }
    
    public void searchShain(HttpServletRequest request) {
	 String keyword = request.getParameter("keyword");
         List<Shain> list;
         if (keyword == null || keyword.isBlank()) {
             // 空検索は0
             list = java.util.List.of();
         } else {
             list = shainDAO.findByNameLike(keyword);
         }
         request.setAttribute("searchResults", list);
    }
    
}
