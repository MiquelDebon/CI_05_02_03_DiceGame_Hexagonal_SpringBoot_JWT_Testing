package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.config;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.security.AuthenticationService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.services.PlayerGamerService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpaMongoDb.JpaGameRepositoryMongoDBAdapter;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpqMySql.JpaPlayerRepositoryMySqlAdapter;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public PlayerGamerService playerGamerService(
            JpaGameRepositoryMongoDBAdapter jpaGameRepository,
            JpaPlayerRepositoryMySqlAdapter jpaPlayerRepository,
            AuthenticationService authenticationService,
            PasswordEncoder passwordEncoder) {
        return new PlayerGamerService(
                jpaGameRepository,
                jpaPlayerRepository,
                authenticationService,
                passwordEncoder);
    }

    @Bean
    public AuthenticationService authenticationService(
            JpaPlayerRepositoryMySqlAdapter jpaPlayerRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        return new AuthenticationService(
                jpaPlayerRepository,
                passwordEncoder,
                jwtService,
                authenticationManager);
    }





}
