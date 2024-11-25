package com.example.prayerproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PrayerTimesLoader {

    private Properties prayerTimes;
    public PrayerTimesLoader(int x,String location) {
        prayerTimes = new Properties();
        try {
            // Charger le fichier en fonction du lieu sélectionné
            String filePath = "src/main/resources/dhohrtimes" + location.toLowerCase() + ".properties";
            FileInputStream input = new FileInputStream(filePath);
            prayerTimes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public PrayerTimesLoader(int x) {

        prayerTimes = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/resources/dhohrtimesmonastir.properties");
            prayerTimes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    
    /*public PrayerTimesLoader(int x,int y,) {

        prayerTimes = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/resources/asrtimesmonastir.properties");
            prayerTimes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public PrayerTimesLoader(int x,int y,String location)
    {
        prayerTimes = new Properties();
        try {
            // Charger le fichier en fonction du lieu sélectionné
            String filePath = "src/main/resources/asrtimes" + location.toLowerCase() + ".properties";
            FileInputStream input = new FileInputStream(filePath);
            prayerTimes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  /*  public PrayerTimesLoader(int x,int y,int z) {

        prayerTimes = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/resources/mghrebtimesmonastir.properties");
            prayerTimes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public PrayerTimesLoader(int x,int y,int z,String location)
    {
        prayerTimes = new Properties();
        try {
            // Charger le fichier en fonction du lieu sélectionné
            String filePath = "src/main/resources/mghrebtimes" + location.toLowerCase() + ".properties";
            FileInputStream input = new FileInputStream(filePath);
            prayerTimes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public PrayerTimesLoader(int x,int y,int z,int t) {

        prayerTimes = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/resources/ichatimesmonastir.properties");
            prayerTimes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public PrayerTimesLoader(int x,int y,int z,int w,String location)
    {
        prayerTimes = new Properties();
        try {
            // Charger le fichier en fonction du lieu sélectionné
            String filePath = "src/main/resources/ichatimes" + location.toLowerCase() + ".properties";
            FileInputStream input = new FileInputStream(filePath);
            prayerTimes.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalTime getDhohrTimeForDate(LocalDate date) {
        // Formatter pour formater la date comme dans le fichier properties (yyyy-MM-dd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String timeString = prayerTimes.getProperty(formattedDate);

        if (timeString != null) {
            // Formatter pour convertir l'heure en LocalTime
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            // Retourner une heure par défaut si aucune donnée n'est disponible
            return LocalTime.of(12, 0); // Par exemple, 12:00 par défaut
        }
    }
    public LocalTime getasrTimeForDate(LocalDate date) {
        // Formatter pour formater la date comme dans le fichier properties (yyyy-MM-dd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String timeString = prayerTimes.getProperty(formattedDate);

        if (timeString != null) {
            // Formatter pour convertir l'heure en LocalTime
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            // Retourner une heure par défaut si aucune donnée n'est disponible
            return LocalTime.of(12, 0); // Par exemple, 12:00 par défaut
        }
    }
    public LocalTime getmghrebTimeForDate(LocalDate date) {
        // Formatter pour formater la date comme dans le fichier properties (yyyy-MM-dd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String timeString = prayerTimes.getProperty(formattedDate);

        if (timeString != null) {
            // Formatter pour convertir l'heure en LocalTime
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            // Retourner une heure par défaut si aucune donnée n'est disponible
            return LocalTime.of(12, 0); // Par exemple, 12:00 par défaut
        }
    }
    public LocalTime getichaTimeForDate(LocalDate date) {
        // Formatter pour formater la date comme dans le fichier properties (yyyy-MM-dd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String timeString = prayerTimes.getProperty(formattedDate);

        if (timeString != null) {
            // Formatter pour convertir l'heure en LocalTime
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            // Retourner une heure par défaut si aucune donnée n'est disponible
            return LocalTime.of(12, 0); // Par exemple, 12:00 par défaut
        }
    }
}
