package org.hulyam.repository;

import org.hulyam.repository.entity.Kiralama;
import org.hulyam.utility.MyFactoryRepository;

public class KiralamaRepository extends MyFactoryRepository<Kiralama, Integer> {
    public KiralamaRepository() {
        super(new Kiralama());
    }
}
