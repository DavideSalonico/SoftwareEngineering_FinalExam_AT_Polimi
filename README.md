Prova Finale di Ingegneria del Software - AA 2022-2023
alt text

Implementazione del gioco da tavolo MyShelfie.

Il progetto consiste nell’implementazione di un sistema distribuito composto da un singolo server in grado di gestire una partita alla volta e multipli client (uno per giocatore) che possono partecipare ad una sola partita alla volta utilizzando il pattern MVC (Model-View-Controller). La rete è stata gestita con l'utilizzo delle socket e di RMI.

Interazione e gameplay: linea di comando (CLI) e grafica (GUI).

Librerie e Plugins
Maven	Strumento di automazione della compilazione utilizzato principalmente per progetti Java.
JavaFx	Libreria grafica per realizzare interfacce utente.
JUnit	Framework di unit testing.

Funzionalità
Funzionalità Sviluppate
Regole Complete
CLI
GUI
Socket + RMI*
2 FA (Funzionalità Avanzate):
Persistenza: lo stato di una partita deve essere salvato su disco, in modo che la partita possa riprendere anche a seguito dell’interruzione dell’esecuzione del server.
Chat: è possibile scambiare messagi con i giocatori in un partita sia in una chat privata fra due giocatori, sia in una chat globale.

DISCLAIMER: Non funziona GUI con protocollo di rete RMI.

Compilazione e packaging
I jar sono stati realizzati con l'ausilio di Maven Shade Plugin. Di seguito sono forniti i jar precompilati. Per compilare i jar autonomamente, posizionarsi nella root del progetto e lanciare il comando:
mvn clean package
Il jar compilato verranno posizionati all'interno della cartella target/.

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

Componenti del gruppo
Jaskaran Ram
Lorenzo Reitani
Mattia Repetti
Davide Salonico
