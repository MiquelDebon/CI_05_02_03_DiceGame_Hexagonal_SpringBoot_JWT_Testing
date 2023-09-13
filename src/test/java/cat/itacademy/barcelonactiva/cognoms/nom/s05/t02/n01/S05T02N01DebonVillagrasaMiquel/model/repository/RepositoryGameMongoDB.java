//package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository;
//
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.S05T02N01DebonVillagrasaMiquelApplication;
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.GameMongoDB;
//import com.mongodb.BasicDBObjectBuilder;
//import com.mongodb.DBObject;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.After;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
////@DataMongoTest
////@RunWith(SpringJunit5Runner.class)
////@RunWith(SpringRunner.class)
//@DataMongoTest
//public class RepositoryGameMongoDB {
//    @Autowired
//    private IGameRepositoryMongoDB gameMongoDBRepository;
//
//
////    @Autowired
////    MongoTemplate mongoTemplate;
//
//    private GameMongoDB gameMongoDB1 = new GameMongoDB(3,100);
//    private GameMongoDB gameMongoDB2 = new GameMongoDB(3,100);;
//    private GameMongoDB gameMongoDB3 = new GameMongoDB(2,2);;
//
////    @BeforeEach
////    public void setUp(){
////        //Arrange
////        gameMongoDB1 = new GameMongoDB("001", 3,100);
////        gameMongoDB2 = new GameMongoDB( 3,1);
////        gameMongoDB3 = new GameMongoDB(2,2);
////    }
////
////    @AfterEach
////    public void tearDown(){
////        mongoTemplate.dropCollection("gameTest");
////    }
//
//    @Test
//    @DirtiesContext
//    public void testFindByPlayerId() {
//        // Given
//        int playerId = 120;
//        GameMongoDB game1 = new GameMongoDB(3, playerId);
//        GameMongoDB game2 = new GameMongoDB(3, playerId);
//        gameMongoDBRepository.save(game1);
//        gameMongoDBRepository.save(game2);
//
//        // When
//        List<GameMongoDB> games = gameMongoDBRepository.findByPlayerId(playerId);
//
//        // Then
//        Assertions.assertThat(games).hasSize(2);
//    }
//
//
//
////    @Test
////    public void gameMongoDbRepo_findById_ReturnMany(){
////        //Arrange
////        int idPlayer = 1;
////        List<GameMongoDB> listExpected = List.of(gameMongoDB1, gameMongoDB2);
//////        gameMongoDBRepository.save(gameMongoDB1);
////
////        //Act
////        List<GameMongoDB> listRetrieved = gameMongoDBRepository.findByPlayerId(idPlayer);
////
////        //Assert
////        Assertions.assertThat(listRetrieved).isNotEmpty();
////        log.info(listRetrieved.size() +  listRetrieved.toString());
////    }
//
////    @Test
////    public void gameMongoDbRepo_saveGameById() throws Exception{
////        //Arrange
////        int idPlayer = gameMongoDB1.getPlayerId();
////        mongoTemplate.save(gameMongoDB1, "gameTest");
////
////        //Act
////        List<GameMongoDB> listRetrieved = gameMongoDBRepository.findByPlayerId(idPlayer);
////
////        //Assert
////        Assertions.assertThat(listRetrieved).isNotEmpty();
////        log.info(listRetrieved.size() +  listRetrieved.toString());
////    }
//
//
////    @Test
////    public void gameMongoTemplate(){
////        mongoTemplate.save(gameMongoDB1, "gameTest");
////
////        List<GameMongoDB> listRetrieved = mongoTemplate.findAll(GameMongoDB.class, "gameTest");
////        Assertions.assertThat(listRetrieved).isNotEmpty();
////        mongoTemplate.dropCollection("gameTest");
////    }
//
//
//
////    @DisplayName("given object to save"
////            + " when save object using MongoDB template"
////            + " then object is saved")
////    @Test
////    public void test(@Autowired MongoTemplate mongoTemplate) {
////        // given
////        DBObject objectToSave = BasicDBObjectBuilder.start()
////                .add("key", "value")
////                .get();
////
////        GameMongoDB gameMongoDB1 = new GameMongoDB(3,1);
////        GameMongoDB gameMongoDB2 = new GameMongoDB(3,1);
////        List<GameMongoDB> listExpected = List.of(gameMongoDB1, gameMongoDB2);
////
////        // when
////        mongoTemplate.save(objectToSave, "collection");
////
////        gameMongoDBRepository.save(gameMongoDB1);
////
////        // then
////        List<GameMongoDB> listRetrieved = gameMongoDBRepository.findByPlayerId(1);
////        log.info(listRetrieved.size() +  listRetrieved.toString());
////        Assertions.assertThat(listRetrieved).isNotNull();
////
////
////        Assertions.assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
////                .containsOnly("value");
////    }
//
//
//
//
//}
