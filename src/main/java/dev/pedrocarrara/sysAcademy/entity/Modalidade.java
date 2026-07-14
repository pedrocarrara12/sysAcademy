package dev.pedrocarrara.sysAcademy.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modalidades")
public class Modalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Column(nullable = false)
    private Boolean ativa;

    @OneToMany(mappedBy = "modalidade", fetch = FetchType.LAZY)
    List<Graduacao> listaGraduacao = new ArrayList<>();

    public Modalidade() {
    }

    public Modalidade(String nome, Boolean ativa) {
        this.nome = nome;
        this.ativa = ativa;
    }

    @PrePersist
    public void prePersist() {
        if (ativa == null) {
            ativa = true;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }
}
