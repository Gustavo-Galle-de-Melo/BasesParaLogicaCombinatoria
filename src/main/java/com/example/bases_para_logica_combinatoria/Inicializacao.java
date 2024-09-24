package com.example.bases_para_logica_combinatoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

// Inicializa o banco de dados com alguns dos exemplos mais conhecidos

@Component
public class Inicializacao implements CommandLineRunner {

    @Autowired
    private RepositorioCombinador repositorioCombinador;

    @Autowired
    private RepositorioFormula repositorioFormula;

    @Autowired
    private RepositorioInvariante repositorioInvariante;

	private static final String[] combinadores = {"B", "C", "I", "K", "S", "W", "Y"};

	private static final String[][] formulas = {
		{"B", "S (K S) K", "K S"},
		{"C", "S (S (K S) (S (K K) S)) (K K)", "K S"},
		{"I", "B C C", "B C"},
		{"I", "S K K", "K S"},
		{"I", "W K", "K W"},
		{"S", "B (B W) (B B C)", "B C W"},
		{"W", "S S (K (S K K))", "K S"},
		{"Y", "B (W W) (B W (B B B))", "B W"},
	};

	private static final String[][] invariantes = {
		{"Impossivel deletar", "K"},
		{"Impossivel duplicar", "S W Y"},
		{"Impossivel mover", "C S"},
		{"Impossivel inserir parenteses", "B S"},
	};

    @Override
    public void run(String... args) throws Exception {

		// Cria combinadores
		for (String simbolo : combinadores) {
			Combinador combinador = new Combinador();
			combinador.setSimbolo(simbolo);
			repositorioCombinador.save(combinador);
		}

		// Adiciona formulas
		for (String[] formulaDTO : formulas) {
			Combinador resultado = repositorioCombinador.findBySimbolo(formulaDTO[0]).get();
			String formulaStr = formulaDTO[1];
			String[] dependenciasStr = formulaDTO[2].split(" ");
			ArrayList<Combinador> dependencias = new ArrayList<>(dependenciasStr.length);
			for (String dependencia : dependenciasStr) {
				dependencias.add(repositorioCombinador.findBySimbolo(dependencia).get());
			}

			Formula formula = new Formula();
			formula.setResultado(resultado);
			formula.setFormula(formulaStr);
			formula.setDependencias(dependencias);

			repositorioFormula.save(formula);
		}

		// Adiciona invariantes
		for (String[] invarianteDTO : invariantes) {
			String nome = invarianteDTO[0];
			String[] excecoesStr = invarianteDTO[1].split(" ");

			ArrayList<Combinador> excecoes = new ArrayList<>(excecoesStr.length);
			for (String excecao : excecoesStr) {
				excecoes.add(repositorioCombinador.findBySimbolo(excecao).get());
			}

			Invariante invariante = new Invariante();
			invariante.setNome(nome);
			invariante.setExcecoes(excecoes);

			repositorioInvariante.save(invariante);
		}
    }
}
