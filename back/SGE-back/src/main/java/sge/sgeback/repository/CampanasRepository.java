package sge.sgeback.repository;

import org.springframework.data.repository.CrudRepository;
import sge.sgeback.model.Campanas;

import java.util.Optional;

public interface CampanasRepository extends CrudRepository<Campanas, Integer> {

    Optional<Campanas> findByNome (String nome);
}
