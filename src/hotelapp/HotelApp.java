/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelapp;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Owner
 */
public class HotelApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        //Used to generate a list of hotel rooms
//        String[] addresses={"Toronto","New York","London","Vancouver"};
//        String[] roomNames={"Basic","Deluxe","Luxury","Exclusive"};
//        SecureRandom rand = new SecureRandom();
//        for(int i=0; i<51;i++)
//        {
//            int city = rand.nextInt(4);
//            String roomName="";
//            int beds=0;
//            String address="";
//            if(i>=0&&i<=10){
//                roomName=roomNames[0];
//                beds=2;
//                address=addresses[city];
//            }
//            else if(i>10&&i<=30){
//                roomName=roomNames[1];
//                beds=3;
//                address=addresses[city];
//            }
//                 
//            else if(i>30&&i<=40){
//                roomName=roomNames[2];
//                beds=3;
//                address=addresses[city];
//            }
//            else if(i>40&&i<=50){
//                beds=4;
//                roomName=roomNames[3];
//                address=addresses[city];
//            }
//            Room newRoom = new Room(roomName,beds,i,"Vacant",address);
//            try {
//                newRoom.generateRoom();
//            } catch (SQLException ex) {
//                Logger.getLogger(HotelApp.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        
    }

