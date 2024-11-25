# WADHAKER

## Overview
**WADHAKER** is a smart Islamic prayer reminder desktop application built with **JavaFX** and **Maven**. Designed with a stunning and user-friendly interface, WADHAKER helps PC users remain mindful of their daily prayers while focusing on their tasks. It ensures that no prayer is missed and offers an offline solution for reliable prayer time management.

---

## Features
- **Prayer Times Display**  
  Displays the five daily Islamic prayer times alongside the current time directly on the main interface.

- **Countdown Timer**  
  Features a countdown timer for the next prayer, keeping users informed of the time remaining.

- **Offline Daily Updates**  
  Automatically updates prayer times daily without requiring an internet connection by leveraging `.properties` files.

- **Location-Based Accuracy**  
  Allows users to select their location offline, ensuring precise prayer times for any supported region.

- **Notifications with Adhan Playback**  
  - Automatically triggers a notification interface when it's time for prayer.  
  - Plays the Adhan (call to prayer) with the option to close the interface manually or let it close automatically.

- **Elegant and Intuitive Design**  
  Offers a visually appealing design that ensures ease of use.

- **Completely Offline**  
  All core functionalities, including location selection and prayer time updates, work without an internet connection.

---

## Why WADHAKER?
WADHAKER was developed to address the challenge of staying consistent with prayer schedules while working on a computer.  
It stands out due to its offline capability, automated updates, and aesthetically pleasing interface. It is ideal for users who value simplicity, efficiency, and reliability in a prayer management tool.

Key benefits of WADHAKER:
1. Never miss a prayer, thanks to real-time notifications and reminders.  
2. Offline functionality eliminates reliance on internet connectivity.  
3. Helps PC users balance their spiritual and professional commitments effortlessly.  

WADHAKER is more than just a reminder—it’s a seamless integration of technology and spirituality.

---

## Technical Details
- **Framework**: JavaFX  
- **Build Tool**: Maven  
- **Storage**: `.properties` files for storing prayer times and configurations.  
- **Media and Notifications**: JavaFX Media API for Adhan playback and custom notification interfaces.

---

## File Structure
- **`src/main/java/`**: Contains the Java source code, including the main class and controller logic.  
- **`src/main/resources/`**: Stores resource files, including:  
  - **Prayer Times**: `.properties` files with daily prayer times.  
  - **Audio Files**: Adhan recordings for playback.  
  - **FXML Files**: Interface layout files for JavaFX.

- **`pom.xml`**: Maven configuration file for managing dependencies and build configurations.

---

## Installation and Usage
To build and run WADHAKER, ensure you have **Java 11 or higher** and **Maven** installed.

1. **Clone the Repository**:  
   ```bash
   git clone https://github.com/kassemabbassi/WADHAKER.git
   cd WADHAKER

## Video Presentation
Curious to see WADHAKER in action?
🎥 Watch the full demonstration in presentation.mp4 to explore its features and interface.

## Author
Developed with care and dedication by Mohamed kassem Abbassi

