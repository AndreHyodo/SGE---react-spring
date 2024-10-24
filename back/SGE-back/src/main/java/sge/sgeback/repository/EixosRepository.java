package sge.sgeback.repository;

import org.springframework.data.repository.CrudRepository;
import sge.sgeback.model.Campanas;
import sge.sgeback.model.Eixos;

import java.util.Optional;

public interface EixosRepository extends CrudRepository<Eixos, Integer> {

    Optional<Eixos> findByName (String nome);
}
