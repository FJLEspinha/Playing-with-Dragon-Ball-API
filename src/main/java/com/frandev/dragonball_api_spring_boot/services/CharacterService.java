package com.frandev.dragonball_api_spring_boot.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.frandev.dragonball_api_spring_boot.models.DragonBallAllCharacters;
import com.frandev.dragonball_api_spring_boot.models.DragonBallCharacter;

@Service
public class CharacterService {
    @Autowired
    RestTemplate restTemplate;

    public DragonBallCharacter getRandomCharacterFromAPI() {
        long[] noId = { 36, 41, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62 };

        long randomId = Math.round(Math.random() * 77) + 1;
        boolean find = Arrays.stream(noId).anyMatch(number -> number == randomId);

        if (find)
            return getRandomCharacterFromAPI();

        String url = "https://dragonball-api.com/api/characters/" + randomId;
        DragonBallCharacter characterDB = restTemplate.getForObject(url, DragonBallCharacter.class);
        return characterDB;
    }

    @SuppressWarnings("null")
    public ArrayList<DragonBallCharacter> getAllDBCharacters() {
        ArrayList<DragonBallCharacter> allDBCharacters = new ArrayList<DragonBallCharacter>();
        for (int i = 1; i <= 6; i++) {
            String url = "https://dragonball-api.com/api/characters?page=" + i;
            DragonBallAllCharacters dbAllCharacter = restTemplate.getForObject(url, DragonBallAllCharacters.class);
            allDBCharacters.addAll(dbAllCharacter.items);
        }
        return allDBCharacters;

    }

    public DragonBallCharacter getButton(Long id) {
        Long idButton = id;
        String url = "https://dragonball-api.com/api/characters/" + idButton;
        DragonBallCharacter characterDB = restTemplate.getForObject(url, DragonBallCharacter.class);
        return characterDB;
    }

    public ArrayList<DragonBallCharacter> getTeam() {
        ArrayList<DragonBallCharacter> team = new ArrayList<DragonBallCharacter>();
        for (int i = 0; i < 5; i++) {
            DragonBallCharacter seclectedCharactter = getRandomCharacterFromAPI();

            seclectedCharactter.ki = seclectedCharactter.ki.toLowerCase();
            if (seclectedCharactter.ki.contains("billion"))
                seclectedCharactter.ki = seclectedCharactter.ki.replace("billion", ".000.000.000.000");
            if (seclectedCharactter.ki.contains("septillion"))
                seclectedCharactter.ki = seclectedCharactter.ki.replace("septillion",
                        ".000.000.000.000.000.000.000.000.000.000.000.000.000.000");
            if (seclectedCharactter.ki.contains("quintillion"))
                seclectedCharactter.ki = seclectedCharactter.ki.replace("quintillion",
                        ".000.000.000.000.000.000.000.000.000.000");
            if (seclectedCharactter.ki.contains("quadrillion"))
                seclectedCharactter.ki = seclectedCharactter.ki.replace("quadrillion",
                        ".000.000.000.000.000.000.000.000");
            if (seclectedCharactter.ki.contains("trillion"))
                seclectedCharactter.ki = seclectedCharactter.ki.replace("trillion", ".000.000.000.000.000.000");
            if (seclectedCharactter.ki.contains("googolplex"))
                seclectedCharactter.ki = seclectedCharactter.ki.replace("googolplex", "1E100");
            if (seclectedCharactter.ki.contains("."))
                seclectedCharactter.ki = seclectedCharactter.ki.replace(".", "");
            if (seclectedCharactter.ki.contains(","))
                seclectedCharactter.ki = seclectedCharactter.ki.replace(",", "");
            if (seclectedCharactter.ki.contains(" "))
                seclectedCharactter.ki = seclectedCharactter.ki.replace(" ", "");

            team.add(seclectedCharactter);
        }
        return team;
    }

    public String[] letsFight(ArrayList<String> team1, ArrayList<String> team2) {

        BigInteger pointsTeam1 = BigInteger.ZERO;
        BigInteger pointsTeam2 = BigInteger.ZERO;
        String characterTeam1;
        String characterTeam2;

        String[] resultAndWinner = new String[3];

        for (int i = 0; i < 5; i++) {
            characterTeam1 = team1.get(i);

            if (!characterTeam1.contains("unknown")) {
                if (characterTeam1.contains("E")) {
                    BigDecimal intermediate = new BigDecimal(characterTeam1);
                    BigInteger transform = intermediate.toBigInteger();
                    pointsTeam1 = pointsTeam1.add(transform);
                } else {
                    BigInteger transform = new BigInteger(characterTeam1);
                    pointsTeam1 = pointsTeam1.add(transform);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            characterTeam2 = team2.get(i);
            if (!characterTeam2.contains("unknown")) {
                if (characterTeam2.contains("E")) {
                    BigDecimal intermediate = new BigDecimal(characterTeam2);
                    BigInteger transform = intermediate.toBigInteger();
                    pointsTeam2 = pointsTeam2.add(transform);

                } else {
                    BigInteger transform = new BigInteger(characterTeam2);
                    pointsTeam2 = pointsTeam2.add(transform);
                }
            }
        }
        resultAndWinner[0] = "Team1: " + pointsTeam1 + " points";
        resultAndWinner[1] = "Team2: " + pointsTeam2 + " points";
        resultAndWinner[2] = pointsTeam1.compareTo(pointsTeam2) > 0 ? "Winner Team1 with : " + pointsTeam1 + " points"
                : "Winner Team2 with : " + pointsTeam2 + " points";

        return resultAndWinner;

    }
}
