/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameModel;

import java.util.Random;

/**
 *
 * @author James freeman (1311990)
 * @version May 2014
 */
public class CardDeck {
    //circularly-linked list storing all the cards in the deck.
    private MyLinkedList<Card> cards;
    //The current card.
    public Card currentCard;
    
    public CardDeck(){
        int[] indices = new int[52];
        Random random = new Random();
        cards = new MyLinkedList<Card>();
        for (int index = 0; index < 52; index++){
            indices[index] = index;
        }
        try{
            for (int index = 51; index > 0; index--){
                int selectedIndex = random.nextInt(index+1);
                cards.add(new Card(indices[selectedIndex]));
                indices[selectedIndex] = indices[selectedIndex] - indices[index];
                indices[index] = indices[selectedIndex] + indices[index];
                indices[selectedIndex] = indices[index] - indices[selectedIndex];
            }
            cards.add(new Card(indices[0]));
        }catch(Exception e){
            e.printStackTrace();
        }
        currentCard = cards.getFirst();
    }
    //Open the next card, if this is the tail card, return null.
    public Card drawCard(){
        if (cards.isEmpty()){
            return null;
        }        
        if(currentCard.isShowing() && cards.size() >1){
           Card card = cards.removeFirst();
           card.setShowing(false);
            cards.addLast(card);
            currentCard = cards.getFirst();
        }
        currentCard.setShowing(true);      
        if (currentCard == cards.getLast()){
            return null;
        }
        return currentCard; 
    }
//    Delete and return the current card (so we can place it in a list or a
//stack).
    public Card takeCard(){
        Card temp = currentCard;
        cards.remove(currentCard);
        if (!cards.isEmpty()){
           currentCard = cards.getFirst(); 
        }else{
            currentCard = null;
        }      
        return temp;
    }
    
    public boolean isEmpty(){
        return cards.isEmpty();
    }
    
    public Card getCurrentCard(){
        return currentCard;
    }

    public static void main(String[] args){
        CardDeck cardDeck = new CardDeck();
        int count = 0;
        while (cardDeck.drawCard() != null){
            System.out.println(count++ +" " +cardDeck.takeCard());
        }
        System.out.println(count++ +" " +cardDeck.takeCard());
    }
}
