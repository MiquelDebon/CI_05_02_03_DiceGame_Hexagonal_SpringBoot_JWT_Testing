package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.config;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.services.PlayerGameService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpaMongoDb.JpaGameRepositoryMongoDBAdapter;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpqMySql.JpaPlayerRepositoryMySqlAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public PlayerGameService playerGamerService(
            JpaGameRepositoryMongoDBAdapter jpaGameRepository,
            JpaPlayerRepositoryMySqlAdapter jpaPlayerRepository,
            PasswordEncoder passwordEncoder) {
        return new PlayerGameService(
                jpaGameRepository,
                jpaPlayerRepository,
                passwordEncoder);
    }


}
