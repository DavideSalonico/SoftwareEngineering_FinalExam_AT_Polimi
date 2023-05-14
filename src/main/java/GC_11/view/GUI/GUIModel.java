package GC_11.view.GUI;

import GC_11.model.GameView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GUIModel  {

    private enum State {
        WaitingForPlayer,
        WaitingForAction,
        YourTurn
    }
    static GameView model;
    private static String clientNickName;


    private static class MainFrame extends JFrame{

        private static final BufferedImage[] background = new BufferedImage[10];
        private static final Map<String, BufferedImage> otherPlayers = new HashMap<>();

        private static final Map<Integer, BufferedImage> blueTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> whiteTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> greenTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> orangeTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> purpleTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> cyanTiles  = new HashMap<>();

        //Empty and Prohibited tiles are managed in different ways

        private static final BufferedImage[] GUIplayer = new BufferedImage[10];
        static {
            try{
                background[0] = ImageIO.read(new File("src/resources/GraphicalResources/misc/sfondo parquet.png"));
                background[1] = ImageIO.read(new File("src/resources/GraphicalResources/misc/livingroom.png"));
                background[2] = ImageIO.read(new File("src/resources/GraphicalResources/misc/bookshelf.png"));
                background[3] = ImageIO.read(new File("src/resources/GraphicalResources/Publisher material/Title 2000x2000px.png"));
                background[4] = ImageIO.read(new File("src/resources/GraphicalResources/common goals cards/" + model.getCommonGoalCard(0).id + ".png"));
                background[5] = ImageIO.read(new File("src/resources/GraphicalResources/common goals cards/" + model.getCommonGoalCard(1).id + ".png"));
                background[6] = ImageIO.read(new File("src/resources/GraphicalResources/common goals cards/Sacchetto Chiuso.png"));

                //ATTENZIONE : getPlayer(nickname) può tornare null
                background[7] = ImageIO.read(new File("src/resources/GraphicalResources/personal goal card/Personal_Goals"+model.getPlayer(clientNickName).getPersonalGoal().getId()+".png"));

                for(int i = 1; i<=3; i++){
                    blueTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Cornici1."+ i +".png")));
                    whiteTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Libri1."+ i +".png")));
                    greenTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Gatti1."+ i +".png")));
                    orangeTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Giochi1."+ i +".png")));
                    purpleTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Piante1."+ i +".png")));
                    cyanTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Trofei1."+ i +".png")));
                }

                for (int i = 0; i<  model.getPlayers().size()-1; i++){
                    if(!model.getPlayers().get(i).getNickname().equals(clientNickName))
                        otherPlayers.put(model.getPlayers().get(i).getNickname(), ImageIO.read(new File("src/resources/GraphicalResources/boards/bookshelf_orth.png")));
                    //i punti
                }
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    public GUIModel(GameView model, String clientNickName) {

        this.model = model;
        this.clientNickName = clientNickName();
    }

    public String getClientNickName() {
        return clientNickName;
    }

    public void setClientNickName(String clientNickName) {
        this.clientNickName = clientNickName;
    }

}
