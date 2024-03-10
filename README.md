
# Final Software Engineering Exam - AY 2022-2023

## Goal 🎯
Implementation of the MyShelfie board game.

The project consists of implementing a distributed system composed of a single server capable of managing one game at a time and multiple clients (one per player) who can participate in only one game at a time using the MVC (Model-View-Controller) pattern. The network is managed using sockets and RMI.

## Interaction and Gameplay
Command Line Interface (CLI) and Graphical User Interface (GUI).

## Libraries and Plugins
Maven: Automation tool used mainly for Java projects.
JavaFX: Graphic library for creating user interfaces.
JUnit: Unit testing framework.
Developed Features
Complete Rules
CLI
GUI
Socket + RMI
2 FA (Advanced Features):
Persistence: the state of a game must be saved to disk, so that the game can be resumed even after the server execution is interrupted.
Chat: it is possible to exchange messages with players in a game, either in a private chat between two players or in a global chat.

## Compilation and Packaging

The JARs have been created with the help of Maven Shade Plugin. Below are the precompiled JARs. To compile the JARs independently, navigate to the project root and run the command:
mvn clean package

The compiled JARs will be placed inside the target/ folder.

## Usage
The following instructions describe how to run the client with CLI or GUI interface.

CLI
To launch MyShelfie Client CLI, type the following command in the terminal:
java -jar myshelfie.jar -CLI

GUI
To launch the GUI mode, type the following command in the terminal:
java -jar PSP000-1.0-SNAPSHOT-jar-with-dependencies.jar -GUI

MyShelfie Server
To launch MyShelfie Server, type the following command in the terminal:
java -jar PSP000-1.0-SNAPSHOT-jar-with-dependencies.jar

## Team

- [Jaskaran Ram](https://github.com/JaskaranRam)
- [Lorenzo Reitani](https://github.com/LorenzoReitani)
- [Mattia Repetti](https://github.com/MattiaRepetti)
- [Davide Salonico](https://github.com/DavideSalonico)

#Prova Finale di Ingegneria del Software - AA 2022-2023

## Obiettivo 🎯

Implementazione del gioco da tavolo MyShelfie.

Il progetto consiste nell’implementazione di un sistema distribuito composto da un singolo server in grado di gestire una partita alla volta e multipli client (uno per giocatore) che possono partecipare ad una sola partita alla volta utilizzando il pattern MVC (Model-View-Controller). La rete è stata gestita con l'utilizzo delle socket e di RMI.

## Interazione e Gameplay

Linea di Comando (CLI) e Grafica (GUI).

## Librerie e Plugin

* Maven	Strumento di automazione della compilazione utilizzato principalmente per progetti Java.
* JavaFx	Libreria grafica per realizzare interfacce utente.
* JUnit	Framework di unit testing.

## Funzionalità Sviluppate

* Regole Complete
* CLI
* GUI
* Socket + RMI
* 2 FA (Funzionalità Avanzate):
* Persistenza: lo stato di una partita deve essere salvato su disco, in modo che la partita possa riprendere anche a seguito dell’interruzione dell’esecuzione del server.
* Chat: è possibile scambiare messagi con i giocatori in un partita sia in una chat privata fra due giocatori, sia in una chat globale.

## Compilazione e Packaging

I jar sono stati realizzati con l'ausilio di Maven Shade Plugin. Di seguito sono forniti i jar precompilati. Per compilare i jar autonomamente, posizionarsi nella root del progetto e lanciare il comando:
mvn clean package
Il jar compilato verranno posizionati all'interno della cartella target/.


## Uso

Le seguenti istruzioni descrivono come eseguire il client con interfaccia CLI o GUI.

CLI
Per lanciare MyShelfie Client CLI digitare da terminale il comando:
java -jar myshelfie.jar -CLI

GUI
Per poter lanciare la modalità GUI digitare da terminale il comando:
java -jar PSP000-1.0-SNAPSHOT-jar-with-dependencies.jar -GUI

MyShelfie Server
Per lanciare MyShelfie Server digitare da terminale il comando:
java -jar PSP000-1.0-SNAPSHOT-jar-with-dependencies.jar

## Squadra

- [Jaskaran Ram](https://github.com/JaskaranRam)
- [Lorenzo Reitani](https://github.com/LorenzoReitani)
- [Mattia Repetti](https://github.com/MattiaRepetti)
- [Davide Salonico](https://github.com/DavideSalonico)
