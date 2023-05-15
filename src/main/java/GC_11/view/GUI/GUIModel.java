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
                background[3] = ImageIO.read(new File("src/resources/GraphicalResources/Publisher material/Title 2000x618px.png"));
                background[4] = ImageIO.read(new File("src/resources/GraphicalResources/common goal cards/4.jpg"));
                background[5] = ImageIO.read(new File("src/resources/GraphicalResources/common goal cards/" + model.getCommonGoalCard(1).getId() + ".jpg"));
                background[6] = ImageIO.read(new File("src/resources/GraphicalResources/misc/Sacchetto Chiuso.png"));

                //ATTENZIONE : getPlayer(nickname) può tornare null
                //background[7] = ImageIO.read(new File("src/resources/GraphicalResources/personal goal cards/Personal_Goals"+model.getPlayer(clientNickName).getPersonalGoal().getId()+".jpg"));



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

        private final ImagePanel title = new ImagePanel(background[3]);
        private final ImagePanel mainBookShelf = new ImagePanel(background[2]);
        private final ImagePanel board = new ImagePanel(background[1]);

        private final ImagePanel commonGoalCard1Label =new ImagePanel(background[4]);
        private final ImagePanel commonGoalCard2Label = new ImagePanel(background[5]);
        private final JLabel bagLabel = new JLabel(new ImageIcon(background[6]));

        //private final JLabel personalGoalCardLabel = new JLabel(new ImageIcon(background[7]));


        public MainFrame(){
            super("MY SHELFIE");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1920, 1080);
            setPreferredSize(new Dimension(900, 1080));
            //it sets the background image
            setContentPane(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (background[0] != null) {
                        g.drawImage(background[0], 0, 0, getWidth(), getHeight(), null);
                    }
                }
            });
            ((JPanel) getContentPane()).setOpaque(false);


            setLayout((new GridLayout(3,1)));
            JPanel firstRow = new JPanel(new GridLayout(1,2));
            JPanel secondRow = new JPanel(new GridLayout(1,3));
            firstRow.setOpaque(false);
            secondRow.setOpaque(false);
            firstRow.add(board);
            firstRow.add(mainBookShelf);

            /*
            for(otherPlayers : otherPlayers.values()){
                secondRow.add(otherPlayers);
            }
            */

            add(title);
            add(firstRow);
            setBackground(Color.ORANGE);  // if the bakcground is not set, it will be orange
            add(secondRow);
            pack();

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public static class ImagePanel extends JPanel {
        private BufferedImage image;

        public ImagePanel(BufferedImage image) {
            this.image = image;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                int width = getWidth();
                int height = getHeight();

                // auto resize image to fit in panel
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                // dispose old graphics and set new image
                g.drawImage(scaledImage, 0, 0, null);
            }
        }

    }
    private static Dimension getScaledImageSize(JLabel panel, ImageIcon image) {
        int maxWidth = panel.getWidth();
        int maxHeight = panel.getHeight();
        int imageWidth = image.getIconWidth();
        int imageHeight = image.getIconHeight();

        if (imageWidth <= maxWidth && imageHeight <= maxHeight) {
            return new Dimension(imageWidth, imageHeight);
        }

        double widthRatio = (double) maxWidth / imageWidth;
        double heightRatio = (double) maxHeight / imageHeight;
        double scaleFactor = Math.min(widthRatio, heightRatio);

        int scaledWidth = (int) (imageWidth * scaleFactor);
        int scaledHeight = (int) (imageHeight * scaleFactor);

        return new Dimension(scaledWidth, scaledHeight);
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
