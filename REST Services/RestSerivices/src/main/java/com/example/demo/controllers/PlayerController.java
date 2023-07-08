package com.example.demo.controllers;

import com.example.demo.models.Player;
import com.example.demo.repositorys.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private PlayerRepository playerRepository;
    public PlayerController(PlayerRepository playersRepo){
        this.playerRepository = playersRepo;
    }
    @GetMapping
    public ResponseEntity getAllPlayers(){
        return ResponseEntity.ok(this.playerRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player savedPlayer = playerRepository.save(player);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlayer);
        return new ResponseEntity<>(
                savedPlayer, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable Long id, @RequestBody String name){
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if(optionalPlayer.isPresent()){
            Player player = optionalPlayer.get();
            player.setName(name);
            playerRepository.save(player);
            return new ResponseEntity<>(
                    "Player updated successsfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(
                "Player not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id){
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if(optionalPlayer.isPresent()){
            playerRepository.deleteById(id);
            return new ResponseEntity<>(
                    "Player deleted successsfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(
                "Player not found", HttpStatus.NOT_FOUND);
    }
}
