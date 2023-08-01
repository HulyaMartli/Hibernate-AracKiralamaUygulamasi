package org.hulyam.repository;

import org.hulyam.repository.entity.Arac;
import org.hulyam.repository.entity.EAracMarkaModel;
import org.hulyam.utility.MyFactoryRepository;

import javax.persistence.TypedQuery;
import java.util.List;

public class AracRepository extends MyFactoryRepository<Arac, Integer> {
    public AracRepository() {
        super(new Arac());
    }

    public boolean plakaVarMi(String plaka) {
        TypedQuery<Boolean> typedQuery = getEntityManager()
                .createNamedQuery("Arac.plakaVarMi", Boolean.class);
        typedQuery.setParameter("plakaparametre", plaka);
        return typedQuery.getSingleResult();
    }

    public Arac plakayaGoreBul(String secilenPlaka) {
        Arac arac = null;
        boolean plakaVarMi = plakaVarMi(secilenPlaka);
        if (plakaVarMi) {
            TypedQuery<Arac> typedQuery = getEntityManager()
                    .createNamedQuery("Arac.plakayaGoreBul", Arac.class);
            typedQuery.setParameter("plakaparametre", secilenPlaka);
            arac = typedQuery.getSingleResult();
            System.out.println(arac);
            return arac;
        } else {
            System.err.println("|------------------------------------------|");
            System.err.println("| Bu plaka (" + secilenPlaka + ") ile bir arac yoktur! |");
            System.err.println("|------------------------------------------|");
        }
        return arac;
    }

    public void modeleGoreBul(EAracMarkaModel eAracMarkaModel) {
        TypedQuery<Arac> typedQuery = getEntityManager()
                .createNamedQuery("Arac.modeleGoreBul", Arac.class);
        typedQuery.setParameter("markamodelparametre", eAracMarkaModel);
        List<Arac> aracList = typedQuery.getResultList();
        for (Arac a : aracList) {
            System.out.println(a);
        }


    }
}
