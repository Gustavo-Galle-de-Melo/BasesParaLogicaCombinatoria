package com.example.bases_para_logica_combinatoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/invariantes")
public class ControllerInvariante {

    @Autowired
    private RepositorioInvariante repositorioInvariante;
	
    @Autowired
	private RepositorioCombinador repositorioCombinador;

    @GetMapping
    public List<Invariante> getInvariantes() {
        return repositorioInvariante.findAll();
    }

    @PostMapping
    public Invariante novaFormula(@RequestBody InvarianteDTO invarianteDTO) {
		String nome = invarianteDTO.getNome();
		String[] excecoesStr = invarianteDTO.getExcecoes();

		ArrayList<Combinador> excecoes = new ArrayList<>(excecoesStr.length);
		for (String excecao : excecoesStr) {
			Optional<Combinador> combinador = repositorioCombinador.findBySimbolo(excecao);
			if (!combinador.isPresent()) {
				System.err.println("Combinador desconhecido: " + excecao);
				return null;
			}

			excecoes.add(combinador.get());
		}

		Invariante invariante = new Invariante();
		invariante.setNome(nome);
		invariante.setExcecoes(excecoes);

        return repositorioInvariante.save(invariante);
    }
	
}
