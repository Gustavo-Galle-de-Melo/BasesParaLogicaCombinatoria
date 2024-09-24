package com.example.bases_para_logica_combinatoria;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioCombinador extends JpaRepository<Combinador, Long> {
    Optional<Combinador> findBySimbolo(String simbolo);
}
