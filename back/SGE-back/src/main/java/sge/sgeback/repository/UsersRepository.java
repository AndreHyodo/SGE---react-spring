package sge.sgeback.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import sge.sgeback.model.Causais;
import sge.sgeback.model.Users;

public interface UsersRepository extends CrudRepository<Users, Integer> {



}