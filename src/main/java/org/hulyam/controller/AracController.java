package org.hulyam.controller;

import org.hulyam.repository.entity.Arac;
import org.hulyam.repository.entity.EAracKiralamaDurum;
import org.hulyam.repository.entity.EAracMarkaModel;
import org.hulyam.repository.entity.EEntityState;
import org.hulyam.service.AracService;

import java.util.List;

public class AracController {
    AracService aracService;

    public AracController() {
        this.aracService = new AracService();
    }

    public Arac aracOlustur(String plaka, EAracMarkaModel markaModel, Double kiralamaBedeli) {
        String plakaKucukseBuyuk = plaka.toUpperCase().trim();
        Arac arac = null;
        boolean plakaVarMi = aracService.plakaVarMi(plakaKucukseBuyuk);
        if (plakaVarMi) {
            System.err.println("|-------------------------------------------------|");
            System.err.println("| Bu plaka ile (" + plakaKucukseBuyuk + ") bir arac zaten kayitli! |");
            System.err.println("| Arac kaydedilememistir.                         |");
            System.err.println("|-------------------------------------------------|");
        } else {
            arac = aracService.save(Arac.builder().plaka(plaka).markamodel(markaModel).kiralamabedeli(kiralamaBedeli).kiralamaDurum(EAracKiralamaDurum.KIRALANABILIR).state(EEntityState.ACTIVE).createdon(System.currentTimeMillis()).build());
            System.out.println("|----------------------------------------------------|");
            System.out.println("| Arac sisteme basarili bir sekilde kaydedilmistir.  |");
            System.out.println("|----------------------------------------------------|");
            System.out.println(arac);
        }
        return arac;
    }

    public Arac plakayaGoreBul(String secilenPlaka) {
        return aracService.plakayaGoreBul(secilenPlaka);
    }

    public void modeleGoreBul(EAracMarkaModel eAracMarkaModel) {
        aracService.modeleGoreBul(eAracMarkaModel);
    }

    public List<Arac> findAll() {
        return aracService.findAll();
    }

    public Arac update(Arac arac) {
        return aracService.update(arac);
    }


}
