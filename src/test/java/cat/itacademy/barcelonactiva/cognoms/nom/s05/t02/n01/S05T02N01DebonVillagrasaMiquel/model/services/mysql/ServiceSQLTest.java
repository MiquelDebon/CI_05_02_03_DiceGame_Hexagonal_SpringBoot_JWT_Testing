package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.services.mysql;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.controller.auth.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.ExceptionHandler.customExceptions.EmptyDataBaseException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.ExceptionHandler.customExceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.dto.PlayerGameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.GameMongoDB;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.PlayerMySQL;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.Role;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository.IGameRepositoryMongoDB;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository.IplayerRepositoryMySQL;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.services.AuthenticationService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.services.PlayerGamerServiceImpl;
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
    private PlayerGamerServiceImpl underTestService;
    @Mock private IplayerRepositoryMySQL playerRepository;
    @Mock private IGameRepositoryMongoDB gameRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationService authenticationMySQLService;


    private PlayerMySQL player1;
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

        game1 = new GameMongoDB(1, player1.getId());
        game2 = new GameMongoDB(2, player1.getId());
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

        PlayerGameDTO playerReturned = underTestService.findPlayerDTOById(1);

        Assertions.assertThat(player1).isNotNull();
        Assertions.assertThat(player1.getId()).isEqualTo(playerReturned.getId());
    }

    @Test
    public void gameSQLService_finPlayerByPlayerId_ReturnException(){
        Mockito.when(playerRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            underTestService.findPlayerDTOById(anyInt());
        });
    }

    @Test
    public void playerSQLService_getAllPlayerDTO_ReturnListPlayersDTO(){
        Mockito.when(playerRepository.findAll()).thenReturn(listPlayerMySQL);

        List<PlayerGameDTO> actualList = underTestService.getAllPlayersDTO();

        Assertions.assertThat(actualList).isNotEmpty();
        Assertions.assertThat(actualList.size()).isEqualTo(3);
    }


    @Test
    public void playerSQLService_getAllPlayerDTO_ReturnException(){
        Mockito.when(playerRepository.findAll()).thenReturn(new ArrayList<PlayerMySQL>());
        assertThrows(EmptyDataBaseException.class, () -> {
            underTestService.getAllPlayersDTO();
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

        GameDTO actualGameDTO = underTestService.saveGame(1);

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

        List<PlayerGameDTO> actualList = underTestService.getAllPlayersDTORanking();

        Assertions.assertThat(actualList).isNotEmpty();
        Assertions.assertThat(actualList.size()).isEqualTo(3);
    }
    @Test
    public void playerSQLService_getAllPlayerDTORanking_ReturnException(){
        Mockito.when(playerRepository.findAll()).thenReturn(new ArrayList<PlayerMySQL>());

        assertThrows(EmptyDataBaseException.class, () -> {
            underTestService.getAllPlayersDTORanking();
        });
    }


    @Test
    public void gameSQLService_deleteGameByPlayerId_ReturnListGameDTO(){
        Mockito.when(playerRepository.findById(1)).thenReturn(Optional.of(player1));
        Mockito.when(gameRepository.deleteByPlayerId(1)).thenReturn(games);

        // Delete games
        List<GameDTO> deletedGames = underTestService.deleteGamesByPlayerId(1);

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

        PlayerGameDTO player = underTestService.getWorstPlayer();

        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getId()).isEqualTo(3);
        Assertions.assertThat(player.getClass()).isEqualTo(PlayerGameDTO.class);
    }

    @Test
    public void playerSQLService_GetBestPlayer_ReturnPlayerGameDTO(){
        Mockito.when(playerRepository.findAll()).thenReturn(listPlayerMySQL);

        PlayerGameDTO player = underTestService.getBestPlayer();

        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getId()).isEqualTo(1);
        Assertions.assertThat(player.getClass()).isEqualTo(PlayerGameDTO.class);
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

        PlayerGameDTO updatedPlayer = underTestService.updatePlayer(registerRequest, idPlayer);

        Assertions.assertThat(updatedPlayer.getName()).isEqualTo("Marta");
    }


}
