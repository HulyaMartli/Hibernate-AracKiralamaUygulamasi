package org.hulyam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblkisi")
@NamedQueries({
        @NamedQuery(name = "Kisi.tcVarMi",
                query = "SELECT COUNT(k)>0 FROM Kisi k WHERE tc =: tcparametre")
})
public class Kisi extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String ad;
    String soyad;
    String tc;
    String tel;
    @OneToMany(mappedBy = "kisi", cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Kiralama> kiralamalar;
}
