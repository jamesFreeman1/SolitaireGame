/**
 * Main class for Solitaire game.
 *
 * ABOUT GAME:
 *
 * Working runnable solitaire game.
 *
 * As requested:
 *
 * The game has a card deck, four card stacks, and seven card lists.
 *
 * The first lists starts with one card, the second with two, and so on, up to the seventh list
 * with seven.
 *
 * You can reveal a card from the deck (one card at a time).
 * 
 * The tail card of any list is always revealed (unless the list is empty).
 * 
 * In a list, you can only put a red card (hearts, diamonds) on top of a black card (spades,
 * clubs), or vice versa.
 * 
 * The Ranks of the cards go king { queen { jack { 10 { 9 { 8 { 7 { 6 { 5 { 4 { 3 { 2 { ace,
 * from highest to lowest. The revealed cards in a card list must have consecutive ranks 
 * in decreasing order. The cards on a card stack must start from ace and have consecutive 
 * ranks in increasing order.
 *
 * Each card stack corresponds to a specific suit (Hearts, Clubs, Spades, Diamonds). You
 * can only place cards to the stack that corresponds to their suits.
 * 
 * You can cut a card list into two lists, where the lower list contains only revealed cards.
 * And then link the lower list to the end of another list
 * 
 * Once all four card stacks have 13 cards in them, you win.
 * 
 * Extension 1) Improved GUI with a green felt background and appropriate playing card images.
 * 
 * Extension 2) A winning message is displayed once all four card stacks have 13 cards in them.
 * 
 * Extension 3) A text area is available at the bottom of the screen and an execute button
 *              for users to carryout commands.
 * 
 * Extension 4) Also added a quit link in the Game menu bar.
 * 
 * @author James Freeman (1311990)
 * @version May 2014
 */
package gameModel;

