package org.hulyam.utility;

import org.hulyam.controller.AracController;
import org.hulyam.controller.KiralamaController;
import org.hulyam.controller.KisiController;
import org.hulyam.repository.entity.Arac;
import org.hulyam.repository.entity.EAracKiralamaDurum;
import org.hulyam.repository.entity.EAracMarkaModel;
import org.hulyam.repository.entity.Kisi;

import java.util.List;
import java.util.Scanner;

public class AracKiralamaUtility {
    static AracController aracController;
    static KisiController kisiController;
    static KiralamaController kiralamaController;
    static Scanner scanner;

    /**
     * Kullanıcıdan int tipinde girdi almak için kullanılır
     *
     * @param girdiMesaji - kullanıcıya gösterilecek bilgi mesajı
     * @return int tipinde girdiyi döner
     */
    public static int intGirdiAl(String girdiMesaji) {
        scanner = new Scanner(System.in);
        System.out.println(girdiMesaji);
        return scanner.nextInt();
    }

    /**
     * Kullanıcıdan String tipinde girdi almak için kullanılır
     *
     * @param girdiMesaji - kullanıcıya gösterilecek bilgi mesajı
     * @return String tipinde girdiyi döner
     */
    public static String StringGirdiAl(String girdiMesaji) {
        scanner = new Scanner(System.in);
        System.out.println(girdiMesaji);
        return scanner.nextLine();
    }

    public static Double DoubleGirdiAl(String girdiMesaji) {
        scanner = new Scanner(System.in);
        System.out.println(girdiMesaji);
        return scanner.nextDouble();
    }

    /**
     * Önceden var olan araçları veritabanına ekler
     */
    public static void VTaraclariEkle() {
        aracController = new AracController();
        aracController.aracOlustur("06AK1234", EAracMarkaModel.VOLKSWAGEN_GOLF_2022, 500d);
        aracController.aracOlustur("06AK1235", EAracMarkaModel.VOLKSWAGEN_GOLF_2022, 500d);
        aracController.aracOlustur("06AK1236", EAracMarkaModel.VOLKSWAGEN_POLO_2022, 500d);
        aracController.aracOlustur("06AK1237", EAracMarkaModel.VOLKSWAGEN_PASSAT_VARIANT_2022, 500d);
        aracController.aracOlustur("06AK1238", EAracMarkaModel.VOLKSWAGEN_TIGUAN_2022, 500d);

        // AYNI PLAKA İLE YENİ KİŞİ KAYDEDİYOR MU KONTROLÜ
        aracController.aracOlustur("06AK1234", EAracMarkaModel.VOLKSWAGEN_GOLF_2022, 500d);
    }

    /**
     * Önceden var olan müşterileri/kişileri veritabanına ekler
     */
    public static void VTkisileriEkle() {
        kisiController = new KisiController();
        kisiController.kisiOlustur("Baris", "Demirci", "11111111111", "05551111111");
        kisiController.kisiOlustur("Hulya", "Martli", "21111111111", "05552111111");

        // AYNI TC İLE YENİ KİŞİ KAYDEDİYOR MU KONTROLÜ
        kisiController.kisiOlustur("Baris", "Demirci", "11111111111", "05551111111");

    }

    public static void VTkiralamaEkle() {
        kiralamaController = new KiralamaController();
        List<Kisi> kisiList = kisiController.findAll();
        Kisi kisi = kisiList.stream().filter(x -> x.getId() == 1).findFirst().get();
        List<Arac> aracList = aracController.findAll();
        Arac arac = aracList.stream().filter(x -> x.getId() == 1).findFirst().get();
        kiralamaController.kiralamaOlustur(3, kisi, arac);
        arac.setKiralamaDurum(EAracKiralamaDurum.KIRADA);
        aracController.update(arac);
    }
}
