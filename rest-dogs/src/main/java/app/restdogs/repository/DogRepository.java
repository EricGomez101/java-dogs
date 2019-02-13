package app.restdogs.repository;

import app.restdogs.models.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long>
{
    List<Dog> findAllByOrderByBreedAsc();
    List<Dog> findAllByOrderByAvgWeightAsc();
    Dog findByBreedIgnoreCase(String breed);
    List<Dog> findAllByAppropriate(Boolean bool);
}
