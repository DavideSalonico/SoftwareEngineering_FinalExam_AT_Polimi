package GC_11.network;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.exceptions.PlayerNotInListException;
import GC_11.model.Game;


import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private final int lobbyID;
    private static int lobbyNumber;
    private int maxPlayers;
    String fisrtPlayer;

    private List<String> playersNames;


    public Lobby(){
        this.maxPlayers=4;
        playersNames = new ArrayList<String>();
        this.lobbyID=lobbyNumber;
        lobbyNumber++;
    }

    public synchronized boolean nameAlreadyTaken(String playerName){
        return playersNames.contains(playerName);
    }

    public synchronized void addPlayer(String playerName) throws ExceededNumberOfPlayersException, NameAlreadyTakenException{
        if (playersNames.size()< maxPlayers && !playersNames.contains(playerName)){
            playersNames.add(playerName);
        }
        else if(playersNames.size() == 4){
            throw new ExceededNumberOfPlayersException();
        }
        else if(this.nameAlreadyTaken(playerName))
        {
            throw new NameAlreadyTakenException(playerName);
        }
    }

    public synchronized void removePlayer(String playerName) throws PlayerNotInListException{
        if (playersNames.contains(playerName)){
            playersNames.remove(playerName);
        }
        else {
            throw new PlayerNotInListException(playerName);
        }
    }

    public synchronized boolean isFull(){
        return (playersNames.size()==maxPlayers);
    }

    public synchronized boolean hasPlayer(String playerName){
        return (playersNames.contains(playerName));
    }

    public synchronized List<String> getPlayers() {
        return playersNames;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void startGame(Game game){
            // Notifica al controller di lanciare creare game e lanciare il gioco

    }

    public void setMaxPlayers(int maxPlayers){
        this.maxPlayers=maxPlayers;
    }

    // TODO LOBBY by Mattia
    // fatto - lanciare l'excpetion in addPlayer se eccede il numero massimo o non può inserire il player
    // Il primo giocatore è il capo del gruppo, e sarà l'unico a poter avviare il gioco, gestire la questione dei permessi
    // Rendere la classe observable, fare anche una view generale condivisa per tutti i player (observer), capire come
    // sarà la view, che dovrà far vedere le stesse cose a tutti ma solo il primo giocatore ha i permessi (ricorda che i giocatori
    // non sono ancora stati creati)
    // rendere la classe runnable sicuro diventerà un thread
    // scrivere costruttore del controller in modo che inizializzi un nuovo gioco, tutti i player e le loro rispettive view singole
    // classe LOBBY è al di fuori di MVC (forse sta nel controller),
    // gestire aspetti di rete e client


}
