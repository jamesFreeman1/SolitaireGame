/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameModel;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author James freeman (1311990)
 * @version May 2014
 */
public class Card {
    //The card's index, from 1 to 52.
    private int cardIndex;
    private Suit suit;
    private Value value;
    private Color color;
    private boolean isShowing;
    
    private static BufferedImage cards;
    private static BufferedImage back;
    private BufferedImage front;
    private double cardWidth;
    private double cardHeight;
    
    
    public enum Suit{
        Club, Diamond, Heart, Spade 
    };
    
    public enum Value{
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
    };
    
    public enum Color{
        Black, Red
    };
    
    public Card(int cardIndex) throws Exception{
        if(cardIndex <0 || cardIndex >51)
            throw new Exception("Invalid Card Index");
        this.cardIndex = cardIndex;
        suit = Suit.values()[cardIndex /13];
        value = Value.values()[cardIndex % 13];
        if (suit == Suit.Spade || suit == Suit.Club){
            color = color.Black;
        }else{
            color = color.Red;
        }
        isShowing = false;
        
        if(cards == null){
            cards = ImageIO.read(this.getClass().getClassLoader().getResource("gameModel/screenshotOfCards.png"));
        }
        
        cardWidth = cards.getWidth() / 13.0;
        cardHeight = cards.getHeight() / 5.0;
        
        front = cropImage(cards, new Rectangle(
                (int)(value.ordinal() * cardWidth), 
                (int)(suit.ordinal()*cardHeight),(int) cardWidth, 
                (int) cardHeight));
        
        if(back == null){
            back = cropImage(cards, new Rectangle((int) (2* cardWidth), (int) (4* cardHeight), (int) cardWidth, (int) cardHeight));
    }
        
    }
    //Returns the card's suit.
    public Suit getSuit(){
        return suit;

    }
    //Returns the card's value (such as 10, king, etc).
    public Value getValue(){
        return value;
    }
//    The colour of the card is `red' if this card is a heart or diamond, and
//`black' otherwise.
    public Color getColor(){
       return color;
    }
    
    public int getCardIndex(){
        return cardIndex;
    }
    
    public boolean isShowing(){
        return isShowing;
    }
    
    public void setShowing(boolean isShowing){
        this.isShowing = isShowing;
    }
//    Returns a string representation of this card, including its suit and rank.
//Example: Ace of clubs would be ClubA, ten of diamonds would be Diamond10, and
//queen of spades would be SpadeQ.
    @Override
    public String toString(){
        String theString="";
        if (!isShowing){
            theString += "Back";
        }else{
            theString += suit.name();
            switch(value){
                case Ace:
                    theString += "A";
                    break;
                case Jack:
                    theString += "J";
                    break;
                case Queen:
                    theString += "Q";
                    break;
                case King:
                    theString += "K";
                    break;
                default:
                    theString += value.ordinal() + 1;
            }
        }
        return theString;
    }
    
   
    
//    Draws the card. In the simple GUI, this should draw a
//Rectangle, with the string representation of the card written in the colour corre-
//sponding to the colour of the card (either black or red). (You don't have to make
//it pretty)
    public void paintThis(Graphics g, int x, int y){
        if(isShowing){
        g.drawImage(front, x, y, null);
        }
        else{
            g.drawImage(back, x, y, null);
        }
    }
    
    public BufferedImage cropImage(BufferedImage bufferedImage, Rectangle rectangle){
        BufferedImage croppedImage = bufferedImage.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        return croppedImage;
    }
    
    public double getCardWidth(){
        return cardWidth;
    }
    
    public double getCardHeight(){
        return cardHeight;
    }
    
    public static void main (String[] args){
        try{
            Card card = new Card(0);
            card.setShowing(true);
            System.out.println(card);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
}
