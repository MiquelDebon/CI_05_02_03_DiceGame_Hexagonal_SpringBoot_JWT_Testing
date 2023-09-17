package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.controller.auth.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.ExceptionHandler.BaseDescriptionException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.ExceptionHandler.customExceptions.EmptyDataBaseException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.dto.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.dto.PlayerGameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.GameMongoDB;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.PlayerMySQL;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.ExceptionHandler.customExceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository.IGameRepositoryMongoDB;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository.IplayerRepositoryMySQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlayerGamerServiceImpl implements IPlayerGameService {

    @Autowired
    private IGameRepositoryMongoDB gameRepository;
    @Autowired
    private IplayerRepositoryMySQL playerRepositorySQL;
    @Autowired
    private AuthenticationService authenticationMySQLService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     *
     * 🔁 ----------  MAPPERS -----------
     *
     */

    public PlayerGameDTO playerDTOfromPlayer(PlayerMySQL player){
        return new PlayerGameDTO(player.getId(), player.getName(), player.getAverageMark(), (player.getSuccessRate() + " %"));
    }
    public GameDTO gameDTOfromGame(GameMongoDB game){
        return new GameDTO(game.getMark());
    }


    /**
     *
     *  ℹ️    ------- METHODS ----------------
     *
     */


    @Override
    public List<PlayerGameDTO> getAllPlayersDTO(){

        List<PlayerMySQL> playerMySQLList = playerRepositorySQL.findAll();
        if(playerMySQLList.size() > 0){
            return playerMySQLList.stream()
                    .map(p -> this.playerDTOfromPlayer(p))
                    .collect(Collectors.toList());
        }else{
            log.error(BaseDescriptionException.EMPTY_DATABASE);
            throw new EmptyDataBaseException();
        }

    }

    @Override
    public List<PlayerGameDTO> getAllPlayersDTORanking(){
        List<PlayerMySQL> playerMySQLList = playerRepositorySQL.findAll();
        if(playerMySQLList.size() > 0){
            return playerMySQLList.stream()
                    .sorted(Comparator.comparing(PlayerMySQL::getSuccessRate).reversed())
                    .map(p -> this.playerDTOfromPlayer(p))
                    .collect(Collectors.toList());
        }else{
            log.error(BaseDescriptionException.EMPTY_DATABASE);
            throw new EmptyDataBaseException();
        }
    }

    @Override
    public PlayerGameDTO updatePlayer(RegisterRequest updatedPlayer, int id){

        PlayerMySQL player = playerRepositorySQL.findById(id)
                .orElseThrow(UserNotFoundException::new);

        //Check the new name is not duplicated
        String currentName = player.getName();
        String updatedName = updatedPlayer.getFirstname();
        if(!currentName.equalsIgnoreCase(updatedName)){
            authenticationMySQLService.checkDuplicatedName(updatedPlayer.getFirstname());
            player.setName(updatedPlayer.getFirstname());
            playerRepositorySQL.save(player);
        }

        //Check the new email is not duplicated
        String currentEmail = player.getEmail();
        String updatedEmail = updatedPlayer.getEmail();
        if(!currentEmail.equalsIgnoreCase(updatedEmail)){
            authenticationMySQLService.checkDuplicatedEmail(updatedPlayer.getEmail());
            player.setEmail(updatedPlayer.getEmail());
            playerRepositorySQL.save(player);
            log.warn("Log out and log in again, otherwise the token will fail because the username won't match");
        }

        //Set the new values
        player.setSurname(updatedPlayer.getLastname());
        player.setPassword(passwordEncoder.encode(updatedPlayer.getPassword()));
        playerRepositorySQL.save(player);

        return this.playerDTOfromPlayer(player);
    }

    @Override
    public PlayerGameDTO findPlayerDTOById(int id){
        Optional<PlayerMySQL> player = playerRepositorySQL.findById(id);
        if(player.isPresent()){
            return this.playerDTOfromPlayer(player.get());
        }else{
            log.error(BaseDescriptionException.NO_USER_BY_THIS_ID);
            throw new UserNotFoundException(BaseDescriptionException.NO_USER_BY_THIS_ID);
        }
    }

    @Override
    public List<GameDTO> findGamesByPlayerId(int id){
        if(playerRepositorySQL.existsById(id)){
            return gameRepository.findByPlayerId(id).stream()
                    .map(this::gameDTOfromGame)
                    .collect(Collectors.toList());
        }else{
            log.error(BaseDescriptionException.NO_USER_BY_THIS_ID);
            throw new UserNotFoundException();
        }
    }

    @Override
    public GameDTO saveGame(int playerId){
        PlayerMySQL player = playerRepositorySQL.findById(playerId).
                orElseThrow(() -> new UserNotFoundException(BaseDescriptionException.NO_USER_BY_THIS_ID));
        int result = LogicGame.PLAY();

        GameMongoDB savedGame = gameRepository.save(new GameMongoDB(result, playerId));
        playerRepositorySQL.save(player.autoSetNewGamesRates(result));
        return gameDTOfromGame(savedGame);
    }

    @Override
    public List<GameDTO> deleteGamesByPlayerId(int id){
        PlayerMySQL player = playerRepositorySQL.findById(id).
                orElseThrow(() -> new UserNotFoundException());

        player.resetAllGamesRate();
        return gameRepository.deleteByPlayerId(id).stream()
                .map(this::gameDTOfromGame)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerGameDTO getWorstPlayer(){
        List<PlayerGameDTO> playersList = this.getAllPlayersDTORanking();
        return playersList.get(playersList.size()-1);
    }

    @Override
    public PlayerGameDTO getBestPlayer(){
        List<PlayerGameDTO> playersList = this.getAllPlayersDTORanking();
        return playersList.get(0);
    }


    @Override
    public Double averageTotalMarks(){
        return this.getAllPlayersDTO().stream()
                .mapToDouble(PlayerGameDTO::getAverageMark)
                .average().getAsDouble();
    }



}
