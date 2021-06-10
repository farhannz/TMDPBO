package Model;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class HighScore {
    public static Connection con;
    public static Statement stm;

    public HighScore(){
    }

    public void connect(){
        try{
            String url ="jdbc:mysql://localhost:3306/tmdpbo";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(url,user,pass);
            this.stm = con.createStatement();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    public ResultSet getResult(){
        ResultSet result = null;
        try{
            String sql = "Select * from tlevel";
            connect();
            result = stm.executeQuery(sql);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return result;
    }
}
