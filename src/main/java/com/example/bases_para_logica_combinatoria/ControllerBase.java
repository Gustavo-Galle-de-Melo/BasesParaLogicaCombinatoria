package com.example.bases_para_logica_combinatoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/base")
public class ControllerBase {

    @Autowired
	private RepositorioCombinador repositorioCombinador;

    @Autowired
    private RepositorioFormula repositorioFormula;

    @Autowired
    private RepositorioInvariante repositorioInvariante;
	
	// Testa se uma base eh turing completa ou nao, retornando uma prova
    @PostMapping
    public List<String> testaCombinadores(@RequestBody BaseDTO baseDTO) {
		
		// Converte os combinadores de List<String> para ArrayList<Combinador>
		ArrayList<Combinador> base = new ArrayList<>(baseDTO.getCombinadores().length);
		for (String simbolo : baseDTO.getCombinadores()) {

			Optional<Combinador> combinador = repositorioCombinador.findBySimbolo(simbolo);
			if (!combinador.isPresent()) {
				System.err.println("Combinador desconhecido: " + simbolo);
				return null;
			}

			base.add(combinador.get());
		}

		int totalCombinadores = repositorioCombinador.findAll().size();
		LinkedList<String> prova = new LinkedList<>();


		// Tenta aplicar as todas as formulas possiveis
		boolean formulaEncontrada;
		List<Formula> formulas = repositorioFormula.findAll();
		do {
			formulaEncontrada = false;
			for (Formula formula : formulas) {

				// Ignora formulas que encontrem combinadores conhecidos
				if (base.contains(formula.getResultado())) {
					continue;
				}

				// Se todas as dependencias ja foram encontradas, a formula pode ser usada
				if (base.containsAll(formula.getDependencias())) {
					formulaEncontrada = true;
					base.add(formula.getResultado());
					prova.add(formula.getResultado() + " = " + formula.getFormula());
				}
			}
		} while (formulaEncontrada);

		// Testa se a base encontrou todos os combinadores
		if (base.size() == totalCombinadores) {
			prova.add("Turing-completo");
			return prova;
		}

		// Testa se a base possui alguma invariante
		boolean invarianteEncontrada = false;
		for (Invariante invariante : repositorioInvariante.findAll()) {

			boolean contemExcecao = false;
			for (Combinador combinador : invariante.getExcecoes()) {

				if (base.contains(combinador)) {
					contemExcecao = true;
					break;
				}
			}

			if (!contemExcecao) {
				invarianteEncontrada = true;
				String lista = invariante.getExcecoes()
					.stream()
					.map(c -> c.getSimbolo())
					.collect(Collectors.joining(" "));
				prova.add(invariante.getNome() + " (implemente " + lista + ")");
			}
		}
		if (invarianteEncontrada) {
			return prova;
		}

		// Todos os testes falharam
		// Mais formulas e invariantes sao necesarias
		prova.add("???");
		return prova;
    }
}
