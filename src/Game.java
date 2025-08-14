import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.util.List;

public class Game {
    final int BoardWidth = 700;
    final int BoardHieght = 750;
    int FieldSize = 5;
    int Score =0;
    int Round =1;
    int OnLightNum =1;
    //boolean
    boolean []  OnLight ;//= new boolean [FieldSize*FieldSize];
    JFrame Board;
    JLabel Label;
    JPanel TextPanel,
            FieldPanel;
    JButton[] Field ;//= new JButton[FieldSize*FieldSize];
    Random random = new Random();
    Timer ClickTimer;
    Image StarImage;
    ImageIcon StarIcon;
    Game(){
        // setting Board
        Board = new JFrame();
        Board.setSize(BoardWidth, BoardHieght);
        Board.setLocationRelativeTo(null);
        Board.setResizable(false);
        Board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Board.setLayout();


        // setting Label
        Label = new JLabel();
        Label.setText("score: " +Integer.toString(Score) +"  round:"+Integer.toString(Round));
        Label.setFont(new Font("Arial", Font.PLAIN, 50));
        Label.setHorizontalAlignment(JLabel.CENTER);
        //Label.setText("Score: " + Integer.toString(score));
        Label.setOpaque(true);
        // setting TextPanel
        TextPanel = new JPanel();
        TextPanel.setLayout(new BorderLayout());
        TextPanel.setBackground(Color.WHITE);
        TextPanel.add(Label);
        Board.add(TextPanel,BorderLayout.NORTH);
        // setting FieldPanel
        FieldPanel = new JPanel();
        FieldPanel.setSize(BoardWidth,BoardWidth);
        //FieldPanel.setLayout(new GridLayout(FieldSize, FieldSize,1,1));
        FieldPanel.setBackground(Color.BLACK);
        Board.add(FieldPanel);

        StarImage = new ImageIcon(getClass().getResource("./Star.png")).getImage();







        ClickTimer = new Timer((Round+1)*1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if((Round==1  && OnLightNum == 0 )|| Round>1){
                    //ClickTimer.stop();
                    SetNextRound();
                    SetOnLight();
                    //ClickTimer.start();

                }
            }
        });

        SetOnLight();
        Board.setVisible(true);
        ClickTimer.start();



    }
    void SetOnLight(){
        ClickTimer.stop();
        Field = new JButton[FieldSize*FieldSize];
        OnLight = new boolean [FieldSize*FieldSize];

         OnLightNum =0;
        int i;
        for(i=0 ; i<FieldSize*FieldSize ;i++)OnLight[i]=false;

        for( i=0 ; i<Round && i<FieldSize*FieldSize-1 ; i++)
        {
            int rand = random.nextInt(FieldSize*FieldSize);
            if(!OnLight[rand])
                OnLight[rand]=true;
            else i--;

        }
        OnLightNum = i;
        // draw
        FieldPanel.setVisible(false);
        FieldPanel.removeAll();
        FieldPanel.setLayout(new GridLayout(FieldSize, FieldSize,1,1));
        StarIcon = new ImageIcon(StarImage.getScaledInstance(FieldPanel.getWidth()/FieldSize, FieldPanel.getHeight()/FieldSize, java.awt.Image.SCALE_SMOOTH));


        for ( i = 0; i < FieldSize*FieldSize; i++)
        {
            JButton tile = new JButton();
            tile.setBackground(Color.BLACK);
            tile.setFocusable(false);

            if(OnLight[i]){
                tile.setIcon(StarIcon);
            }
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if(tile.getIcon() == null){ //<----- attention
                        Label.setText("Wrong move :( start again");
                        Score =0;
                        Round =1;
                        FieldSize = 5;
                        SetOnLight();

                    }
                    else{
                        Score += 1;
                        OnLightNum --;
                        Label.setText("score: " +Integer.toString(Score) +"  round: "+Integer.toString(Round));
                        tile.setBackground(Color.BLACK);
                        tile.setIcon(null);


                    }
                }
            });

            Field[i] = tile;
            FieldPanel.add(tile);
        }

        FieldPanel.setVisible(true);
        ClickTimer.start();
    }
    void SetNextRound(){

        Round++;
        Label.setText("score: " +Integer.toString(Score) +"  round: "+Integer.toString(Round));

        if(Round % 6 == 0)FieldSize++;

        return;

    }


}

class Coordinate{
        int x,y;
    Coordinate(int a,int b){
        x=a;
        y=b;
    }
}