package com.example.bases_para_logica_combinatoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/formulas")
public class ControllerFormula {

    @Autowired
    private RepositorioFormula repositorioFormula;

    @Autowired
	private RepositorioCombinador repositorioCombinador;

    @GetMapping
    public List<Formula> getFormulas() {
        return repositorioFormula.findAll();
    }

    @PostMapping
    public Formula novaFormula(@RequestBody FormulaDTO formulaDTO) {
		String resultadoStr = formulaDTO.getResultado();
		String formulaStr = formulaDTO.getFormula();
		String[] dependenciasStr = formulaDTO.getDependencias();

		Optional<Combinador> resultado = repositorioCombinador.findBySimbolo(resultadoStr);
		if (!resultado.isPresent()) {
			System.err.println("Combinador desconhecido: " + resultadoStr);
			return null;
		}

		ArrayList<Combinador> dependencias = new ArrayList<>(dependenciasStr.length);
		for (String dependencia : dependenciasStr) {
			Optional<Combinador> combinador = repositorioCombinador.findBySimbolo(dependencia);
			if (!combinador.isPresent()) {
				System.err.println("Combinador desconhecido: " + dependencia);
				return null;
			}

			dependencias.add(combinador.get());
		}

		Formula formula = new Formula();
		formula.setResultado(resultado.get());
		formula.setFormula(formulaStr);
		formula.setDependencias(dependencias);

        return repositorioFormula.save(formula);
    }
	
}
