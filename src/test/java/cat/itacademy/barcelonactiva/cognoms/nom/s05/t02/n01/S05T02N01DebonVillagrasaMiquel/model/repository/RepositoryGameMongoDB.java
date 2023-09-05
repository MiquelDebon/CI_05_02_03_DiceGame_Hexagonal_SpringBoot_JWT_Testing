//package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository;
//
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.S05T02N01DebonVillagrasaMiquelApplication;
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.GameMongoDB;
//import com.mongodb.BasicDBObjectBuilder;
//import com.mongodb.DBObject;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.After;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//@Slf4j
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@DataMongoTest
//public class RepositoryGameMongoDB {
//    @Autowired
//    private IGameRepositoryMongoDB gameMongoDBRepository;
//
//
//    @DisplayName("given object to save"
//            + " when save object using MongoDB template"
//            + " then object is saved")
//    @Test
//    public void test(@Autowired MongoTemplate mongoTemplate) {
//        // given
//        DBObject objectToSave = BasicDBObjectBuilder.start()
//                .add("key", "value")
//                .get();
//
//        GameMongoDB gameMongoDB1 = new GameMongoDB(3,1);
//        GameMongoDB gameMongoDB2 = new GameMongoDB(3,1);
//        List<GameMongoDB> listExpected = List.of(gameMongoDB1, gameMongoDB2);
//
//        // when
//        mongoTemplate.save(objectToSave, "collection");
//
//        gameMongoDBRepository.save(gameMongoDB1);
//
//        // then
//        List<GameMongoDB> listRetrieved = gameMongoDBRepository.findByPlayerId(1);
//        log.info(listRetrieved.size() +  listRetrieved.toString());
//        Assertions.assertThat(listRetrieved).isNotNull();
//
//
//        Assertions.assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
//                .containsOnly("value");
//    }
//
//
//
//    @Autowired
//    private IGameRepositoryMongoDB gameMongoDBRepository;
//
//    private GameMongoDB gameMongoDB1;
//    private GameMongoDB gameMongoDB2;
//    private GameMongoDB gameMongoDB3;
//
//    @BeforeEach
//    public void setUp(){
//        //Arrange
//        gameMongoDB1 = new GameMongoDB(3,1);
//        gameMongoDB2 = new GameMongoDB(3,1);
//        gameMongoDB3 = new GameMongoDB(2,2);
//    }
//
//    @Test
//    public void gameMongoDbRepo_findById_ReturnMany(){
//        //Arrange
//        int idPlayer = 1;
//        List<GameMongoDB> listExpected = List.of(gameMongoDB1, gameMongoDB2);
//        gameMongoDBRepository.save(gameMongoDB1);
//
//        //Act
//        List<GameMongoDB> listRetrieved = gameMongoDBRepository.findByPlayerId(idPlayer);
//
//        //Assert
//        Assertions.assertThat(listRetrieved).isNotNull();
//        log.info(listRetrieved.size() +  listRetrieved.toString());
//    }
//
//
//}
