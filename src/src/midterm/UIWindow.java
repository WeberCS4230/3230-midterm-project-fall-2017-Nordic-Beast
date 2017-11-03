package midterm;

import blackjack.message.ChatMessage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class UIWindow extends JFrame {

     //Creating everything necessary for the window
    private final JPanel displayPanel;
    private final JPanel inputPanel;

    private JTextArea textDisplay;
    private JTextArea textInput;

    private final JScrollPane displayScroll;
    private final JScrollPane inputScroll;

    private final JButton sendChatButton;
    private String inputString;
    private String username;
    private String pastUser;

    public UIWindow(Client parent) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900,600);
        BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setResizable(false);
        
        displayPanel = new JPanel();
        inputPanel = new JPanel();
        
        textDisplay = new JTextArea();
        textInput = new JTextArea();
        
        displayScroll = new JScrollPane(textDisplay);
        inputScroll = new JScrollPane(textInput);
        
        sendChatButton = new JButton("send");
        
        textDisplay.setEditable(false);
        textDisplay.setLineWrap(true);
        textDisplay.setWrapStyleWord(true);
        
        /*
        * Set max and min sized because otherwise it looked wonky
         */
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));
        displayPanel.setMaximumSize(new Dimension(500, 300));
        displayPanel.setMinimumSize(new Dimension(500, 300));
        displayPanel.add(displayScroll);
        displayPanel.setBorder(new EmptyBorder(10, 10, 40, 10));

        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        inputPanel.setMaximumSize(new Dimension(500, 200));
        inputPanel.setMinimumSize(new Dimension(500, 200));
        inputPanel.add(inputScroll);
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        AbstractAction submit = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textInput.selectAll();
                inputString = textInput.getSelectedText();
                textInput.setText(null);
                parent.sendChat(inputString);
            }
        };
        sendChatButton.addActionListener(submit);
        sendChatButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK), "submitted");
        sendChatButton.getActionMap().put("submitted", submit);
        
        this.add(displayPanel, BorderLayout.NORTH);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(sendChatButton, BorderLayout.SOUTH);
        this.setVisible(true);       
    }
    
    public String getUsername(){
        username = JOptionPane.showInputDialog(this, "Please enter your username.", null);
        return username;
    }
    
    public String retryUsername(){
        pastUser = username;
        username = JOptionPane.showInputDialog(this, "Username unavailable, please try again.", null);
        if (pastUser.equals(username)){
            retryUsername();
        }
        return username;
    }
    
    public void appendChat(ChatMessage inMessage){
        textDisplay.append("\n" + inMessage.getUsername() + ": " + inMessage.getText());
    }
}
