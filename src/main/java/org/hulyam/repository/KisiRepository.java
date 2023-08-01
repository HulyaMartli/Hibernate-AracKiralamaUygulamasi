package org.hulyam.repository;

import org.hulyam.repository.entity.Kisi;
import org.hulyam.utility.MyFactoryRepository;

import javax.persistence.TypedQuery;

public class KisiRepository extends MyFactoryRepository<Kisi, Integer> {
    public KisiRepository() {
        super(new Kisi());
    }

    public boolean tcVarMi(String tc) {
        TypedQuery<Boolean> typedQuery = getEntityManager()
                .createNamedQuery("Kisi.tcVarMi", Boolean.class);
        typedQuery.setParameter("tcparametre", tc);
        return typedQuery.getSingleResult();
    }
}
