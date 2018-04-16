
package hotelapp;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Owner
 */
public class RoomManagerController implements Initializable {

    
    @FXML   private ComboBox<String> roomComboBox;
    @FXML   private DatePicker roomDate;
    @FXML   private Label bookedLabel;
    @FXML   private Label totalLabel;
    @FXML   private DatePicker roomDateTwo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            generateComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(RoomManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void generateComboBox() throws SQLException
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        try{
            ObservableList<Room> rooms = FXCollections.observableArrayList();
            conn = DriverManager.getConnection("jdbc:sqlserver://javacontacts.database.windows.net:1433;database=ContactsDB;user=chastainM@javacontacts;password=Chastain.Marchildon");

            String sql = "SELECT * FROM Room WHERE Vacant = Vacant";

            s = conn.createStatement();
            rs=s.executeQuery(sql);
                while(rs.next()){
                    Room newRoom = new Room(rs.getString("Name"),rs.getInt("RoomNumber"),rs.getInt("Beds"),rs.getString("Vacant"));
                    rooms.add(newRoom);
                }
                for(Room room : rooms)
                {
                  roomComboBox.getItems().add(room.getName());  
                }
        } 
        catch(Exception e){
           System.err.print(e.getMessage()); 
        }
        finally
        {
            if(conn != null)
                conn.close();
            if(s!= null)
                s.close();
            if(rs != null)
                rs.close();
        }
    }
    
    public void bookRoom() throws SQLException
    {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
          
            conn = DriverManager.getConnection("jdbc:sqlserver://javacontacts.database.windows.net:1433;database=ContactsDB;user=chastainM@javacontacts;password=Chastain.Marchildon");
            String sql = "UPDATE Room SET Vacant = 'Booked' WHERE RoomId = " + this.roomComboBox.getValue();
            ps = conn.prepareStatement(sql);
            
            ps.executeUpdate();
            bookedLabel.setText("Room Successfully Booked");
        }
        catch(Exception e){
            System.err.print(e.getMessage());
        }
           finally
        {
            if(conn != null)
                conn.close();
            if(ps != null)
                ps.close();
        }
    }
 
}
