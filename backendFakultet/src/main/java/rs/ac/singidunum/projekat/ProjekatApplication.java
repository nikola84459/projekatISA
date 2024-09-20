package rs.ac.singidunum.projekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "rs.ac.singidunum.projekat.repositories")
@ComponentScan(basePackages = "rs.ac.singidunum.projekat.*")
@EntityScan("rs.ac.singidunum.projekat.models")
public class ProjekatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjekatApplication.class, args);
    }

}
