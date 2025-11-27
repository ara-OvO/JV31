package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
/**
 * DAO(Data Access Object):データベースへのアクセスを行うクラスを作り、そのクラスを通してデータベースへアクセスするデザインパターン
 * 
 */
public class ShainDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IH13A01_JV31_07_PU");

    /**
     * 全件検索
     * @return 社員データ
     */
    public List<Shain> findAll(ShainSearch shainSearch) {
        EntityManager em = emf.createEntityManager();
        try {
        	CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Shain> cq = cb.createQuery(Shain.class);
            Root<Shain> root = cq.from(Shain.class);
            List<Predicate> predicates = new ArrayList<>();
            
            //まずnullじゃないことを確認
            //次にTrimして、からじゃないことを確認
            //Trimは文字列の前後から半角空白を取り除くこと
            if (shainSearch.getName() != null && !shainSearch.getName().trim().isEmpty()) {
            	//SQLのLIKE句を追加
            	//name = %LIKE% の部分一致 →　"マイケル高橋" "高橋マイケル"
            	//※超大量データの場合は前方一致か完全一致をデフォルトにするといい
                predicates.add(cb.like(root.get("name"),"%" + shainSearch.getName().trim() + "%"));
            }
            if (shainSearch.getGender() != null && !shainSearch.getGender().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("gender"), shainSearch.getGender().trim()));
            }
            if (shainSearch.getNote() != null && !shainSearch.getNote().trim().isEmpty()) {
                predicates.add(cb.like(root.get("note"),"%" + shainSearch.getNote().trim() + "%"));
            }

            //可変長配列からSQLのwhere句を生成
            cq.where(predicates.toArray(new Predicate[0]));
            
            //ソート順
            //昇順か降順か
            if ("asc".equals(shainSearch.getSortMode())) {
                cq.orderBy(cb.asc(root.get(shainSearch.getSortColumn())));
            } else if ("desc".equals(shainSearch.getSortMode())) {
                cq.orderBy(cb.desc(root.get(shainSearch.getSortColumn())));
            }
                       
            return em.createQuery(cq).getResultList();
            
	    } finally {
	        em.close();
	    }	
    }

    /**
     * IDをキーに社員を1件抽出
     * @param id 社員ID
     * @return
     */
    public Shain findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Shain.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * 名前検索
     * @param keyword
     * @return
     */
    public List<Shain> findByNameLike(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            if (keyword == null || keyword.isBlank()) return List.of();

            String kw = "%" + escapeLike(keyword.trim()) + "%";
            return em.createQuery(
                    "SELECT s FROM Shain s " +
                    "WHERE s.name LIKE :kw ESCAPE '\\' " +
                    "ORDER BY s.id",
                    Shain.class)
                .setParameter("kw", kw)
                .getResultList();
        } finally {
            em.close();
        }
    }

    // LIKE 用の簡易エスケープ（\ と % と _）
    private static String escapeLike(String s) {
        return s.replace("\\", "\\\\")
                .replace("%",  "\\%")
                .replace("_",  "\\_");
    }


    /**
     * 社員データ追加
     * @param shain
     */
    public void insert(Shain shain) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(shain);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * 社員データ更新
     * @param shain
     */
    public void update(Shain shain) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(shain);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * 社員データ削除
     * @param id
     */
    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Shain shain = em.find(Shain.class, id);
            if (shain != null) {
                em.remove(shain);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    
}
