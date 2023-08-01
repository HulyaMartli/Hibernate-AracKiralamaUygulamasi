package org.hulyam.utility;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class MyFactoryRepository<T, ID> implements ICrud<T, ID> {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private Session session;
    private Transaction transaction;
    private T t;

    public MyFactoryRepository(T t) {
        entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
        this.t = t;
    }

    public void openSession() {
        session = HibernateUtility.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public void closeSession() {
        transaction.commit();
        session.close();
    }

    @Override
    public <S extends T> S save(S entity) {
        try {
            openSession();
            session.save(entity);
            closeSession();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        try {
            openSession();
            entities.forEach(entity -> {
                session.save(entity);
            });
            closeSession();
            return entities;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public <S extends T> S update(S entity) {
        try {
            openSession();
            session.update(entity);
            closeSession();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public <S extends T> Iterable<S> updateAll(Iterable<S> entities) {
        try {
            openSession();
            entities.forEach(entity -> {
                session.update(entity);
            });
            closeSession();
            return entities;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(ID id) {
        Optional<T> sonuc = findById(id);
        return delete(sonuc.get());
    }

    @Override
    public Optional<T> findById(ID id) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get("id"), id));
        List<T> sonuc = entityManager.createQuery(criteria).getResultList();
        if (sonuc.isEmpty()) return Optional.empty();
        return Optional.of(sonuc.get(0));
    }

    @Override
    public List<T> findByEntity(T entity) {
        try {
            //Musteri{id=null, ad="Ali", adres="İzmir"}
            //
            Field[] fields = entity.getClass().getDeclaredFields(); //id,ad,adres
            CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
            Root<T> root = (Root<T>) criteria.from(t.getClass());
            criteria.select(root);
            List<Predicate> kosulListesi = new ArrayList<>();

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);//bu adım sayesinde private alanlar da erişime açılır.
                if (fields[i].get(entity) != null && !fields[i].getName().equals("id")) {
                    if (fields[i].getType().isAssignableFrom(String.class))
                        kosulListesi.add(criteriaBuilder.like(root.get(fields[i].getName()), "%" + fields[i].get(entity) + "%"));
                    else if (fields[i].getType().isAssignableFrom(Integer.class))
                        kosulListesi.add(criteriaBuilder.equal(root.get(fields[i].getName()), fields[i].get(entity)));
                    else
                        kosulListesi.add(criteriaBuilder.equal(root.get(fields[i].getName()), fields[i].get(entity)));
                }
            }

            criteria.where(kosulListesi.toArray(new Predicate[]{}));
            return entityManager.createQuery(criteria).getResultList();

        } catch (Exception e) {
            System.out.println("Beklenmedik bir hata. findByEntity." + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean existById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<T> findAllByColumnNameAndValue(String column, String columnValue) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get(column), columnValue));
        return entityManager.createQuery(criteria).getResultList();
    }
}
