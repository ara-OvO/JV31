package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * 日本語の「社員マスタ」用エンティティ
 * 英語表記のテーブルの場合、各nameプロパティを編集してね
 */
@Entity
@Table(name = "社員マスタ")
public class Shain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ユーザID")
    private int id;

    @Column(name = "氏名")
    private String name;

    @Column(name = "性別")
    private String gender;

    @Column(name = "備考")
    private String note;

    public Shain() {}

    public Shain(int id, String name, String gender, String note) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.note = note;
    }

    /*
     * 各項目のGetter＆Setter
     */
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/*
     * 区分値に対して名称をJavaで設定する場合
     */
    @Transient
    public String getGenderLabel() {
        switch (gender) {
            case "1": return "男性";
            case "2": return "女性";
            case "3": return "その他";
            default: return "未設定";
        }
    }
}
