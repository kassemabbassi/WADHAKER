package com.example.prayerproject;


import javafx.animation.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;



public class HelloController {
    @FXML
     Label datelabel;

    @FXML
    private ComboBox<String> local;

    @FXML
    Label sobhtime;
    @FXML
    Label ichatime;
    @FXML
     Label dhohrtime;
    @FXML
    Label asrtime;
    @FXML
    Label mghrebtime;
    @FXML
    Label tempsrestant;
   /* public void openquranfile(ActionEvent event) {
        try {
            // Mettre l'interface courante en plein écran à false
           Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setFullScreen(false);

            // Charger le fichier quranfile.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("quranfile.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 1530, 800);

            // Configurer et afficher la nouvelle scène
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
   private static final LocalTime INITIAL_PRAYER_TIME = LocalTime.of(4, 20);
    private static final LocalTime initialtimeofmaghreb = LocalTime.of(18, 47);
    private static final LocalDate END_DATE = LocalDate.of(2024, 9, 30);

    private PrayerTimesLoader prayerTimesLoader;
    private PrayerTimesLoader prayerTimesLoaderofasr ;
    private PrayerTimesLoader prayerTimesLoaderofmghreb ;
    private PrayerTimesLoader prayerTimesLoaderoficha;

    LocalTime customTime = LocalTime.of(15,48); // Exemple : 12:50

    // Mettez à jour le label avec le temps prédéfini


    @FXML
    public void initialize()
    {
        local.getItems().addAll("Monastir", "Feriana");  // Ajouter les lieux disponibles


        local.setValue("Monastir");  // Lieu par défaut
        prayerTimesLoader = new PrayerTimesLoader(5,local.getValue());  // Charger les temps pour le lieu par défaut
        updateDhohrTimeLabel();
        prayerTimesLoaderofasr=new PrayerTimesLoader(5,6,local.getValue());
        updateasrTimeLabel();
        prayerTimesLoaderofmghreb=new PrayerTimesLoader(1,2,3,local.getValue());
        updatemghrebTimeLabel();
        prayerTimesLoaderoficha=new PrayerTimesLoader(1,2,3,4,local.getValue());
        updateichaTimeLabel();
        local.setOnAction(event -> {
            String selectedLocation = local.getValue();  // Obtenir le lieu sélectionné
            prayerTimesLoader = new PrayerTimesLoader(5,selectedLocation);  // Charger les nouveaux temps de prière
            updateDhohrTimeLabel();  // Mettre à jour le label avec la nouvelle heure
            prayerTimesLoaderofasr=new PrayerTimesLoader(5,6,selectedLocation);
            updateasrTimeLabel();
            prayerTimesLoaderofmghreb=new PrayerTimesLoader(1,2,3,selectedLocation);
            updatemghrebTimeLabel();
            prayerTimesLoaderoficha=new PrayerTimesLoader(1,2,3,4,selectedLocation);
            updateichaTimeLabel();
        });

        updateDateTime(datelabel);
        updateLabelWithTime(sobhtime, customTime);


        // Timeline pour mettre à jour l'heure chaque seconde
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateTime(datelabel)));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();
        //updatesobhTimeLabel();
        updateDhohrTimeLabel();
        updateasrTimeLabel();
        updatemghrebTimeLabel();
        updateichaTimeLabel();
        checksobhtime();
        checkDhohrtime();
        checkasrtime();
        checkmghrebtime();
        checkichatime();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(24 * 60 * 60 * 1000), event -> {
          //  updatesobhTimeLabel();
            updateDhohrTimeLabel();
            updateasrTimeLabel();
            updatemghrebTimeLabel();
            updateichaTimeLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        updateNextPrayerTime();
        //calculateRemainingTimebefore10();

    }
    private void updateLabelWithTime(Label label, LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Définir le format
        String formattedTime = time.format(formatter); // Formater le temps
        label.setText(formattedTime); // Mettre à jour le texte du label
    }
    private void updatesobhTimeLabel() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(2024, 9, 1); // Date de départ
        long daysElapsed = ChronoUnit.DAYS.between(startDate, today);

        // Incrémenter l'heure de prière
        LocalTime currentPrayerTime = INITIAL_PRAYER_TIME.plusMinutes(daysElapsed);

        // Vérifier si la date actuelle est avant ou après la date de fin
        if (!today.isAfter(END_DATE)) {
            sobhtime.setText( currentPrayerTime.toString());
        } else {
            sobhtime.setText("La période pour les heures de prière est terminée.");
        }
    }
    private void updatemghrebTimeLabel() {
        LocalDate today = LocalDate.now();
        LocalTime mghrebtime1 = prayerTimesLoaderofmghreb.getmghrebTimeForDate(today);

        // Mettre à jour le label avec l'heure de Dhohr
       mghrebtime.setText(mghrebtime1.toString());
    }

    private void updateDhohrTimeLabel() {
        LocalDate today = LocalDate.now();
        LocalTime dhohrTime = prayerTimesLoader.getDhohrTimeForDate(today);

        // Mettre à jour le label avec l'heure de Dhohr
        dhohrtime.setText(dhohrTime.toString());
    }

    private void updateichaTimeLabel() {
        LocalDate today = LocalDate.now();
        LocalTime dhohrTime = prayerTimesLoaderoficha.getichaTimeForDate(today);

        // Mettre à jour le label avec l'heure de Dhohr
        ichatime.setText(dhohrTime.toString());
    }
    private void updateasrTimeLabel() {
        LocalDate today = LocalDate.now();
        LocalTime dhohrTime = prayerTimesLoaderofasr.getasrTimeForDate(today);

        // Mettre à jour le label avec l'heure de Dhohr
        asrtime.setText(dhohrTime.toString());
    }
 /*  public void checksobhtime() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES); // Ignorer les secondes

            // Extraire l'heure de prière du label
            String sobhtimeText = sobhtime.getText();
            LocalTime timeofsobh = parseTimeFromLabel(sobhtimeText);

            // Debugging output
            System.out.println("Current time: " + currentTime);
            System.out.println("Time of Sobh: " + timeofsobh);

            if (currentTime.equals(timeofsobh.truncatedTo(java.time.temporal.ChronoUnit.MINUTES))) {
                try {
                    // Charger et afficher le fichier FXML
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adhansobh.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Quran File");
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(true); // Optionnel : Mettre en plein écran
                    stage.show();

                    // Démarrer la lecture du MP3
                    //playMP3("lib/adhansobh.mp3"); // Spécifiez le chemin correct vers votre fichier MP3

                    // Arrêter le Timeline après avoir joué le MP3
                    timeline.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }*/
 private boolean isAdhansobh = false;
    private boolean isAdhandhohr = false;
    private boolean isAdhanasr= false;
    private boolean isAdhanmghreb = false;
    private boolean isAdhanicha = false;

