package GC_11.network;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.exceptions.PlayerNotInListException;
import GC_11.model.Game;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Lobby {
    private final int maxPlayers;
    private List<String> playersNames;
    private Game gameModel = null;
    private PropertyChangeListener listener;


    public Lobby(int n){
        maxPlayers=n;
        playersNames = new ArrayList<String>();
    }

    public synchronized boolean nameAlreadyTaken(String playerName){
        return playersNames.contains(playerName);
    }

    public synchronized Game addPlayer(String playerName) throws ExceededNumberOfPlayersException, NameAlreadyTakenException{
        if (playersNames.size() < maxPlayers && !playersNames.contains(playerName)){
            playersNames.add(playerName);
        }
        else if(this.isFull()){
            throw new ExceededNumberOfPlayersException();
        }
        else if(this.nameAlreadyTaken(playerName))
        {
            throw new NameAlreadyTakenException(playerName);
        }
        return null;
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

    private synchronized boolean hasPlayer(String playerName){
        return (playersNames.contains(playerName));
    }

    public synchronized List<String> getPlayers() {
        return playersNames;
    }

    public int getMaxPlayers() {
        return maxPlayers;
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


    public Game getGameModel() {
        return gameModel;
    }

    public void setGameModel(Game gameModel) {
        this.gameModel = gameModel;
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "GAME CREATED",
                null,
                this.gameModel);
        this.listener.propertyChange(evt);
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }
}
