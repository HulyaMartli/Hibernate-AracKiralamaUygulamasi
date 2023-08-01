package org.hulyam.utility;

import java.util.List;
import java.util.Optional;

public interface IService<T, ID> {
    //Kaydetme İşlemleri
    <S extends T> S save(S entity);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

    <S extends T> S update(S entity);

    <S extends T> Iterable<S> updateAll(Iterable<S> entities);

    //Silme İşlemleri
    boolean delete(T entity);

    boolean deleteById(ID id);

    //Arama ve Listeleme İşlemleri
    Optional<T> findById(ID id);

    /**
     * Bu metod entity içinde bulunan herhangi bir alana(field) göre kendisi otomatik sorgu yapacak.
     * Reflection API kullanılacak.
     *
     * @param entity
     * @return
     */
    List<T> findByEntity(T entity);

    boolean existById(ID id);

    List<T> findAll();

    List<T> findAllByColumnNameAndValue(String column, String columnValue);

}
