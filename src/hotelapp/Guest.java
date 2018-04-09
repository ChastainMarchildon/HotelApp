
package hotelapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Owner
 */
public class Guest {
    private String name,password;
    private byte[] salt;
    
    public Guest(String name, String password)
    {
        setName(name);
        getSalt();
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
    
    public byte[] setSalt()
    {
        byte[] salt = new byte[16];
        try{
        SecureRandom rand = SecureRandom.getInstanceStrong();
       
        rand.nextBytes(salt);
        
        }
        catch(NoSuchAlgorithmException e)
        {
          System.err.print(e);  
        }
        this.salt=salt;
        return salt;
    }
    
    public byte[] getSalt()
    {
        return this.salt;
    }
    
    public String hashPassword(String password, byte[] salt)
    {
        salt=this.salt;
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
        this.password=hashedPassword;
        return hashedPassword;
    }
    public void insertIntoDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlserver://javacontacts.database.windows.net:1433;database=ContactsDB;user=chastainM@javacontacts;password=Chastain.Marchildon");
            
            String sql = "INSERT INTO Guest(name,password) VALUES(?,?)";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, name);
            ps.setString(2, password);

            
            ps.executeUpdate();
        }
        catch(Exception e)
                {
                    System.err.println(e.getMessage());
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
