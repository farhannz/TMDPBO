package ViewModel;
import Model.HighScore;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class VMHighScore {
    public HighScore hs;

    public VMHighScore(){
        this.hs = new HighScore();
    }
    public DefaultTableModel getTabel(){
        Object[] header = {
                "No",
                "Username",
                "Success",
                "Fail"
        };
        DefaultTableModel tabel = new DefaultTableModel(null,header);
        try{
            ResultSet res = this.hs.getResult();
            int no = 1;
            while(res.next()) {
                Object data[] = new Object[4];
                data[0] = no;
                data[1] = res.getString("Username");
                data[2] = res.getString("Success");
                data[3] = res.getString("Fail");
                no++;
                tabel.addRow(data);
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return tabel;
    }

    public void Insert(int success,int fail, String Username){
        hs.Insert(success,fail,Username);
    }
}
