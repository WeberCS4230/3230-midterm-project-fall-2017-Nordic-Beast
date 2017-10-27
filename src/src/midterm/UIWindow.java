package midterm;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UIWindow extends JFrame {

    private final JPanel displayPanel;
    private String username;

    public UIWindow() {
        displayPanel = new JPanel();
        
        this.setVisible(true);       
    }
    
    public String getUsername(){
        username = JOptionPane.showInputDialog(this, "Please enter your username.", null);
        return username;
    }
    
    public String retryUsername(){
        username = JOptionPane.showInputDialog(this, "Username unavailable, please try again.", null);
        return username;
    }
}
