package org.hulyam.service;

import org.hulyam.repository.KisiRepository;
import org.hulyam.repository.entity.Kisi;
import org.hulyam.utility.MyFactoryService;

public class KisiService extends MyFactoryService<KisiRepository, Kisi, Integer> {
    public KisiService() {
        super(new KisiRepository());
    }

    public boolean tcVarMi(String tc) {
        return getRepository().tcVarMi(tc);
    }
}
