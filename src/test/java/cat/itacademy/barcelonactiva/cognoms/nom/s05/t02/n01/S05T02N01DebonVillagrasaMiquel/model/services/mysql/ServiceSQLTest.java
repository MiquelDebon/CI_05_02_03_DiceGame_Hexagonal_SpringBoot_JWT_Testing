package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.services.mysql;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.EmptyDataBaseException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.PlayerGameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.request.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.GameMongoDB;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.PlayerMySQL;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Role;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpaMongoDb.JpaGameRepositoryMongoDB;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.repository.jpqMySql.JpaPlayerRepositoryMySQL;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.services.PlayerGamerService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;

@Slf4j
@DisplayName("Service test")
@ExtendWith(MockitoExtension.class)
public class ServiceSQLTest {

    @InjectMocks
    private PlayerGamerService underTestService;
    @Mock private JpaPlayerRepositoryMySQL playerRepository;
    @Mock private JpaGameRepositoryMongoDB gameRepository;
    @Mock private PasswordEncoder passwordEncoder;


    private PlayerMySQL player1;
    private PlayerGameDto playerReturned;
    private GameMongoDB game1;
    private GameMongoDB game2;
    private List<GameMongoDB> games ;
    private List<PlayerMySQL> listPlayerMySQL;
    private RegisterRequest registerRequest;

    @BeforeEach
    public void setUp(){
        player1 = PlayerMySQL.builder()
                .id(1)
                .name("Miquel")
                .surname("Debon")
                .role(Role.USER)
                .email("mdebonbcn@gmail.com")
                .password("Debon123Gts+")
                .registerDate(new Date().toString())
                .build();
        playerReturned = PlayerGameDto.builder()
                .name("Miquel")
                .amountOfGames(0)
                .averageMark(0.0)
                .successRate("0 %").build();

        game1 = new GameMongoDB("01",1, player1.getId());
        game2 = new GameMongoDB("02", 2, player1.getId());
        games = Arrays.asList(game1, game2);

        listPlayerMySQL = Arrays.asList(
                new PlayerMySQL(1, "Miquel"),
                new PlayerMySQL(2, "Marta"),
                new PlayerMySQL(3, "Jorge"));

        registerRequest = new RegisterRequest("Marta", "Debon",
                "mdebonbcn@gmail.com", "passwordD123+");
    }


