package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@Entity
@Table(name="countries")
@NoArgsConstructor
@AllArgsConstructor
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    String code;
    String name;


}
