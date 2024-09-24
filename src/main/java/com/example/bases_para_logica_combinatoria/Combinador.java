package com.example.bases_para_logica_combinatoria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

// Cada instancia desta classe equivale a uma 'letra'
// Ex.: B, C, K, W, S

@Entity
public class Combinador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String simbolo;

    public Long getId() {
        return id;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

	@Override
	public String toString() {
		return simbolo;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Combinador)) {
			return false;
		}
		Combinador c = (Combinador) o;
		return simbolo.equals(c.simbolo);
	}
}
