package com.example.bases_para_logica_combinatoria;

public class FormulaDTO {
	private String resultado;
	private String formula;
	private String[] dependencias;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String[] getDependencias() {
        return dependencias;
    }

    public void setDependencias(String[] dependencias) {
        this.dependencias = dependencias;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

}
