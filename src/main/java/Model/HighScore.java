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

    public void Insert(int success,int fail, String username){
        try{
            connect();
//            String sql = "SELECT * from highscore where Username ='"+username+"' limit 1";
//            ResultSet res = stm.executeQuery(sql);
//            System.out.println(res);
            String sql = "INSERT INTO tlevel(username,success,fail) VALUES('" + username + "'," + Integer.toString(success) + "," +
                    Integer.toString(fail) + ") ON DUPLICATE KEY UPDATE " + "success= GREATEST(success," + Integer.toString(success) +"), fail=GREATEST(fail," + Integer.toString(fail) +")";
            System.out.println(sql);
//
//            while(res.next()){
//                int hasil = res.getInt("ScoreAkhir");
////                System.out.println(score + time);
////                System.out.println(hasil);
//                if(hasil < score + time){
//                    sql = "UPDATE highscore SET scoreakhir = GREATEST(scoreakhir," + Integer.toString(score+time) +")" + ",Score =" + Integer.toString(score) + ",Waktu =" + Integer.toString(time) + " WHERE username = '" +username+"'";
//                }
//                else{
//                    sql = "";
//                }
////                System.out.print(sql);
//            }
            stm.execute(sql);
        }
        catch(Exception e){
            System.err.println("Gagal memasukkan data : " + e.getMessage());
        }
    }
}
