package model;

/**
 * 社員一覧の検索用のクラス
 */
public class ShainSearch{
	
	  private Integer id;
	
	  private String name;
	  
	  private String gender;
	  
	  private String note;
	  
	  private String sortMode; 
	  
	  private String sortColumn;

	  
	  public int getId() {
		  return id;
	  }
	  
	  /**
	   * @return name
	   */
	  public String getName() {
	      return name;
	  }

	  public void setName(String name) {
	      this.name = name;
	  }

	  /**
	   * @return gender
	   */
	  public String getGender() {
	      return gender;
	  }
	  
	  public void setGender(String gender) {
	      this.gender = gender;
	  }
	  
	  /**
	   * @return note
	   */
	  public String getNote() {
	      return note;
	  }
	  
	  public void setNote(String note) {
	      this.note = note;
	  }
	  
	  /**
	   * @return sortMode
	   */
	  public String getSortMode() {
	      return sortMode;
	  }
	  
	  public void setSortMode(String sortMode) {
		    this.sortMode = sortMode;
		}

	  
	  /**
	   * @return gender
	   */
	  public String getSortColumn() {
	      return sortColumn;
	  }
	  
	  public void setSortColumn(String sortColumn) {
	      this.sortColumn = sortColumn;
	  }
}
