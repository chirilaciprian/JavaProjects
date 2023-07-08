package com.example.Client;
import com.example.Client.models.Game;
import com.example.Client.models.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ClientApplication {

	static RestTemplate restTemplate = new RestTemplate();

	public static List<Player> getAllPlayers() {
		String url = "http://localhost:8080/players";
		ResponseEntity<Player[]> response = restTemplate.getForEntity(url, Player[].class);
		Player[] playersArray = response.getBody();
		return Arrays.asList(playersArray);
	}

	public static Player addPlayer(Player player) {
		String url = "http://localhost:8080/players";
		HttpEntity<Player> request = new HttpEntity<>(player);
		ResponseEntity<Player> response = restTemplate.postForEntity(url, request, Player.class);
		return response.getBody();
	}

	public static String updatePlayer(Long id, String name) {
		String url = "http://localhost:8080/players/" + id;
		HttpEntity<String> request = new HttpEntity<>(name);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
		return response.getBody();
	}

	public static void deletePlayer(Long id) {
		String url = "http://localhost:8080/players/" + id;
		restTemplate.delete(url);
	}

	public List<Game> getAllGames() {
		String url = "http://localhost:8080/players";
		ResponseEntity<Game[]> response = restTemplate.getForEntity(url, Game[].class);
		Game[] gamesArray = response.getBody();
		return Arrays.asList(gamesArray);
	}


	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
		addPlayer(new Player(21L,"Andrei"));
		getAllPlayers().forEach(p->{

		});
		updatePlayer(7L,"Cip");
		getAllPlayers().forEach(p->{
			System.out.println(p.getId() + " - " + p.getName());
		});
		System.out.println("\n");
		deletePlayer(1L);
		getAllPlayers().forEach(p->{
			System.out.println(p.getId() + " - " + p.getName());
		});
	}
}
