//package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository;
//
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Game;
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.GameMongoDB;
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpaMongoDb.JpaGameRepositoryMongoDB;
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpaMongoDb.JpaGameRepositoryMongoDBAdapter;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
////@DataMongoTest
////@RunWith(SpringJunit5Runner.class)
////@RunWith(SpringRunner.class)
////@ExtendWith(MockitoExtension.class)
////@DataMongoTest
//@RunWith(MockitoJUnitRunner.class)
//public class RepositoryGameMongoDbTest {
//    @InjectMocks
//    private JpaGameRepositoryMongoDBAdapter underTestGameRepository;
//    @Mock
//    private JpaGameRepositoryMongoDB gameMongoDBRepositoryMock;
//
//
////    @Autowired
////    MongoTemplate mongoTemplate;
//
//    private final Game game1 = new Game("001",3,100);
//    private final GameMongoDB gameM1 = new GameMongoDB("001",3,100);
//    private final Game game2 = new Game("002",3,100);
//    private final GameMongoDB gameM2 = new GameMongoDB("002",3,100);
//
//    private final Game game3= new Game("003",2,102);
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
//    public void testFindByPlayerId() {
//        // Given
//        int playerId = 100;
//        List<Game> expectedList = Arrays.asList(game1, game2);
//        when(GameMongoDB.fromDomainModel(game1)).thenReturn(gameM1);
//        when(GameMongoDB.fromDomainModel(game2)).thenReturn(gameM2);
//        when(any(GameMongoDB.class).toDomainModel()).thenReturn(any(Game.class));
//
//        when(underTestGameRepository.findByPlayerId(playerId)).thenReturn(expectedList);
//
//        // When
//        List<Game> games = underTestGameRepository.findByPlayerId(playerId);
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
