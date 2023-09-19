package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpqMySql;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.PlayerMySQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPlayerRepositoryMySQL extends JpaRepository<PlayerMySQL, Integer> {
    boolean existsByEmail(String email);
    Optional<PlayerMySQL> findByEmail(String email);

    UserDetails findByEmailAndPassword(String email, String password);
}
