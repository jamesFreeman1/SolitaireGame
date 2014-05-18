/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameModel;

import java.util.Stack;

/**
 *
 * @author James freeman (1311990)
 * @version May 2014
 */
public class CardStack {
    //A stack to store the cards.
    public MyStack<Card> cards;        
    public CardStack(){
        cards = new MyStack<Card>();   
    }
    //Adds c to the top of the stack, if the rules allow this.
    public boolean add(Card c){
       if(cards.isEmpty()){
           if(c.getValue() == Card.Value.Ace){
               cards.push(c);
               return true;
           }
       }else{
           if(c.getSuit() == cards.peek().getSuit()){
               if(c.getValue().ordinal() == cards.peek().getValue().ordinal() + 1){
               cards.push(c);
               return true;
               }
           }
       }
       return false;
    }
    
    public boolean isEmpty(){
        return cards.isEmpty();
    }
    
    public Card peek(){
        return cards.peek();
    }
    
    public int size(){
        return cards.size();
    }
    
    public String toString(){
        String result = "";
        if(cards.isEmpty()){
            result += "Empty";
            
        }else {
            result += cards.peek();
        }
        return result;
    }
    
    
}
