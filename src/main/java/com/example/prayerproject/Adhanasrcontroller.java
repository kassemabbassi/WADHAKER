package com.example.prayerproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Adhanasrcontroller {
    @FXML
    Button stopbutton;


    private Player mp3Player;

    private int timeseconds=207;
    @FXML
    public  void initialize()
    {
        playMP3("lib/adhandhohr.mp3");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (timeseconds > 0) {
                timeseconds--;
            } else {
                // Fermer le lecteur MP3 si encore actif
                if (mp3Player != null) {
                    mp3Player.close();
                }

                // Fermer la fenêtre
                Stage stage = (Stage) stopbutton.getScene().getWindow(); // Utiliser un bouton ou autre élément pour obtenir la scène
                stage.close();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Répéter jusqu'à ce que le temps soit écoulé
        timeline.play(); // Lancer le compteur
    }

    private void playMP3(String filePath) {
        byte[] audioData = readFileToByteArray(filePath);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData)) {
            mp3Player = new Player(byteArrayInputStream);

            // Lecture dans un nouveau thread pour ne pas bloquer l'interface utilisateur
            new Thread(() -> {
                try {
                    mp3Player.play();
                } catch (JavaLayerException ex) {
                    ex.printStackTrace();
                }
            }).start();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour lire un fichier en tableau d'octets
    private byte[] readFileToByteArray(String filePath) {
        File file = new File(filePath);
        byte[] data = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    // Méthode pour arrêter la lecture et fermer la fenêtre
    @FXML
    private void stopAndCloseWindow(ActionEvent event) {
        if (mp3Player != null) {
            mp3Player.close();  // Arrêter la lecture du MP3
        }

        // Fermer la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
