/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Owner
 */
public class Room {
    private String name, vacancy;
    private int beds,roomNumber;
    
    
    public Room(String name, int beds,int roomNumber,String vacancy)
    {
        setName(name);
        setBeds(beds);
        setRoomNumber(roomNumber);
        setVacancy(vacancy);
                
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }
    
    public void setVacancy(String vacancy)
    {
        try{
            if(vacancy.equals("Vacant"))
            {
                this.vacancy = vacancy;
            }
            else if(vacancy.equals("Booked"))
            {
                this.vacancy=vacancy;
            }
        }
        catch(IllegalArgumentException e)
        {
            System.err.print(e.getMessage());
        }
        
    }
    
    public String getVacancy()
    {
        return this.vacancy;
    }
    
    public void generateRoom() throws SQLException
    {
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlserver://javacontacts.database.windows.net:1433;database=ContactsDB;user=chastainM@javacontacts;password=Chastain.Marchildon");

            String sql = "INSERT INTO Room(RoomNumber,Name,Beds,Vacant) VALUES(?,?,?,?)";

            ps = conn.prepareStatement(sql);

            ps.setInt(1, roomNumber);
            ps.setString(2, name);
            ps.setInt(3,beds);
            ps.setString(4, vacancy);

            ps.executeUpdate();
        }
        catch(Exception e)
        {
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
