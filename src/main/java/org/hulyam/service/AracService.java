package org.hulyam.service;

import org.hulyam.repository.AracRepository;
import org.hulyam.repository.entity.Arac;
import org.hulyam.repository.entity.EAracMarkaModel;
import org.hulyam.utility.MyFactoryService;

public class AracService extends MyFactoryService<AracRepository, Arac, Integer> {
    public AracService() {
        super(new AracRepository());
    }

    public boolean plakaVarMi(String plaka) {
        return getRepository().plakaVarMi(plaka);
    }

    public Arac plakayaGoreBul(String secilenPlaka) {
        return getRepository().plakayaGoreBul(secilenPlaka);
    }

    public void modeleGoreBul(EAracMarkaModel eAracMarkaModel) {
        getRepository().modeleGoreBul(eAracMarkaModel);
    }
}
