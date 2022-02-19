package guru.springframework.petclinic.repositories;

import guru.springframework.petclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PerRepository extends CrudRepository<Pet, Long> {
}
