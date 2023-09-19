package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpaMongoDb;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.GameMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaGameRepositoryMongoDB extends MongoRepository<GameMongoDB, Integer> {
    List<GameMongoDB> deleteByPlayerId(int id);
    List<GameMongoDB> findByPlayerId(int id);
}
