package com.favorites.repository;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.List;

/**
 * @Description: JPA原生SQL基类
 * @Auth: yuyang
 * @Date: 2017/1/18 19:30
 * @Version: 1.0
 **/
@Service
public class BaseNativeSqlRepository {

    @PersistenceUnit
    private EntityManagerFactory emf;

    /**
     * JPA使用原生SQL，返回List形式的数组，数组中内容按照查询字段先后
     * @param sql
     * @return
     */
    public List<Object[]> sqlArrayList(String sql){
        EntityManager em=emf.createEntityManager();
        Query query=em.createNativeQuery(sql);
        List<Object[]> list = query.getResultList();
        em.close();
        return  list;
    }

    /**
     * JPA使用原生SQL，返回List<Object>形式的对象
     * @param sql
     * @param obj
     * @return
     */
    public List sqlObjectList(String sql, Object obj){
        EntityManager em=emf.createEntityManager();
        Query query=em.createNativeQuery(sql,obj.getClass());
        List list = query.getResultList();
        em.close();
        return  list;
    }

}
