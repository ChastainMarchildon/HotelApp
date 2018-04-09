
package hotelapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Owner
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML   private Button logInbutton;
    @FXML   private Button registerButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void registerPressed(ActionEvent event) throws IOException
    {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event,"Register.fxml","Register a new Guest");
    }
    
}
