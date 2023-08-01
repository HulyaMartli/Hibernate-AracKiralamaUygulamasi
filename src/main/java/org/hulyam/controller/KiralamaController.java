package org.hulyam.controller;

import org.hulyam.repository.entity.*;
import org.hulyam.service.KiralamaService;

public class KiralamaController {
    KiralamaService kiralamaService;

    public KiralamaController() {
        this.kiralamaService = new KiralamaService();
    }

    public Kiralama kiralamaOlustur(int sure, Kisi kisi, Arac arac) {
        Double toplamtutar = sure * arac.getKiralamabedeli();
        Kiralama kiralama = kiralamaService.save(Kiralama.builder().sure(sure).kiralamatarihi(System.currentTimeMillis())
                .kisi(kisi).arac(arac)
                .toplamtutar(toplamtutar)
                .state(EEntityState.ACTIVE).createdon(System.currentTimeMillis()).build());
        System.out.println("|--------------------------------------------------------------|");
        System.out.println("| Kiralama kaydi sisteme basarili bir sekilde kaydedilmistir.  |");
        System.out.println("|--------------------------------------------------------------|");
        System.out.println(kiralama);
        return kiralama;

    }


}
