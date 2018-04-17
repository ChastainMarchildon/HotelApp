
package hotelapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Owner
 */
public class RegisterController implements Initializable {
    
    @FXML   private TextField nameField;
    @FXML   private PasswordField passwordField;
    @FXML   private Button registerButton;
    @FXML   private Label errorLabel;
    @FXML   private TextField emailField;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    //Registers a new User and inserts it into the database
        public void register(ActionEvent event)
    {
        SceneChanger sc = new SceneChanger();
        if(this.nameField.getText().isEmpty() || this.passwordField.getText().isEmpty())
        {
            errorLabel.setText("You must fill all fields");
        }
        else{
            try{
                Guest newGuest = new Guest(this.nameField.getText(),this.passwordField.getText(),this.emailField.getText());
                newGuest.setSalt();
                newGuest.hashPassword(newGuest.getPassword(),newGuest.getSalt());
                newGuest.insertIntoDB();
                sc.changeScenes(event,"FXMLDocument.fxml","Register Or Log in");

            }
            catch(Exception e)
            {
                errorLabel.setText(e.getMessage());
            }
        }
    }
    
}
