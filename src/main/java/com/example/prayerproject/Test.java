package com.example.prayerproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX MP3 Player");

        // Créez un bouton pour lire le MP3
        Button playButton = new Button("Play MP3");

        // Mise en page
        VBox vbox = new VBox(playButton);
        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Démarrer la lecture du MP3 automatiquement au démarrage
        playMP3("lib/adhansobh.mp3"); // Spécifiez le chemin correct vers votre fichier MP3
    }

    // Méthode pour lire un fichier MP3
    private void playMP3(String filePath) {
        byte[] audioData = readFileToByteArray(filePath);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData)) {
            Player mp3Player = new Player(byteArrayInputStream);

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

    public static void main(String[] args) {
        launch(args);
    }
}
