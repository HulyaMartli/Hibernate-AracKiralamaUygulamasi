package org.hulyam.service;

import org.hulyam.repository.KiralamaRepository;
import org.hulyam.repository.entity.Kiralama;
import org.hulyam.utility.MyFactoryService;

public class KiralamaService extends MyFactoryService<KiralamaRepository, Kiralama, Integer> {
    public KiralamaService() {
        super(new KiralamaRepository());
    }
}
