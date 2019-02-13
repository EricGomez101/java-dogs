package app.restdogs;

import app.restdogs.models.Dog;
import app.restdogs.repository.DogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SeedDatabase
{
    @Bean
    public CommandLineRunner initDB(DogRepository dogrepo)
    {
        return args ->
        {
            log.info("Seeding " + dogrepo.save(new Dog("Springer", 50, false)));
            log.info("Seeding " + dogrepo.save(new Dog("Bulldog", 50, true)));
            log.info("Seeding " + dogrepo.save(new Dog("Collie", 50, false)));
            log.info("Seeding " + dogrepo.save(new Dog("Boston Terrie", 35, true)));
            log.info("Seeding " + dogrepo.save(new Dog("Corgie", 35, true)));
        };
    }
}