import gameModel.Card;
import gameModel.CardDeck;
import gameModel.CardList;
import gameModel.CardStack;
import gameModel.PaintingPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Solitaire extends KeyAdapter implements ActionListener {

    //A CardDeck for the current game's deck.
    private CardDeck cardDeck;
    //An array of CardStacks (of length 4).
    private CardStack[] cardStacks;
    //An array of CardLists (of length 7).
    private CardList[] cardLists;
    private boolean isRunning;
    private PaintingPanel paintingPanel;
    private JTextField txtCommand;
    private JButton btnExecute;
    private Scanner scanner;
    private JFrame frame;

    public Solitaire() {
        initialize();
        isRunning = false;
        try {
            paintingPanel = new PaintingPanel(cardDeck, cardStacks, cardLists);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel userInputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        txtCommand = new JTextField();
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        txtCommand.addKeyListener(this);
        userInputPanel.add(txtCommand, gridBagConstraints);
        btnExecute = new JButton("Execute");
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        btnExecute.addActionListener(this);
        userInputPanel.add(btnExecute, gridBagConstraints);
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(paintingPanel, BorderLayout.CENTER);
        pane.add(userInputPanel, BorderLayout.SOUTH);
        frame = new JFrame("Solitare");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar mb = new JMenuBar();
        JMenu game = new JMenu("Game");
        mb.add(game);
        JMenuItem restart = new JMenuItem("restart");
        game.add(restart);
        JMenuItem quit = new JMenuItem("quit");
        game.add(quit);
        
        class exitaction implements ActionListener{
            @Override
            public void actionPerformed (ActionEvent e){
                System.exit(0);
            }
        }
        class restartaction implements ActionListener{
            @Override
            public void actionPerformed (ActionEvent e){
                initialize();
            paintingPanel.setCardDeck(cardDeck);
            paintingPanel.setCardLists(cardLists);
            paintingPanel.setCardStacks(cardStacks);
            paintingPanel.setIsWinner(false);
            cardDeck.drawCard();
            paintingPanel.repaint();
            }
        }
        
        quit.addActionListener(new exitaction());
        restart.addActionListener(new restartaction());
        
        frame.setJMenuBar(mb);
        
        frame.setContentPane(pane);
        frame.pack();
        Toolkit toolKit = Toolkit.getDefaultToolkit();
        frame.setLocation((toolKit.getScreenSize().width - frame.getWidth()) / 2, (toolKit.getScreenSize().height - frame.getHeight()) / 2);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void initialize() {
        cardDeck = new CardDeck();
        cardStacks = new CardStack[4];
        cardStacks[0] = new CardStack();
        cardStacks[1] = new CardStack();
        cardStacks[2] = new CardStack();
        cardStacks[3] = new CardStack();

        cardLists = new CardList[7];
        cardLists[0] = createCardList(1);
        cardLists[1] = createCardList(2);
        cardLists[2] = createCardList(3);
        cardLists[3] = createCardList(4);
        cardLists[4] = createCardList(5);
        cardLists[5] = createCardList(6);
        cardLists[6] = createCardList(7);
    }

    private CardList createCardList(int size) {
        Card[] cards = new Card[size];
        for (int index = 0; index < size; index++) {
            if (cardDeck.drawCard() != null) {
                cards[index] = cardDeck.takeCard();
                cards[index].setShowing(false);
            }
        }
        return new CardList(cards);
    }

//    Should create a new Solitaire, then call showGUI, pass-
//ing the newly-created Solitaire object to it. Lastly, should call the Solitaire
//object's startGame method.
    public static void main(String[] args) {
        // TODO code application logic here
        Solitaire solitaire = new Solitaire();
        solitaire.startGame();
    }

//    Perform whatever command indicates if the
//rules allow it and return a success message. If the command is invalid, return a
//warning instead.
    public boolean executeCommand(String command) {
        if (command.compareToIgnoreCase("quit") == 0 || !isRunning) {
            isRunning = false;
            return true;
        } else if (command.compareToIgnoreCase("DrawCard") == 0) {
            if (cardDeck.drawCard() != null) {
                return true;
            } else {
                return false;
            }
        } else if (command.toLowerCase().contains("deckto")) {
            StringTokenizer tokenizer = new StringTokenizer(command);
            if (tokenizer.countTokens() != 2) {
                return false;
            }
            tokenizer.nextToken();
            int deckNumber = -1;
            try {
                deckNumber = Integer.parseInt(tokenizer.nextToken());
            } catch (Exception e) {
                return false;
            }
            if (deckNumber < 1 || deckNumber > 7) {
                return false;
            }
            if (cardLists[deckNumber - 1].add(cardDeck.getCurrentCard())) {
                cardDeck.takeCard();
                cardDeck.drawCard();
                return true;
            }
            return false;
        } else if (command.toLowerCase().contains("link")) {
            StringTokenizer tokenizer = new StringTokenizer(command);
            if (tokenizer.countTokens() != 3) {
                return false;
            }
            tokenizer.nextToken();
            String cardName = tokenizer.nextToken();
            int deckNumber = -1;
            try {
                deckNumber = Integer.parseInt(tokenizer.nextToken());
            } catch (Exception e) {
                return false;
            }
            if (deckNumber < 1 || deckNumber > 7) {
                return false;
            }
            int cardListIndex = 0;
            for (CardList cardList : cardLists) {
                int cardIndex = 0;
                for (Card card : cardList.getCards()) {
                    if (card.toString().compareToIgnoreCase(cardName) == 0 && card.isShowing()) {
                        if (cardListIndex == deckNumber - 1) {
                            return false;
                        }
                        if (cardLists[deckNumber - 1].getCards().isEmpty() && card.getValue() == Card.Value.King) {
                            return cardLists[deckNumber - 1].link(cardList.cut(cardIndex));
                        } else if (card.getColor() != cardLists[deckNumber - 1].getCards().getLast().getColor() && card.getValue().ordinal() + 1 == cardLists[deckNumber - 1].getCards().getLast().getValue().ordinal()) {
                            return cardLists[deckNumber - 1].link(cardList.cut(cardIndex));
                        }
                    }
                    cardIndex++;
                }
                cardListIndex++;
            }
            return false;
        } else if (command.toLowerCase().contains("send")) {
            StringTokenizer tokenizer = new StringTokenizer(command);
            if (tokenizer.countTokens() != 2) {
                return false;
            }
            tokenizer.nextToken();
            String cardName = tokenizer.nextToken();
            Card card = null;
            boolean isAdded = false;
            boolean isFromDeck = false;
            int listIndex = 0;
            if (cardName.compareToIgnoreCase(cardDeck.getCurrentCard().toString()) == 0) {
                card = cardDeck.getCurrentCard();
                isFromDeck = true;
            }
            if (!isFromDeck) {
                for (CardList cardList : cardLists) {
                    if (!cardList.getCards().isEmpty()) {
                        if (cardName.compareToIgnoreCase(cardList.getCards().getLast().toString()) == 0) {
                            card = cardList.getCards().getLast();
                            break;
                        }
                    }
                    listIndex++;
                }
            }
            if (card == null) {
                return false;
            }
            for (CardStack cardStack : cardStacks) {
                isAdded = cardStack.add(card);
                if (isAdded) {
                    break;
                }
            }
            if (isAdded && isFromDeck) {
                cardDeck.takeCard();
                cardDeck.drawCard();
                return true;
            } else if (isAdded) {
                cardLists[listIndex].moveTail();
                return true;
            }
            return false;
        } else if (command.toLowerCase().contains("restart")) {
            initialize();
            paintingPanel.setCardDeck(cardDeck);
            paintingPanel.setCardLists(cardLists);
            paintingPanel.setCardStacks(cardStacks);
            paintingPanel.setIsWinner(false);
            cardDeck.drawCard();
            paintingPanel.repaint();
            return true;
        } else {
            return false;
        }
    }
    //Runs a loop that accepts commands until either a quit command is
    //given or the player wins. Should attempt to perform any commands given, and
    //prints all messages back to the user.

    public void startGame() {
        scanner = new Scanner(System.in);
        isRunning = true;
        cardDeck.drawCard();
        paintingPanel.repaint();
        printCurrentStep();
        while (isRunning) {
            try {
                if (executeCommand(scanner.nextLine())) {
                    if (isRunning) {
                        if (cardStacks[0].size() == 13
                                && cardStacks[1].size() == 13
                                && cardStacks[2].size() == 13
                                && cardStacks[3].size() == 13) {
                            paintingPanel.setIsWinner(true);
                            System.out.println("Well done!! You win");
                        } else {
                            printCurrentStep();
                        }
                        paintingPanel.repaint();
                    }
                } else {
                    System.out.print("Invalid move. Your next move. ");
                }
            } catch (Exception e) {
                isRunning = false;
            }
        }
        scanner.close();
        System.exit(0);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnExecute) {
            if (executeCommand(txtCommand.getText())) {
                if (isRunning) {
                    System.out.println();
                    printCurrentStep();
                    paintingPanel.repaint();
                } else {
                    frame.setVisible(false);
                    try {
                        System.in.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                System.out.print("\nInvalid move. Your next move: ");
            }
            txtCommand.setText("");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (executeCommand(txtCommand.getText())) {
                if (isRunning) {
                    System.out.println();
                    printCurrentStep();
                    paintingPanel.repaint();
                } else {
                    frame.setVisible(false);
                    try {
                        System.in.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                System.out.print("\nInvalid move. Your next move: ");
            }
            txtCommand.setText("");
        }
    }
    
    //Provides a printout for CLI
    public void printCurrentStep() {
        System.out.println("<--------------Current Step-------------->");
        if (cardDeck.isEmpty()) {
            System.out.println("Card Deck: Is Empty");
        } else {
            System.out.print("Card Deck: Not Empty");
            System.out.println("       Open Card: " + cardDeck.getCurrentCard());
        }
        System.out.print("Card Stacks : ");
        for (CardStack cardStack : cardStacks) {
            System.out.print(cardStack + " ");
        }
        System.out.println("");
        System.out.println("Card Lists : ");
        int count = 1;
        for (CardList cardList : cardLists) {
            System.out.println(count++ + ": " + cardList);
        }
        System.out.print("Your next move: ");
    }
}
