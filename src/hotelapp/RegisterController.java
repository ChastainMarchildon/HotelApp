/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelapp;

import Models.Guest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Owner
 */
public class RegisterController implements Initializable {
    
    @FXML   private TextField nameField;

    @FXML   private TextField passwordField;

    @FXML   private Button registerButton;

    @FXML   private Label errorLabel;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
        public void register(ActionEvent event)
    {
        try{
            Guest newGuest = new Guest(this.nameField.getText(),this.passwordField.getText());
            newGuest.setSalt();
            newGuest.hashPassword(newGuest.getPassword(),newGuest.getSalt());
        }
        catch(IllegalArgumentException e)
        {
            errorLabel.setText("You must enter a name AND Password");
        }
    }
    
}
