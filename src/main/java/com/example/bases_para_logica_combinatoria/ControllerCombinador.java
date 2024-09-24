package com.example.bases_para_logica_combinatoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combinadores")
public class ControllerCombinador {

    @Autowired
    private RepositorioCombinador repositorioCombinador;

    @GetMapping
    public List<Combinador> getCombinadores() {
        return repositorioCombinador.findAll();
    }

    @PostMapping
    public Combinador novoCombinador(@RequestBody Combinador combinador) {
        return repositorioCombinador.save(combinador);
    }
	
}
