/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameModel;


/**
 *
 * @author James freeman (1311990)
 * @version May 2014
 */
public class CardList {

    //A linked list to store the cards in this list.
    private MyLinkedList<Card> cards;
    //The index of the first opened card.
    private int openIndex;
    //The tail card.
    private Card tailCard;

    public CardList(Card[] cards) {
        if (cards == null || cards.length == 0) {
            throw new IllegalArgumentException();
        }
        this.cards = new MyLinkedList<Card>();
        for (Card card : cards) {
            this.cards.add(card);
        }
        tailCard = this.cards.getLast();
        tailCard.setShowing(true);
        openIndex = findOpenedIndex();
    }

//    Separate the list into two: [0 .. (i-1)] and [i .. count].
//Open the card at (i-1) if necessary. Return the second list.
    public MyLinkedList<Card> cut(int index) {
        if (index < 0 || index > cards.size()) {
            throw new IndexOutOfBoundsException();
        }
        MyLinkedList<Card> sublist = new MyLinkedList<Card>();
//        int currentIndex = openIndex;
        int currentIndex = cards.size() - 1;
        while (index <= currentIndex) {
            sublist.addFirst(cards.removeLast());
            currentIndex--;
        }
        if (!cards.isEmpty()) {
            cards.getLast().setShowing(true);
            openIndex = findOpenedIndex();
            tailCard = cards.getLast();
        } else {
            tailCard = null;
        }
        return sublist;
    }
//    Join this list to the tail of the other list, if the rules allow
//this.

    public boolean link(MyLinkedList<Card> other) {
        Card card = other.getFirst();
//        Card card = other.cards.getFirst();
        if (cards.isEmpty()) {
            if (card.getValue() == Card.Value.King) {
                while (!other.isEmpty()) {
                    cards.addLast(other.removeFirst());
                }
                tailCard = cards.getLast();
                openIndex = findOpenedIndex();
                return true;
            }
        } else if (card.getColor() != tailCard.getColor()) {
            if (card.getValue().ordinal() == tailCard.getValue().ordinal() - 1) {
                while (!other.isEmpty()) {
                    cards.addLast(other.removeFirst());
                }
                tailCard = cards.getLast();
                openIndex = findOpenedIndex();
                return true;
            }
        }
        return false;
    }

    //Add c as the new tail card, if the rules allow this.
    public boolean add(Card c) {
        if (cards.isEmpty()) {
            if (c.getValue() == Card.Value.King) {
                cards.add(c);
                tailCard = cards.getLast();
                openIndex = findOpenedIndex();
                return true;
            }
        } else if (c.getColor() != tailCard.getColor()) {
            if (c.getValue().ordinal() == tailCard.getValue().ordinal() - 1) {
                cards.add(c);
                tailCard = cards.getLast();
                openIndex = findOpenedIndex();
                return true;
            }
        }
        return false;
    }

//    Delete and return the tail card. Set the card beneath it as the new tail
//card. Open the new tail card if necessary.
    public Card moveTail() {
        if (!cards.isEmpty()) {
            Card card = cards.removeLast();
            if (!cards.isEmpty()) {
                tailCard = cards.getLast();
                tailCard.setShowing(true);
                openIndex = findOpenedIndex();
            } else {
                tailCard = null;
            }
            return card;
        } else {
            return null;
        }
    }

    public String toString() {
        String result = "";
        if (cards.isEmpty()) {
            result += "Empty";
        } else {
            for (int index = 0; index < cards.size() - 1; index++) {
                result += cards.get(index) + "-";
            }
            result += cards.getLast();
        }
        return result;
    }

    public MyLinkedList<Card> getCards() {
        return cards;
    }

    public int findOpenedIndex() {
        int index = -1;
        for (Card card : cards) {
            index++;
            if (card.isShowing()) {
                break;
            }
        }
        return index;
    }

    public int getOpenedIndex() {
        return openIndex;
    }

    public static void main(String[] args) {
        CardDeck cardDeck = new CardDeck();
        int listSize = 7;
        Card[] cards = new Card[listSize];
        for (int index = 0; index < listSize; index++) {
            if (cardDeck.drawCard() != null) {
                cards[index] = cardDeck.takeCard();
                cards[index].setShowing(false);
            }
        }
        CardList cardList = new CardList(cards);
        System.out.println(cardList);
        Card card = cardList.moveTail();
        while (card != null) {
            System.out.println(card);
            System.out.println(cardList);
            card = cardList.moveTail();
        }
    }
}