    @Test
    public void playerSQLService_findByID_ReturnPlayerDTO(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player1));

        PlayerGameDto playerReturned = underTestService.findPlayerById(1);

        Assertions.assertThat(playerReturned).isNotNull();
    }

    @Test
    public void gameSQLService_finPlayerByPlayerId_ReturnException(){
        Mockito.when(playerRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            underTestService.findPlayerById(anyInt());
        });
    }

    @Test
    public void playerSQLService_getAllPlayerDTO_ReturnListPlayersDTO(){
        Mockito.when(playerRepository.findAll()).thenReturn(listPlayerMySQL);

        List<PlayerGameDto> actualList = underTestService.getAllPlayers();

        Assertions.assertThat(actualList).isNotEmpty();
        Assertions.assertThat(actualList.size()).isEqualTo(3);
    }


    @Test
    public void playerSQLService_getAllPlayerDTO_ReturnException(){
        Mockito.when(playerRepository.findAll()).thenReturn(new ArrayList<PlayerMySQL>());
        assertThrows(EmptyDataBaseException.class, () -> {
            underTestService.getAllPlayers();
        });
    }


    @Test
    public void gamesSQLService_findGamesByPlayerId_ReturnGameList(){
        Mockito.when(playerRepository.existsById(1)).thenReturn(true);
        Mockito.when(gameRepository.findByPlayerId(1)).thenReturn(games);

        underTestService.findGamesByPlayerId(1);
        Assertions.assertThat(games.size()).isEqualTo(2);
        Assertions.assertThat(games).isNotEmpty();
    }

    @Test
    public void gamesSQLService_findGamesByPlayerId_returnException(){
        Mockito.when(playerRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () ->
                underTestService.findGamesByPlayerId(anyInt()));
    }


    @Test
    public void gameSQLService_saveGame_returnSavedGame(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player1));
        Mockito.when(gameRepository.save(any(GameMongoDB.class))).thenReturn(game1);

        GameDto actualGameDTO = underTestService.saveGame(1);

        Assertions.assertThat(actualGameDTO).isNotNull();
    }

    @Test
    public void gameSQLService_saveGame_returnUserNotFoundException(){
        Mockito.when(playerRepository.findById(1))
                .thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> {
            underTestService.saveGame(1);
        });

        Mockito.verify(gameRepository, never()).save(any());
    }

    @Test
    public void playerSQLService_getAllPlayerDTORanking_ReturnListPlayersDTO(){
        Mockito.when(playerRepository.findAll()).thenReturn(listPlayerMySQL);

        List<PlayerGameDto> actualList = underTestService.getAllPlayersRanking();

        Assertions.assertThat(actualList).isNotEmpty();
        Assertions.assertThat(actualList.size()).isEqualTo(3);
    }
    @Test
    public void playerSQLService_getAllPlayerDTORanking_ReturnException(){
        Mockito.when(playerRepository.findAll()).thenReturn(new ArrayList<PlayerMySQL>());

        assertThrows(EmptyDataBaseException.class, () -> {
            underTestService.getAllPlayersRanking();
        });
    }


    @Test
    public void gameSQLService_deleteGameByPlayerId_ReturnListGameDTO(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player1));
        Mockito.when(gameRepository.deleteByPlayerId(1)).thenReturn(games);

        // Delete games
        List<GameDto> deletedGames = underTestService.deleteGamesByPlayerId(1);

        // Verify
        Assertions.assertThat(deletedGames.size()).isEqualTo(2);
        Mockito.verify(gameRepository, Mockito.times(1)).deleteByPlayerId(1);
    }
    @Test
    public void gameSQLService_deleteGamesByPlayerId_returnException(){
        Mockito.when(playerRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                ()-> underTestService.deleteGamesByPlayerId(anyInt()));
    }


    @Test
    public void playerSQLService_GetWorstPlayer_ReturnPlayerGameDTO(){
        Mockito.when(playerRepository.findAll()).thenReturn(listPlayerMySQL);

        PlayerGameDto player = underTestService.getWorstPlayer();

        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getClass()).isEqualTo(PlayerGameDto.class);
    }

    @Test
    public void playerSQLService_GetBestPlayer_ReturnPlayerGameDTO(){
        Mockito.when(playerRepository.findAll()).thenReturn(listPlayerMySQL);

        PlayerGameDto player = underTestService.getBestPlayer();

        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getClass()).isEqualTo(PlayerGameDto.class);
    }

    @Test
    public void playerSQL_getAverageTotalMarks_returnDouble(){
        Mockito.when(playerRepository.findAll()).thenReturn(listPlayerMySQL);

        Double doubleValue = underTestService.averageTotalMarks();

        Assertions.assertThat(doubleValue).isNotNull();
    }

    @Test
    public void playerSQL_getAverageTotalMarks_returnEmptyDBException(){
        Mockito.when(playerRepository.findAll())
                .thenThrow(EmptyDataBaseException.class);

        assertThrows(EmptyDataBaseException.class,
                ()->underTestService.averageTotalMarks());
    }


    @Test
    public void playerSQLService_updatePlayer_ReturnUpdatedPlayer(){
        int idPlayer = 1;
        Mockito.when(playerRepository.findById(anyInt())).thenReturn(Optional.of(player1));
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(anyString());

        PlayerGameDto updatedPlayer = underTestService.updatePlayer(registerRequest, idPlayer);

        Assertions.assertThat(updatedPlayer.getName()).isEqualTo("Marta");
    }


}
