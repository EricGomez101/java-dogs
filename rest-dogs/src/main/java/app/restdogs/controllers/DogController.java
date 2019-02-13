package app.restdogs.controllers;

import app.restdogs.DogNotFoundException;
import app.restdogs.models.Dog;
import app.restdogs.repository.DogRepository;
import app.restdogs.repository.DogResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/dogs")
public class DogController
{
    private final DogRepository dogrepo;
    private final DogResourceAssembler assembler;

    public DogController(DogRepository dogrepo, DogResourceAssembler assembler)
    {
        this.dogrepo = dogrepo;
        this.assembler = assembler;
    }

    @GetMapping("/breeds")
    public Resources<Resource<Dog>> all()
    {
        List<Resource<Dog>> dogs = dogrepo.findAllByOrderByBreedAsc().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(dogs,
                linkTo(methodOn(DogController.class).all()).withSelfRel());
    }

    @GetMapping("/weight")
    public Resources<Resource<Dog>> allByWeight()
    {
        List<Resource<Dog>> dogs = dogrepo.findAllByOrderByAvgWeightAsc().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());


        return new Resources<>(dogs,
                linkTo(methodOn(DogController.class).all()).withSelfRel());
    }

    @GetMapping("/apartment")
    public Resources<Resource<Dog>> allByApartment()
    {
        List<Resource<Dog>> dogs = dogrepo.findAllByAppropriate(true).stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(dogs,
                linkTo(methodOn(DogController.class).all()).withSelfRel());
    }
    @GetMapping("/breeds/{id}")
    public Resource<Dog> findOneById(@PathVariable Long id)
    {
        Dog dog = dogrepo.findById(id)
                .orElseThrow(() -> new DogNotFoundException(id));

        return assembler.toResource(dog);
    }

    @GetMapping("/breeds/{breed}")
    public Resource<Dog> findByBreed(@PathVariable String breed)
    {
        return assembler.toResource(dogrepo.findByBreedIgnoreCase(breed));
    }

    @PutMapping("/{id}")
    public Resource<Dog> updatedDog(@RequestBody Dog dog, @PathVariable Long id)
    {
        Dog updated = dogrepo.findById(id)
                .map(d ->
                {
                    d.setBreed(dog.getBreed());
                    d.setAvgWeight(dog.getAvgWeight());
                    d.setAppropriate(dog.isAppropriate());
                    return dogrepo.save(d);
                })
                .orElseThrow(() -> new DogNotFoundException(id));

        return assembler.toResource(updated);
    }

    @PostMapping("/")
    public void addDog(@RequestBody Dog dog)
    {
        if (dogrepo.findByBreedIgnoreCase(dog.getBreed()) == null)
        {
            dogrepo.save(dog);
        }
    }

    @DeleteMapping("/{id}")
    public void removeDog(@PathVariable Long id)
    {
        dogrepo.deleteById(id);
    }

    @DeleteMapping("/breeds/{breed}")
    public void removeDog(@PathVariable String breed)
    {
        Dog dog = dogrepo.findByBreedIgnoreCase(breed);
        dogrepo.delete(dog);
    }
}
