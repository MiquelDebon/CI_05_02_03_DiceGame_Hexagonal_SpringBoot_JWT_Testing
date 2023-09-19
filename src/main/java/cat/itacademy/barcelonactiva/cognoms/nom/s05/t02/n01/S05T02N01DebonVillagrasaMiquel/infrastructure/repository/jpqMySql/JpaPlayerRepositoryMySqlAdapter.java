package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpqMySql;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.out.PlayerRepositoryPort;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.PlayerMySQL;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaPlayerRepositoryMySqlAdapter implements PlayerRepositoryPort {

    private JpaPlayerRepositoryMySQL playerRepositoryMySQL;

    public JpaPlayerRepositoryMySqlAdapter(JpaPlayerRepositoryMySQL jpaPlayerRepositoryMySQL) {
        this.playerRepositoryMySQL = jpaPlayerRepositoryMySQL;
    }

    @Override
    public List<Player> findAll() {
        return playerRepositoryMySQL.findAll().stream()
                .map(PlayerMySQL::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Player> findById(int id) {
        return playerRepositoryMySQL.findById(id)
                .map(PlayerMySQL::toDomainModel);
    }

    @Override
    public void save(Player player) {
        playerRepositoryMySQL.save(PlayerMySQL.fromDomainModel(player));
    }

    @Override
    public Boolean existsById(int id) {
        return playerRepositoryMySQL.existsById(id);
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        return playerRepositoryMySQL.findByEmail(email)
                .map(PlayerMySQL::toDomainModel);
    }


}
