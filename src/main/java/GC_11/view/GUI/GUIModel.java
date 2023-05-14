package GC_11.view.GUI;

import GC_11.model.GameView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    private final MainFrame mainFrame;


    private static class MainFrame extends JFrame{

        private static final BufferedImage[] background = new BufferedImage[8];
        private static final Map<String, BufferedImage> otherPlayers = new HashMap<>();

        private static final Map<Integer, BufferedImage> blueTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> whiteTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> greenTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> orangeTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> purpleTiles  = new HashMap<>();
        private static final Map<Integer, BufferedImage> cyanTiles  = new HashMap<>();

        //Empty and Prohibited tiles are managed in different ways

        static {
            try{
                background[0] = ImageIO.read(new File("src/resources/GraphicalResources/misc/sfondo parquet.jpg"));
                background[1] = ImageIO.read(new File("src/resources/GraphicalResources/boards/livingroom.png"));
                background[2] = ImageIO.read(new File("src/resources/GraphicalResources/boards/bookshelf.png"));
                background[3] = ImageIO.read(new File("src/resources/GraphicalResources/Publisher material/Title 2000x2000px.png"));
                //background[4] = ImageIO.read(new File("src/resources/GraphicalResources/common goals cards/" + model.getCommonGoalCard(0).getId() + ".jpg"));
                //background[5] = ImageIO.read(new File("src/resources/GraphicalResources/common goals cards/" + model.getCommonGoalCard(1).getId() + ".jpg"));
                background[6] = ImageIO.read(new File("src/resources/GraphicalResources/misc/Sacchetto Chiuso.png"));

                //ATTENZIONE : getPlayer(nickname) può tornare null
                //background[7] = ImageIO.read(new File("src/resources/GraphicalResources/personal goal card/Personal_Goals"+model.getPlayer(clientNickName).getPersonalGoal().getId()+".jpg"));



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

        private final JLabel backgroundLabel = new JLabel(new ImageIcon(background[0]));
        private final JLabel titleLabel= new JLabel();
        {
            Image scaledTitle = background[3].getScaledInstance(background[3].getWidth()/2,background[3].getHeight()/2, Image.SCALE_SMOOTH);
            ImageIcon x = new ImageIcon(scaledTitle);
            titleLabel.setIcon(x);
            //titleLabel = new JLabel(new ImageIcon(scaledTitle));
            setBackground(Color.GRAY);
        }
        private final JLabel mainBookShelfLabel = new JLabel();
        {
            Image scaledBookShelf = background[2].getScaledInstance(background[2].getWidth()/2,background[2].getHeight()/2, Image.SCALE_SMOOTH);
            ImageIcon x = new ImageIcon(scaledBookShelf);
            mainBookShelfLabel.setIcon(x);
            //mainBookShelfLabel = new JLabel(new ImageIcon(scaledBookShelf));
            setBackground(Color.GRAY);
        }
        private final JLabel boardLabel = new JLabel(new ImageIcon(background[1]));


        //private final JLabel commonGoalCard1Label = new JLabel(new ImageIcon(background[4]));
        //private final JLabel commonGoalCard2Label = new JLabel(new ImageIcon(background[5]));
        private final JLabel bagLabel = new JLabel(new ImageIcon(background[6]));
        //private final JLabel personalGoalCardLabel = new JLabel(new ImageIcon(background[7]));


        public MainFrame(){
            super("MY SHELFIE");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1920, 1080);
            //setResizable(false);
            JPanel mainPanel = new JPanel(new GridLayout(3,1));
            JPanel firstRow = new JPanel(new GridLayout(1,2));
            JPanel secondRow = new JPanel(new GridLayout(1,3));
            firstRow.add(boardLabel);
            firstRow.add(mainBookShelfLabel);

            /*
            for(otherPlayers : otherPlayers.values()){
                secondRow.add(otherPlayers);
            }
            */

            Image scaledTitle = background[3].getScaledInstance(background[3].getWidth(),background[3].getHeight(), Image.SCALE_SMOOTH);
            Image scaledMainBookShelf = background[2].getScaledInstance(background[3].getWidth(),background[3].getHeight(), Image.SCALE_SMOOTH);
            mainPanel.add(titleLabel);
            mainPanel.add(firstRow);
            //secondRow.setBackground(Color.BLACK);  i colori vengono mostrati solo se non coperti da immagini
            mainPanel.add(secondRow);
            add(mainPanel);
            pack();
            setVisible(true);

        }
    }

    public GUIModel(GameView model, String clientNickName) {

        this.model = model;
        this.clientNickName = clientNickName;
        this.mainFrame = new MainFrame();
    }

    public String getClientNickName() {
        return clientNickName;
    }

    public void setClientNickName(String clientNickName) {
        this.clientNickName = clientNickName;
    }

}
