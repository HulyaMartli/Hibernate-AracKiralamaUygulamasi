package org.hulyam;

import org.hulyam.controller.AracController;
import org.hulyam.controller.KiralamaController;
import org.hulyam.controller.KisiController;
import org.hulyam.repository.entity.*;
import org.hulyam.utility.AracKiralamaUtility;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AracKiralamaApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AracController aracController = new AracController();
        KisiController kisiController = new KisiController();
        KiralamaController kiralamaController = new KiralamaController();

        int secim;
        // Bu array Arac markamodel secimlerinde kolaylik icin tanimlanmistir
        EAracMarkaModel[] modeller = EAracMarkaModel.values();

        // HAZIR VERILER - BASLANGIC
        /* !!! EGER HAZIR VERI ILE UYGULAMAYI TEST ETMEK ISTERSENIZ LUTFEN
         * ILK BASTA ASAGIDAKI KOD SATIRLARINI hibernate.cdh.xml "create-drop"da iken calistiriniz.
         * Daha sonra uygulamanin dogru calisabilmesi icin
         * asagidaki hazir veri kod satirlarini tekrar yoruma alip,
         * hibernate.cdh.xml "update"te iken uygulamayi kullaniniz.

         * * Şirketin var olan araçlarının veritabanına kaydedilmesi
         */
        AracKiralamaUtility.VTaraclariEkle();

        /* Şirketin var olan müşterilerinin/kişilerin veritabanına kaydedilmesi
         */
        AracKiralamaUtility.VTkisileriEkle();

        /* Bir örnek kiralama: 1 idli müşteriye 1 idli araç kiralanır
         */
        AracKiralamaUtility.VTkiralamaEkle();
        //HAZIR VERILER - BITIS


        // ARAC KIRALAMA UYGULAMA - BASLANGIC
        do {
            System.out.println();
            System.out.println("|---------------------------------|");
            System.out.println("|----------ARAC KIRALAMA----------|");
            System.out.println("|---------------------------------|");
            System.out.println("| 1 - Arac Ekle                   |");
            System.out.println("| 2 - Arac Arama                  |");
            System.out.println("| 3 - Kisi Ekleme                 |");
            System.out.println("| 4 - Kiralama                    |");
            System.out.println("| 5 - Raporlar                    |");
            System.out.println("| 0 - CIKIS                       |");
            System.out.println("|---------------------------------|");
            System.out.println("|---------------------------------|");
            secim = AracKiralamaUtility.intGirdiAl("Lutfen yukaridaki menuden seciminize\nkarsilik gelen numarayi giriniz:");

            switch (secim) {
                case 1:
                    // Sadece 2022 yapımı Volksvagen marka araçların belli modellerini kiralayan
                    // bir şirket olarak tasarlandı. Enum --> EAracMarkaModel

                    // plaka bilgisi
                    String plaka = AracKiralamaUtility.StringGirdiAl("Lutfen arada bosluk olmadan plaka giriniz (Orn: 06T123):");

                    // markamodel Secimi
                    Arrays.stream(EAracMarkaModel.values()).forEach(x -> System.out.println(x.ordinal() + 1 + "-" + x.name()));
                    int markamodelSecim = AracKiralamaUtility.intGirdiAl("Lutfen yukaridaki listede modele\nkarsilik gelen numarayi giriniz:") - 1;

                    // arac icin guncel kiralama bedeli giris
                    Double kiralamaBedeli = AracKiralamaUtility.DoubleGirdiAl("Lutfen guncel arac kiralama bedelini giriniz:");

                    // araç oluşturma
                    aracController.aracOlustur(plaka, modeller[markamodelSecim], kiralamaBedeli);

                    break;

                case 2:
                    int aramaSecim;
                    do {
                        System.out.println("|----------ARAC ARAMA----------|");
                        System.out.println("| 1 - Plakaya gore arac arama  |");
                        System.out.println("| 2 - Modele gore arac arama   |");
                        System.out.println("| 0 - ANA MENUYE DON           |");
                        System.out.println("|------------------------------|");
                        aramaSecim = AracKiralamaUtility.intGirdiAl("Lutfen arama tipine karsilik\ngelen numarayi giriniz:");
                        switch (aramaSecim) {
                            case 1:
                                String secilenPlaka = AracKiralamaUtility.StringGirdiAl("Lutfen arada bosluk olmadan plaka giriniz (Orn: 06T123):");
                                String plakaKucukseBuyuk = secilenPlaka.toUpperCase().trim();
                                aracController.plakayaGoreBul(plakaKucukseBuyuk);
                                break;
                            case 2:
                                Arrays.stream(EAracMarkaModel.values()).forEach(x -> System.out.println(x.ordinal() + 1 + "-" + x.name()));
                                int markamodel = AracKiralamaUtility.intGirdiAl("Lutfen yukaridaki listede modele\nkarsilik gelen numarayi giriniz:") - 1;
                                aracController.modeleGoreBul(modeller[markamodel]);
                        }
                    } while (aramaSecim != 0);


                    break;
                case 3:
                    System.out.println("|--------MUSTERI EKLEME--------|");
                    String ad = AracKiralamaUtility.StringGirdiAl("Lutfen kisi adi giriniz:");
                    String soyad = AracKiralamaUtility.StringGirdiAl("Lutfen kisi soyadi giriniz:");
                    String tc = AracKiralamaUtility.StringGirdiAl("Lutfen kisi TC giriniz:");
                    String tel = AracKiralamaUtility.StringGirdiAl("Lutfen kisi telefonu giriniz:");
                    kisiController.kisiOlustur(ad, soyad, tc, tel);
                    break;
                case 4:
                    System.out.println("|-----KIRALAMA KAYDI OLUSTUR-----|");
                    // KISI SECIMI
                    List<Kisi> kisiList = kisiController.findAll();
                    kisiList.forEach(x -> System.out.println(x.getId() + " - " + x.getAd() + " " + x.getSoyad() + " (Tc:" + x.getTc() + ")"));
                    int kisiid = AracKiralamaUtility.intGirdiAl("Lutfen yukaridaki kisi listesinden kiralama\nkaydi olusturacaginiz musteri idsini giriniz:");
                    Kisi kisi = kisiList.stream().filter(x -> x.getId() == kisiid).findFirst().get();

                    // ARAC SECIMI
                    System.out.println("|---MUSAIT / KIRALANABILIR ARACLAR---|");
                    List<Arac> aracList = aracController.findAll()
                            .stream().filter(x -> x.getKiralamaDurum() == EAracKiralamaDurum.KIRALANABILIR).toList();
                    aracList.forEach(x -> System.out.println(x.getId() + " - " + x.getPlaka() + " - " + x.getMarkamodel()));
                    int aracid = AracKiralamaUtility.intGirdiAl("Lutfen yukaridaki arac listesinden kiralama\nkaydi olusturacaginiz arac idsini giriniz:");
                    Arac arac = aracList.stream().filter(x -> x.getId() == aracid).findFirst().get();
                    int sure = AracKiralamaUtility.intGirdiAl("Kac gunlugune kiralama yapilacak?");
                    kiralamaController.kiralamaOlustur(sure, kisi, arac);
                    arac.setKiralamaDurum(EAracKiralamaDurum.KIRADA);
                    aracController.update(arac);
                    break;
                case 5:
                    int raporSecim;
                    do {
                        System.out.println("|--------------------RAPORLAR--------------------|");
                        System.out.println("| 1 - Su an kirada olan araclari listele         |");
                        System.out.println("| 2 - Su an bosta olan araclari listele          |");
                        System.out.println("| 3 - Musteriye gore kiralanan arabalari listele |");
                        System.out.println("| 0 - ANA MENUYE DON                             |");
                        System.out.println("|------------------------------------------------|");
                        raporSecim = AracKiralamaUtility.intGirdiAl("Lutfen goruntulemek istediginiz rapora\nkarsilik gelen numarayi giriniz:");

                        switch (raporSecim) {
                            case 1:
                                System.out.println("|---KIRADAKI ARACLAR---|");
                                List<Arac> kiradakiaraclar = aracController.findAll()
                                        .stream().filter(x -> x.getKiralamaDurum() == EAracKiralamaDurum.KIRADA).toList();
                                kiradakiaraclar.forEach(x -> System.out.println(x));
                                break;
                            case 2:
                                System.out.println("|---MUSAIT / KIRALANABILIR ARACLAR---|");
                                List<Arac> musaitaraclar = aracController.findAll()
                                        .stream().filter(x -> x.getKiralamaDurum() == EAracKiralamaDurum.KIRALANABILIR).toList();
                                musaitaraclar.forEach(x -> System.out.println(x));
                                break;
                            case 3:
                                List<Kisi> musterilistesi = kisiController.findAll();
                                musterilistesi.forEach(x -> System.out.println(x.getId() + " - " + x.getAd() + " " + x.getSoyad() + " (Tc:" + x.getTc() + ")"));
                                int secilenkisiid = AracKiralamaUtility.intGirdiAl("Lutfen yukaridaki kisi listesinden kiraladigi araclari\ngormek istediginiz musterinin idsini giriniz:");
                                Kisi secilenkisi = musterilistesi.stream().filter(x -> x.getId() == secilenkisiid).findFirst().get();
                                List<Kiralama> kiralamalar = secilenkisi.getKiralamalar();
                                for (Kiralama k : kiralamalar) {
                                    System.out.println(k.getArac());
                                }
                        }
                    } while (raporSecim != 0);

                    break;
            }

        } while (secim != 0);
        System.out.println("Sistemden çıkış başarılı!");

        // ARAC KIRALAMA UYGULAMA - BITIS

    }

}