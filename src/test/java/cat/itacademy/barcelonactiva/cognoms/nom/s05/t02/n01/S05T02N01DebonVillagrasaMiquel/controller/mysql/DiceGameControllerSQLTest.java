package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.controller.mysql;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.EmptyDataBaseException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.PlayerGameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.request.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.rest.controller.DiceController;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.ExceptionHandler.ApiExceptionHandler;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.PlayerMySQL;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Role;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.services.PlayerGamerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DiceGameControllerSQLTest {

    //We need to fake HTTP requests. So we will auto-wire a MockMvc bean which Spring Boot autoconfigures.
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private DiceController underTestController;
    @Mock
    private PlayerGamerService service;
    @Autowired
    private ObjectMapper objectMapper;

    private PlayerMySQL player;
    private List<PlayerGameDto> listPlayerGameDto;
    private GameDto gameDTO;
    private PlayerGameDto playerGameDto;
    private List<GameDto> listGameDto;
    private RegisterRequest registerRequest;
    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(underTestController)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();

        player = PlayerMySQL.builder()
                .id(1)
                .name("Miquel")
                .surname("Debon")
                .role(Role.USER)
                .email("mdebonbcn@gmail.com")
                .password("Debon123Gts+")
                .registerDate(new Date().toString())
                .build();
        playerGameDto = PlayerGameDto.builder()
                .name("Miquel")
                .amountOfGames(0)
                .successRate("0 %")
                .averageMark(2).build();
        gameDTO = new GameDto(3);
        listPlayerGameDto = Arrays.asList(
                new PlayerGameDto("Miquel", 0,0,"0 %"),
                new PlayerGameDto( "Marta", 5, 0,"5 %"),
                new PlayerGameDto("Amelie", 10, 0,"10 %"));
        listGameDto = Arrays.asList(
                new GameDto(1),
                new GameDto(2),
                new GameDto(3));

        registerRequest = new RegisterRequest("Miquel", "Debon", "mdebonbcn@gmail.com", "passworD123+");
    }


    @Test
    public void diceController_playGame_returnGameDTO() throws Exception{

        given(service.saveGame(player.getId())).willReturn(gameDTO);
        mockMvc.perform(post("/players/{id}/games", player.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Mark").value(gameDTO.getMark()))
                .andDo(print());

        given(service.saveGame(player.getId())).willThrow(UserNotFoundException.class);
        mockMvc.perform(post("/players/{id}/games", player.getId()))
                .andExpect(status().isNotFound());
    }


    @Test
    public void diceController_getAllPlayers_returnListPlayerGameDTO() throws Exception{
        given(service.getAllPlayers()).willReturn(listPlayerGameDto);
        mockMvc.perform(get("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(listPlayerGameDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listPlayerGameDto.size()))
                .andExpect(jsonPath("$.[0].Name").value(listPlayerGameDto.get(0).getName()))
                .andDo(print());

        given(service.getAllPlayers()).willThrow(EmptyDataBaseException.class);
        mockMvc.perform(get("/players"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    public void diceController_getGamePlayer_returnListGameDTO() throws Exception{
        given(service.findGamesByPlayerId(player.getId())).willReturn(listGameDto);
        mockMvc.perform(get("/players/{id}", player.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(listGameDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()" ).value(listGameDto.size()))
                .andExpect(jsonPath("$.[0].Mark").value(listGameDto.get(0).getMark()))
                .andDo(print());

        given(service.findGamesByPlayerId(player.getId())).willThrow(UserNotFoundException.class);
        mockMvc.perform(get("/players/{id}", player.getId()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void diceController_deleteGameById_returnUpdatedPlayerGameDTO() throws Exception{
        given(service.findPlayerById(player.getId())).willReturn(playerGameDto);
        mockMvc.perform(delete("/players/{id}/games",player.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(playerGameDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name" ).value(playerGameDto.getName()))
                .andDo(print());

        given(service.findPlayerById(player.getId())).willThrow(UserNotFoundException.class);
        mockMvc.perform(delete("/players/{id}/games", player.getId()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void diceController_getRankingPlayers_returnListPlayerByRanking() throws Exception{
        given(service.getAllPlayersRanking()).willReturn(listPlayerGameDto);
        mockMvc.perform(get("/players/ranking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(listPlayerGameDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listPlayerGameDto.size()))
                .andDo(print());

        given(service.getAllPlayersRanking()).willThrow(EmptyDataBaseException.class);
        mockMvc.perform(get("/players/ranking"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    public void diceController_getWorstPlayer_returnPlayer() throws Exception{
        given(service.getWorstPlayer()).willReturn(playerGameDto);
        mockMvc.perform(get("/players/ranking/loser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(playerGameDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name" ).value(playerGameDto.getName()))
                .andExpect(jsonPath("$['Average mark']" ).value(playerGameDto.getAverageMark()))
                .andExpect(jsonPath("$['Amount of games']" ).value(playerGameDto.getAmountOfGames()))
                .andDo(print());

        given(service.getWorstPlayer()).willThrow(EmptyDataBaseException.class);
        mockMvc.perform(get("/players/ranking/loser"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void diceController_getBestPlayer_returnPlayer() throws Exception{
        given(service.getBestPlayer()).willReturn(playerGameDto);
        mockMvc.perform(get("/players/ranking/winner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(playerGameDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name" ).value(playerGameDto.getName()))
                .andExpect(jsonPath("$['Average mark']" ).value(playerGameDto.getAverageMark()))
                .andExpect(jsonPath("$['Amount of games']" ).value(playerGameDto.getAmountOfGames()))
                .andDo(print());

        given(service.getBestPlayer()).willThrow(EmptyDataBaseException.class);
        mockMvc.perform(get("/players/ranking/winner"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    public void diceController_getAverageTotalMark_returnAverageMark() throws Exception{
        given(service.averageTotalMarks()).willReturn(5d);
        mockMvc.perform(get("/players/totalAverageMark")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(Double.class)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5));
    }
    @Test
    public void diceController_getAverageTotalMark_returnEmptyException() throws Exception{
        given(service.averageTotalMarks()).willThrow(EmptyDataBaseException.class);
        mockMvc.perform(get("/players/totalAverageMark"))

                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    public void diceController_updatePlayer_returnUpdatedPlayerDTO() throws Exception{
        given(service.updatePlayer(registerRequest, player.getId())).willReturn(playerGameDto);
        mockMvc.perform(put("/players/{id}", player.getId(), registerRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(registerRequest)))

                .andExpect(status().isOk());
    }


}
