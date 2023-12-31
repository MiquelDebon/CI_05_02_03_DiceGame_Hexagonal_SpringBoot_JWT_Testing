package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.LogicGame;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.DuplicateUserEmailException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.DuplicateUserNameException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.EmptyDataBaseException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.PlayerGameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.mapperDto.MapperDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.request.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.out.GameRepositoryPort;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.in.PlayerGameUsesCasesPort;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.out.PlayerRepositoryPort;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.ExceptionHandler.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PlayerGameService implements PlayerGameUsesCasesPort {

    private GameRepositoryPort gameRepository;
    private PlayerRepositoryPort playerRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerGameService(
            GameRepositoryPort gameRepository,
            PlayerRepositoryPort playerRepository,
            PasswordEncoder passwordEncoder) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public List<PlayerGameDto> getAllPlayers(){

        List<Player> playerList = playerRepository.findAll();
        if(playerList.size() > 0){
            return playerList.stream()
                    .map(MapperDto::playerDtoFromPlayer)
                    .collect(Collectors.toList());
        }else{
            log.error(MessageException.EMPTY_DATABASE);
            throw new EmptyDataBaseException();
        }

    }

    @Override
    public List<PlayerGameDto> getAllPlayersRanking(){
        List<Player> playerList = playerRepository.findAll();
        if(playerList.size() > 0){
            return playerList.stream()
                    .sorted(Comparator.comparing(Player::getSuccessRate)
                    .reversed())
                    .map(MapperDto::playerDtoFromPlayer)
                    .collect(Collectors.toList());
        }else{
            log.error(MessageException.EMPTY_DATABASE);
            throw new EmptyDataBaseException();
        }
    }

    @Override
    public PlayerGameDto updatePlayer(RegisterRequest updatedPlayer, int id){

        Player player = playerRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        //Check the new name is not duplicated
        String currentName = player.getName();
        String updatedName = updatedPlayer.getFirstname();
        if(!currentName.equalsIgnoreCase(updatedName)){
            checkDuplicatedName(updatedPlayer.getFirstname());
            player.setName(updatedPlayer.getFirstname());
            playerRepository.save(player);
        }

        //Check the new email is not duplicated
        String currentEmail = player.getEmail();
        String updatedEmail = updatedPlayer.getEmail();
        if(!currentEmail.equalsIgnoreCase(updatedEmail)){
            checkDuplicatedEmail(updatedPlayer.getEmail());
            player.setEmail(updatedPlayer.getEmail());
            playerRepository.save(player);
            log.warn("Log out and log in again, otherwise the token will fail because the username won't match");
        }

        //Set the new values
        player.setSurname(updatedPlayer.getLastname());
        player.setPassword(passwordEncoder.encode(updatedPlayer.getPassword()));
        playerRepository.save(player);

        return MapperDto.playerDtoFromPlayer(player);
    }

    @Override
    public PlayerGameDto findPlayerById(int id){
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if(optionalPlayer.isPresent()){
            return MapperDto.playerDtoFromPlayer(optionalPlayer.get());
        }else{
            log.error(MessageException.NO_USER_BY_THIS_ID);
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<GameDto> findGamesByPlayerId(int id){
        if(playerRepository.existsById(id)){
            return gameRepository.findByPlayerId(id).stream()
                    .map(MapperDto::gameDtoFromGame)
                    .collect(Collectors.toList());
        }else{
            log.error(MessageException.NO_USER_BY_THIS_ID);
            throw new UserNotFoundException();
        }
    }

    @Override
    public GameDto saveGame(int playerId){
        Player player = playerRepository.findById(playerId).
                orElseThrow(() -> new UserNotFoundException());
        int result = LogicGame.PLAY();

        Game savedGame = gameRepository.save(new Game(result, playerId));
        playerRepository.save(player.autoSetNewGamesRates(result));
        return MapperDto.gameDtoFromGame(savedGame);
    }

    @Override
    public List<GameDto> deleteGamesByPlayerId(int id){
        Player player = playerRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException());

        player.resetAllGamesRate();
        return gameRepository.deleteByPlayerId(id).stream()
                .map(MapperDto::gameDtoFromGame)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerGameDto getWorstPlayer(){
        List<PlayerGameDto> playersList = this.getAllPlayersRanking();
        return playersList.get(playersList.size()-1);
    }

    @Override
    public PlayerGameDto getBestPlayer(){
        List<PlayerGameDto> playersList = this.getAllPlayersRanking();
        return playersList.get(0);
    }


    @Override
    public Double averageTotalMarks(){
        return this.getAllPlayers().stream()
                .mapToDouble(PlayerGameDto::getAverageMark)
                .average().getAsDouble();
    }


    public void checkDuplicatedEmail(String email){
        if(playerRepository.findAll()
                .stream().map(Player::getEmail)
                .anyMatch((n)-> n.equalsIgnoreCase(email))
        ){
            throw new DuplicateUserEmailException("Duplicated Email");
        }
    }

    public void checkDuplicatedName(String name){
        if (
                !name.equalsIgnoreCase("ANONYMOUS")
                        &&
                        playerRepository.findAll()
                                .stream().map(Player::getName)
                                .anyMatch((n)-> n.equalsIgnoreCase(name))
        ){
            throw new DuplicateUserNameException("Duplicated name");
        }
    }


}
