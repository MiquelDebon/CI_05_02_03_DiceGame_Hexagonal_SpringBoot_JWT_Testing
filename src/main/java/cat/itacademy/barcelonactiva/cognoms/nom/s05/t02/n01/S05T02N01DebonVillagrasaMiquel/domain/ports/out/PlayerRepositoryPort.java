package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.out;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Player;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface PlayerRepositoryPort {
    List<Player> findAll();
    Optional<Player> findById(int id);
    void save(Player player);
    Boolean existsById(int id);
    Optional<Player> findByEmail(String email);

}
