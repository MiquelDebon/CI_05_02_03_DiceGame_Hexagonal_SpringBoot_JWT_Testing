package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpaMongoDb;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.out.GameRepositoryPort;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.GameMongoDB;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaGameRepositoryMongoDBAdapter implements GameRepositoryPort {

    private JpaGameRepositoryMongoDB jpaGameRepositoryMongoDB;

    public JpaGameRepositoryMongoDBAdapter(JpaGameRepositoryMongoDB jpaGameRepositoryMongoDB) {
        this.jpaGameRepositoryMongoDB = jpaGameRepositoryMongoDB;
    }

    @Override
    public List<Game> findAll() {
        return jpaGameRepositoryMongoDB.findAll().stream()
                .map(GameMongoDB::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Game> findByPlayerId(int id) {
        return jpaGameRepositoryMongoDB.findByPlayerId(id).stream()
                .map(GameMongoDB::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Game save(Game game) {
        return jpaGameRepositoryMongoDB.save(GameMongoDB.fromDomainModel(game))
                .toDomainModel();
    }

    @Override
    public List<Game> deleteByPlayerId(int id) {
        return jpaGameRepositoryMongoDB.deleteByPlayerId(id).stream()
                .map(GameMongoDB::toDomainModel)
                .collect(Collectors.toList());
    }
}
