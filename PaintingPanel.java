/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author James freeman (1311990)
 * @version May 2014
 */
public class PaintingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int GAP = 10;
    private static final int LIST_GAP = 35;
    private static final int HEIGHT = 650;
    private static final int FONT_SIZE = 96;

    private CardDeck cardDeck;
    private CardStack[] cardStacks;
    private CardList[] cardLists;
    private BufferedImage background;
    private BufferedImage emptyCard;
    private boolean isWinner;

    public PaintingPanel(CardDeck cardDeck, CardStack[] cardStacks, CardList[] cardLists) throws IOException {
        super();
        this.cardDeck = cardDeck;
        this.cardStacks = cardStacks;
        this.cardLists = cardLists;
        setPreferredSize(new Dimension((int) (cardDeck.getCurrentCard().getCardWidth() * 7 + 8 * GAP), HEIGHT));
        background = ImageIO.read(this.getClass().getClassLoader().getResource("gameModel/greenfelt.png"));
        emptyCard = ImageIO.read(this.getClass().getClassLoader().getResource("gameModel/emptyCard.png"));
        isWinner = false;
    }

    //Draw the game stored in game.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        if (!cardDeck.isEmpty()) {
            cardDeck.getCurrentCard().paintThis(g, GAP, GAP);
        } else {
            g.drawImage(emptyCard, GAP, GAP, null);
        }
        int step = 3;
        for (CardStack cardStack : cardStacks) {
            if (!cardStack.isEmpty()) {
                cardStack.peek().paintThis(g, (int) (cardDeck.getCurrentCard().getCardWidth() 
                        * step + (step + 1) * GAP), GAP);
            } else {
                g.drawImage(emptyCard, (int) (cardDeck.getCurrentCard()
                        .getCardWidth() * step + (step + 1) * GAP), GAP, null);
            }
            step++;
        }
        step = 0;
        for (CardList cardList : cardLists) {
            if (!cardList.getCards().isEmpty()) {
                int listStep = 0;
                for (Card card : cardList.getCards()) {
                    card.paintThis(g, (int) (cardDeck.getCurrentCard()
                            .getCardWidth() * step + (step + 1) * GAP),
                            (int) (cardDeck.getCurrentCard().getCardHeight() + listStep * LIST_GAP + 2 * GAP));
                    listStep++;
                }
            }else{
                g.drawImage(emptyCard, (int)(cardDeck.getCurrentCard()
                        .getCardWidth() *step + (step+1)*GAP),
                        (int)(cardDeck.getCurrentCard().getCardHeight() + 2 * GAP), null);
            }
            step++;
        }
        if(isWinner){
            g.setColor(Color.WHITE);
            Font font = new  Font("serif", Font.BOLD, FONT_SIZE);
            g.setFont(font);
            int width = g.getFontMetrics().stringWidth("YOU WIN!");
            g.drawString("YOU WIN!", (getWidth()-width)/2, getHeight()/2);
        }
    }
    /**
     * Set the Card Deck to new card Deck
     *
     * @param cardDeck is made to be the cardDeck
     */
    public void setCardDeck(CardDeck cardDeck){
        this.cardDeck = cardDeck;
    }
     /**
     * Set the Card Stack to new Card Stack
     *
     * @param cardStacks is made to be the cardDeck
     */
    public void setCardStacks(CardStack[] cardStacks){
        this.cardStacks = cardStacks;
    }
     /**
     * Set the Card List to new Card List
     *
     * @param cardLists is made to be the cardLists
     */
    public void setCardLists(CardList[] cardLists){
        this.cardLists = cardLists;
    }
     /**
     * Set the isWinner field to true or false
     *
     * @param isWinner is the new boolean value of the isWinner field.
     */
    public void setIsWinner(boolean isWinner){
        this.isWinner = isWinner;
    }
    
}
