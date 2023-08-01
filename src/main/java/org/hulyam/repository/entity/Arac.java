package org.hulyam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblarac")
@NamedQueries({
        @NamedQuery(name = "Arac.plakaVarMi",
                query = "SELECT COUNT(a)>0 FROM Arac a WHERE plaka =: plakaparametre"),
        @NamedQuery(name = "Arac.plakayaGoreBul",
                query = "SELECT a FROM Arac a WHERE plaka =: plakaparametre"),
        @NamedQuery(name = "Arac.modeleGoreBul",
                query = "SELECT a FROM Arac a WHERE markamodel =: markamodelparametre")
})
public class Arac extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String plaka;
    Double kiralamabedeli;
    @Enumerated(EnumType.STRING)
    EAracMarkaModel markamodel;
    @Enumerated(EnumType.STRING)
    EAracKiralamaDurum kiralamaDurum;
}
