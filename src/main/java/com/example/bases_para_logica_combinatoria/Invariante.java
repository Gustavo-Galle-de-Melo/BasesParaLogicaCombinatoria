package com.example.bases_para_logica_combinatoria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

// Pelo menos uma excecao de cada invariante
// deve estar presente em cada base turing completa
// Ex.: A base BCW nao eh turing completa pois eh impossivel deletar

@Entity
public class Invariante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String nome;

    @ManyToMany
	private List<Combinador> excecoes;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Combinador> getExcecoes() {
        return excecoes;
    }

    public void setExcecoes(List<Combinador> excecoes) {
        this.excecoes = excecoes;
    }

}
