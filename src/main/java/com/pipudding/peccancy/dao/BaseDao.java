package com.pipudding.peccancy.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T> {
	
	private Class<T> clazz;  
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public BaseDao() {  
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();  
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
        //System.out.println("DAO的真实实现类是：" + this.clazz.getName());  
    }  
	
	public void save(T entity)
	{
		sessionFactory.getCurrentSession().save(entity);
	}
	
	public void update(T entity) {  
		sessionFactory.getCurrentSession().update(entity);  
    }  
  
    public void delete(String id) {  
    		sessionFactory.getCurrentSession().delete(findById(id));  
    }  
    
    public void delete(T entity)
    {
    		sessionFactory.getCurrentSession().delete(entity);
    }
  
    public T findById(String id) {  
        return (T) sessionFactory.getCurrentSession().get(clazz,id); 
    }  
  
    public List<T> findByHQL(String hql, Object... params) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        for (int i = 0; params != null && i < params.length; i++) {  
        		query.setParameter(String.valueOf(i), params[i]);
        }  
        return query.list();  
    }  
    
    public void executeHQL(String hql, Object... params) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        for (int i = 0; params != null && i < params.length; i++) {  
            query.setParameter(String.valueOf(i), params[i]);  
        }  
        query.executeUpdate();
    }  
}
