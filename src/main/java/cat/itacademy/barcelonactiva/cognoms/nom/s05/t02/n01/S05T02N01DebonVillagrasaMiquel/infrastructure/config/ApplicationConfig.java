package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.config;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.security.AuthenticationService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.services.PlayerGamerService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpaMongoDb.JpaGameRepositoryMongoDBAdapter;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpqMySql.JpaPlayerRepositoryMySqlAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public PlayerGamerService playerGamerService(
            JpaGameRepositoryMongoDBAdapter jpaGameRepository,
            JpaPlayerRepositoryMySqlAdapter jpaPlayerRepository,
            PasswordEncoder passwordEncoder) {
        return new PlayerGamerService(
                jpaGameRepository,
                jpaPlayerRepository,
                passwordEncoder);
    }


}
