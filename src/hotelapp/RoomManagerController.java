
package hotelapp;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BinaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Owner
 */
public class RoomManagerController implements Initializable {

    @FXML   private TextField roomSearch;
    @FXML   private DatePicker roomDate;
    @FXML   private Label bookedLabel;
    @FXML   private Label calculatedLabel;
    @FXML   private DatePicker roomDateTwo;
    @FXML   private TableColumn<Room, String> roomColumn;
    @FXML   private TableColumn<Room, String> addressColumn;
    @FXML   private TableView<Room> roomTable;
    @FXML   private Label availableLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.roomColumn.setCellValueFactory(new PropertyValueFactory<Room,String>("Name"));
            this.addressColumn.setCellValueFactory(new PropertyValueFactory<Room,String>("Address"));
            generateList();
        } catch (SQLException ex) {
            Logger.getLogger(RoomManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void generateList() throws SQLException
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        try{
            conn = DriverManager.getConnection("jdbc:sqlserver://javacontacts.database.windows.net:1433;database=ContactsDB;user=chastainM@javacontacts;password=Chastain.Marchildon");

            String sql = "SELECT * FROM Room WHERE Vacant = Vacant";

            s = conn.createStatement();
            rs=s.executeQuery(sql);
               
            while(rs.next()){
                 Room newRoom = new Room(rs.getString("Name"),rs.getInt("RoomNumber"),rs.getInt("Beds"),rs.getString("Vacant"),rs.getString("Address"));
                    rooms.add(newRoom);   
                }
            roomTable.getItems().addAll(rooms);
            availableLabel.setText(String.format("%d",rooms.size()));
  
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
        //Filtering the populated TableView. Tutorial found at http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        FilteredList<Room> filterRooms = new FilteredList<>(rooms);
        roomSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterRooms.setPredicate(room -> {
                if (newValue == null || newValue.isEmpty()) 
                {
                    
                    return true;
                }
                String lowerCaseSearch = newValue.toLowerCase();

                if(room.getAddress().toLowerCase().contains(lowerCaseSearch) || room.getName().toLowerCase().contains(lowerCaseSearch))
                {
                    long available = filterRooms.stream()
                             .count();
                              availableLabel.setText(String.format("%d",available));
                    return true; 
                }
               
                return false; 
            });
        });
        roomTable.setItems(filterRooms); 
        
       
               
    }
    
    public void bookRoom() throws SQLException
    {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
          
            conn = DriverManager.getConnection("jdbc:sqlserver://javacontacts.database.windows.net:1433;database=ContactsDB;user=chastainM@javacontacts;password=Chastain.Marchildon");
            String sql = "UPDATE Room SET Vacant = 'Booked' WHERE RoomNumber = " + this.roomTable.getSelectionModel().getSelectedItem().getRoomNumber();
            ps = conn.prepareStatement(sql);
            
            ps.executeUpdate();
            bookedLabel.setText("Room Successfully Booked");
        }
        catch(Exception e){
            System.err.print(e.getMessage());
            bookedLabel.setText("That room is not available at this time.");
        }
           finally
        {
            if(conn != null)
                conn.close();
            if(ps != null)
                ps.close();
        }
        
    }
    
    public void calculateTotal()
    {
        Integer fromDate = roomDate.getValue().getDayOfMonth();
        Integer toDate = roomDateTwo.getValue().getDayOfMonth();
        double total = (toDate - fromDate)*110;
        
        calculatedLabel.setText(String.format("%10.2f", total));
    }
 
}
