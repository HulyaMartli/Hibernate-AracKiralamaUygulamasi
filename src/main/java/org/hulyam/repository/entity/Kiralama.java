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
@Table(name = "tblkiralama")
public class Kiralama extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer sure; //kaç günlüğüne kiralanacak
    Long kiralamatarihi;
    Double toplamtutar;
    @ManyToOne
    Kisi kisi;
    @ManyToOne
    Arac arac;
}
