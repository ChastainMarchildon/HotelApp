
package hotelapp;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Owner
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML   private TextField emailField;
    @FXML   private PasswordField passwordField;
    @FXML   private Button loginButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void login(ActionEvent event) throws SQLException
    {
        String email = emailField.getText();
        String password = passwordField.getText();
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
                
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlserver://javacontacts.database.windows.net:1433;database=ContactsDB;user=chastainM@javacontacts;password=Chastain.Marchildon");
            
            String sql = "SELECT name,password,salt FROM Guest WHERE email = \'"+email+"\'";
            
            s = conn.createStatement();
            rs=s.executeQuery(sql);
            while(rs.next())
                {
                String hashedPassword = rs.getString("Password");
                Blob blob = rs.getBlob("Salt");
                int blobLength = (int) blob.length();  
                byte[] salt = blob.getBytes(1, blobLength);


                if(hashedPassword.equals(confirmHash(password,salt)))
                {
                  SceneChanger sc = new SceneChanger();
                  sc.changeScenes(event,"RoomManager.fxml", "Book a Room");
                }
            }
                
        }
        catch(Exception e)
                {
                    System.err.println(e.getMessage());
                }
        finally
        {
            if(conn != null)
                conn.close();
            if(s != null)
                s.close();
        }
    }
        public static String confirmHash(String password, byte[] salt)
    {
        String hashedPassword=null;
        StringBuilder sb = new StringBuilder();
        
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashed = md.digest(password.getBytes());
            md.update(salt);
            
            for(int i=0; i<hashed.length;i++)
           {
               sb.append(String.format("%02x",hashed[i]));
           }
           hashedPassword = sb.toString();
        }
        catch(NoSuchAlgorithmException e)
        {
            System.err.print(e);
        }
        
        return hashedPassword;
    }
    
}
