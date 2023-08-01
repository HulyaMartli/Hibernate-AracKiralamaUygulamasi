package org.hulyam.controller;

import org.hulyam.repository.entity.*;
import org.hulyam.service.KisiService;

import java.util.List;

public class KisiController {
    KisiService kisiService;

    public KisiController() {
        this.kisiService = new KisiService();
    }

    public Kisi kisiOlustur(String ad, String soyad, String tc, String tel) {
        Kisi kisi = null;
        boolean tcVarMi = kisiService.tcVarMi(tc);
        if (tcVarMi) {
            System.err.println("|-------------------------------------------------|");
            System.err.println("| Bu TC (" + tc + ") ile bir kisi zaten kayitli! |");
            System.err.println("| Kisi kaydedilememistir.                         |");
            System.err.println("|-------------------------------------------------|");
        } else {
            kisi = kisiService.save(Kisi.builder().ad(ad).soyad(soyad).tc(tc).tel(tel).state(EEntityState.ACTIVE).createdon(System.currentTimeMillis()).build());
            System.out.println("|------------------------------------------------------|");
            System.out.println("| Musteri sisteme basarili bir sekilde kaydedilmistir. |");
            System.out.println("|------------------------------------------------------|");
            System.out.println(kisi);
        }
        return kisi;
    }


    public List<Kisi> findAll() {
        return kisiService.findAll();
    }
}