    // Nouveau drapeau

    public void checksobhtime() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES);

            String sobhtimeText = sobhtime.getText();
            LocalTime timeofsobh = parseTimeFromLabel(sobhtimeText);

            if (currentTime.equals(timeofsobh) && !isAdhansobh) {
                isAdhansobh = true; // Empêche l'ouverture multiple
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adhansobh.fxml"));
                    Parent root = fxmlLoader.load();

                    Stage stage = new Stage();
                    stage.setTitle("Adhan Sobh");

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setFullScreen(true);

                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), root);
                    fadeTransition.setFromValue(0.0);
                    fadeTransition.setToValue(1.0);
                    fadeTransition.play();

                    stage.show();

                    // Arrêter la Timeline après l'ouverture de l'interface (si nécessaire)
                    timeline.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void checkmghrebtime() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES); // Ignorer les secondes

            // Extraire l'heure de prière du label
            String sobhtimeText = mghrebtime.getText();
            LocalTime timeofmghreb = parseTimeFromLabel(sobhtimeText);



            if (currentTime.equals(timeofmghreb.truncatedTo(java.time.temporal.ChronoUnit.MINUTES)) && !isAdhanmghreb) {
                isAdhanmghreb=true;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adhanmghreb.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Quran File");
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(true); // Optionnel : Mettre en plein écran
                    stage.show();

                    timeline.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void checkDhohrtime() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES); // Ignorer les secondes

            // Extraire l'heure de prière du label
            String sobhtimeText = dhohrtime.getText();
            LocalTime timeofdhohr = parseTimeFromLabel(sobhtimeText);

            ;

            if (currentTime.equals(timeofdhohr.truncatedTo(java.time.temporal.ChronoUnit.MINUTES)) && !isAdhandhohr) {
                isAdhandhohr=true;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adhandhohr.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Quran File");
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(true); // Optionnel : Mettre en plein écran
                    stage.show();

                    timeline.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void checkasrtime() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES); // Ignorer les secondes

            // Extraire l'heure de prière du label
            String sobhtimeText = asrtime.getText();
            LocalTime timeofasr = parseTimeFromLabel(sobhtimeText);



            if (currentTime.equals(timeofasr.truncatedTo(java.time.temporal.ChronoUnit.MINUTES)) && !isAdhanasr) {
                isAdhanasr=true;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adhanasr.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Adhan File");
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(true); // Optionnel : Mettre en plein écran
                    stage.show();

                    timeline.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void checkichatime() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now().truncatedTo(java.time.temporal.ChronoUnit.MINUTES); // Ignorer les secondes

            // Extraire l'heure de prière du label
            String sobhtimeText = ichatime.getText();
            LocalTime timeoficha = parseTimeFromLabel(sobhtimeText);



            if (currentTime.equals(timeoficha.truncatedTo(java.time.temporal.ChronoUnit.MINUTES)) && !isAdhanicha) {
                isAdhanicha=true;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adhanicha.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Adhan File");
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(true); // Optionnel : Mettre en plein écran
                    stage.show();

                    timeline.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private LocalTime parseTimeFromLabel(String timeText) {
        // Assurez-vous que le texte du label est dans le format correct, par exemple "HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(timeText, formatter);
        } catch (Exception e) {
            // En cas d'erreur de parsing, retourner une valeur par défaut ou gérer l'erreur
            return LocalTime.of(0, 0); // Valeur par défaut
        }
    }


    private void updateNextPrayerTime() {
        // Créer une Timeline pour mettre à jour chaque seconde
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            calculateRemainingTime();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Lancer immédiatement le calcul sans attendre une seconde
        calculateRemainingTime();
    }

    private void calculateRemainingTime() {
        LocalTime now = LocalTime.now();
        String salat;

        // Récupérer les heures des prières depuis les labels
        LocalTime sobh = LocalTime.parse(sobhtime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime dhohr = LocalTime.parse(dhohrtime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime asr = LocalTime.parse(asrtime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime maghreb = LocalTime.parse(mghrebtime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime icha = LocalTime.parse(ichatime.getText(), DateTimeFormatter.ofPattern("HH:mm"));

        LocalTime nextPrayerTime = null;

        // Comparer l'heure actuelle avec les heures de prière
        if (now.isBefore(sobh)) {
            nextPrayerTime = sobh;
            salat="الفجر";

        } else if (now.isBefore(dhohr)) {
            nextPrayerTime = dhohr;
            salat="الظهر";
        } else if (now.isBefore(asr)) {
            nextPrayerTime = asr;
            salat="العصر";
        } else if (now.isBefore(maghreb)) {
            nextPrayerTime = maghreb;
            salat="المغرب";
        } else if (now.isBefore(icha)) {
            nextPrayerTime = icha;
            salat="العشاء";
        } else {
            // Si toutes les prières du jour sont passées, la prochaine est sobh du jour suivant
            nextPrayerTime = sobh.plusHours(24);
            salat="الفجر";
        }

        // Calculer le temps restant jusqu'à la prochaine prière
        long hours = nextPrayerTime.getHour() - now.getHour();
        long minutes = nextPrayerTime.getMinute() - now.getMinute();
        long seconds = nextPrayerTime.getSecond() - now.getSecond();

        if (seconds < 0) {
            minutes--;
            seconds += 60;
        }
        if (minutes < 0) {
            hours--;
            minutes += 60;
        }
        if (hours < 0) {
            hours += 24;
        }

        // Mettre à jour le label avec le temps restant en heures, minutes et secondes
        tempsrestant.setText(String.format("الوقت المتبقي لرفع أذان "+salat+":    " +"%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void calculateRemainingTimebefore10() {
        LocalTime now = LocalTime.now();
        String salat;

        // Récupérer les heures des prières depuis les labels
        LocalTime sobh = LocalTime.parse(sobhtime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime dhohr = LocalTime.parse(dhohrtime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime asr = LocalTime.parse(asrtime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime maghreb = LocalTime.parse(mghrebtime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime icha = LocalTime.parse(ichatime.getText(), DateTimeFormatter.ofPattern("HH:mm"));

        LocalTime nextPrayerTime = null;

        // Comparer l'heure actuelle avec les heures de prière
        if (now.isBefore(sobh)) {
            nextPrayerTime = sobh;
            salat="الفجر";

        } else if (now.isBefore(dhohr)) {
            nextPrayerTime = dhohr;
            salat="الظهر";
        } else if (now.isBefore(asr)) {
            nextPrayerTime = asr;
            salat="العصر";
        } else if (now.isBefore(maghreb)) {
            nextPrayerTime = maghreb;
            salat="المغرب";
        } else if (now.isBefore(icha)) {
            nextPrayerTime = icha;
            salat="العشاء";
        } else {
            // Si toutes les prières du jour sont passées, la prochaine est sobh du jour suivant
            nextPrayerTime = sobh.plusHours(24);
            salat="الفجر";
        }

        // Calculer le temps restant jusqu'à la prochaine prière
        long hours = nextPrayerTime.getHour() - now.getHour();
        long minutes = nextPrayerTime.getMinute() - now.getMinute();
        long seconds = nextPrayerTime.getSecond() - now.getSecond();
        long finalMinutes = minutes;
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {


            if (finalMinutes ==10) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("adhandhohr.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Quran File");
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(true); // Optionnel : Mettre en plein écran
                    stage.show();

                    timeline.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        if (seconds < 0) {
            minutes--;
            seconds += 60;
        }
        if (minutes < 0) {
            hours--;
            minutes += 60;
        }
        if (hours < 0) {
            hours += 24;
        }

        // Mettre à jour le label avec le temps restant en heures, minutes et secondes
        tempsrestant.setText(String.format("الوقت المتبقي لرفع أذان "+salat+":    " +"%02d:%02d:%02d", hours, minutes, seconds));
    }




    private void updateDateTime(Label label) {
        LocalDateTime now = LocalDateTime.now();

        // Formatter pour la date en arabe
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd yyyy", new Locale("ar"));
        String formattedDate = now.format(dateFormatter);

        // Formatter pour l'heure
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault());
        String formattedTime = now.format(timeFormatter);

        // Mise à jour du Label avec date et heure sur des lignes séparées
        datelabel.setText(formattedDate + "\n" +"        "+ formattedTime);
    }



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



}