package GC_11.view.GUI;

import GC_11.network.GameViewMessage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GUIModel {

    private enum State {
        WaitingForPlayer,
        WaitingForAction,
        YourTurn
    }

    static GameViewMessage model;
    private static String clientNickName;
    private final MainFrame mainFrame;

    //VERSIONE JAVA SWING, DA CANCELLARE E REFACTORARE con GUIView che è la versione JavaFX
    private static class MainFrame extends JFrame {

        private static final BufferedImage[] background = new BufferedImage[8];
        private static final Map<String, BufferedImage> otherPlayers = new HashMap<>();

        private static final Map<Integer, BufferedImage> blueTiles = new HashMap<>();
        private static final Map<Integer, BufferedImage> whiteTiles = new HashMap<>();
        private static final Map<Integer, BufferedImage> greenTiles = new HashMap<>();
        private static final Map<Integer, BufferedImage> yellowTiles = new HashMap<>();
        private static final Map<Integer, BufferedImage> purpleTiles = new HashMap<>();
        private static final Map<Integer, BufferedImage> cyanTiles = new HashMap<>();

        //Empty and Prohibited tiles are managed in different ways

        static {
            try {
                background[0] = ImageIO.read(new File("src/resources/GraphicalResources/misc/sfondo parquet.jpg"));
                background[1] = ImageIO.read(new File("src/resources/GraphicalResources/boards/livingroom.png"));
                background[2] = ImageIO.read(new File("src/resources/GraphicalResources/boards/bookshelf.png"));
                background[3] = ImageIO.read(new File("src/resources/GraphicalResources/Publisher material/Title 2000x618px.png"));
                background[4] = ImageIO.read(new File("src/resources/GraphicalResources/common goal cards/4.jpg"));
                background[5] = ImageIO.read(new File("src/resources/GraphicalResources/common goal cards/" + model.getCommonGoalCard(1).getId() + ".jpg"));
                background[6] = ImageIO.read(new File("src/resources/GraphicalResources/misc/Sacchetto Chiuso.png"));

                //ATTENZIONE : getPlayer(nickname) può tornare null
                //background[7] = ImageIO.read(new File("src/resources/GraphicalResources/personal goal cards/Personal_Goals"+model.getPlayer(clientNickName).getPersonalGoal().getId()+".jpg"));


                for (int i = 1; i <= 3; i++) {
                    blueTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Cornici1." + i + ".png")));
                    whiteTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Libri1." + i + ".png")));
                    greenTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Gatti1." + i + ".png")));
                    yellowTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Giochi1." + i + ".png")));
                    purpleTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Piante1." + i + ".png")));
                    cyanTiles.put(i, ImageIO.read(new File("src/resources/GraphicalResources/item tiles/Trofei1." + i + ".png")));
                }

                for (int i = 0; i < model.getPlayers().size(); i++) {
                    if (!model.getPlayers().get(i).getNickname().equals(clientNickName))
                        otherPlayers.put(model.getPlayers().get(i).getNickname(), ImageIO.read(new File("src/resources/GraphicalResources/boards/bookshelf_orth.png")));
                    //i punti
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        private final ImagePanel title = new ImagePanel(background[3], "", 2);
        private final ImagePanel mainBookShelf = new ImagePanel(background[2], "YOUR SHELF", 5);
        private final ImagePanel board = new ImagePanel(background[1], "BOARD", 5);

        private final ImagePanel commonGoalCard1 = new ImagePanel(background[4], "1° COMMON GOAL CARD", 5);
        private final ImagePanel commonGoalCard2 = new ImagePanel(background[5], "2° COMMON GOAL CARD", 5);
        private final ImagePanel bagLabel = new ImagePanel(background[6], "BAG WITH TILES", 5);

        //private final JLabel personalGoalCardLabel = new JLabel(new ImageIcon(background[7]));


        public MainFrame() {
            super("MY SHELFIE");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1920, 1080);
            setPreferredSize(new Dimension(950, 1000));
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


            setLayout((new GridLayout(3, 1)));
            JPanel firstRow = new JPanel(new GridLayout(1, 2));
            JPanel secondRow = new JPanel(new GridLayout(1, 3));
            JPanel utils = new JPanel(new GridLayout(3, 1));

            firstRow.setOpaque(false);
            secondRow.setOpaque(false);
            utils.setOpaque(false);

            firstRow.add(board);
            utils.add(commonGoalCard1);
            utils.add(bagLabel);
            utils.add(commonGoalCard2);
            firstRow.add(utils);
            firstRow.add(mainBookShelf);

            // Dispatch every other player to his shelf and his nickname
            for (Map.Entry<String, BufferedImage> entry : otherPlayers.entrySet()) {
                secondRow.add(new ImagePanel(entry.getValue(), entry.getKey(), 5));
            }


            add(title);
            add(firstRow);
            setBackground(Color.YELLOW);  // if the background is not set, it will be yellow
            add(secondRow);
            pack();

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public static class ImagePanel extends JPanel {
        private BufferedImage image;
        private String labelText;
        private int padding;

        public ImagePanel(BufferedImage image, String labelText, int padding) {
            this.image = image;
            this.labelText = labelText;
            this.padding = padding;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                int width = getWidth() - 10;
                int height = getHeight() - 10;

                // Calculate available space for image and text
                int availableWidth = width - 2 * padding;
                int availableHeight = height - 2 * padding;

                // Calculate image position and size
                int imageWidth;
                int imageHeight;
                double aspectRatio = (double) image.getWidth() / image.getHeight();
                if (aspectRatio > 1) {
                    imageWidth = availableWidth;
                    imageHeight = (int) (availableWidth / aspectRatio);
                } else {
                    imageHeight = availableHeight;
                    imageWidth = (int) (availableHeight * aspectRatio);
                }
                int imageX = (width - imageWidth) / 2;
                int imageY = (height - imageHeight) / 2;

                // Calculate text position and size
                g.setColor(Color.BLACK);
                g.setFont(new Font("Serif Bold Italic", Font.BOLD, 15));
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(labelText);
                int textHeight = fm.getHeight();
                int textX = (width - textWidth) / 2;
                int textY = imageY + imageHeight + textHeight + padding;

                // Check if there is enough space for the text
                if (textY + textHeight <= height - padding) {
                    // Draw image
                    g.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);

                    // Draw label text below the image
                    g.drawString(labelText, textX, textY);
                } else {
                    // Draw image centered if there is no space for the text
                    g.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
                }
            }
        }

    }


    public GUIModel(GameViewMessage model, String clientNickName) {

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
