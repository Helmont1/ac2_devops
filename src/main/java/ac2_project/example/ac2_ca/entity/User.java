package ac2_project.example.ac2_ca.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "tb_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    @Embedded
    private Plano plano;

    private String username;

    @Embedded
    private User_Email email;

    private Long moedas;

    private Long vouchers;

    private Long qtdCursosDisponiveis;
}