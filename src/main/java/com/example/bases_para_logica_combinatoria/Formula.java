package com.example.bases_para_logica_combinatoria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

// Ex: 'I = S K K'

@Entity
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Combinador resultado;
	private String formula;

    @ManyToMany
	private List<Combinador> dependencias;

    public Long getId() {
        return id;
    }

    public Combinador getResultado() {
        return resultado;
    }

    public void setResultado(Combinador resultado) {
        this.resultado = resultado;
    }

    public List<Combinador> getDependencias() {
        return dependencias;
    }

    public void setDependencias(List<Combinador> dependencias) {
        this.dependencias = dependencias;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

}
