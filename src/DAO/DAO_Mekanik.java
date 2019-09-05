package DAO;

import Koneksi.Database;
import Model.Mekanik;
import Model.Motor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DAO_Mekanik implements Model_DAO<Mekanik>{

    
    public DAO_Mekanik(){
        connection = Database.KoneksiDB();
    }
    
    Connection connection;
    String INSERT = "INSERT INTO mekanik(kd_mekanik,nama_mekanik,alamat,no_telp) values(?,?,?,?)";
    String UPDATE = "UPDATE mekanik SET nama_mekanik=?, alamat=?, no_telp=? WHERE kd_mekanik=?";
    String DELETE = "DELETE FROM mekanik WHERE kd_mekanik=?";
    String SELECT = "SELECT * FROM mekanik";
    String CARI = "SELECT * FROM mekanik WHERE kd_mekanik LIKE ?";
    String COUNTER = "SELECT max(kd_mekanik) as kode FROM mekanik";
    
    @Override
    public int autonumber(Mekanik Object) {
        PreparedStatement statement = null;
        int nomor=0;
        try{
            statement = connection.prepareStatement(COUNTER);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                nomor=rs.getInt("kode")+1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return nomor;
    }

    @Override
    public void insert(Mekanik Object) {
         PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CARI);
            statement.setInt(1, Object.getKdmekanik());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                JOptionPane.showMessageDialog(null,"Data sudah pernah di simpan!");
            else{
                PreparedStatement statement1 = null;
                statement1 = connection.prepareStatement(INSERT);
                statement1.setInt(1, Object.getKdmekanik());
                statement1.setString(2, Object.getNmmekanik());
                statement1.setString(3, Object.getAlamatmekanik());
                statement1.setString(4, Object.getNotelpmekanik());
                statement1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil di simpan!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                statement.close();
            }catch (SQLException ex) {
                Logger.getLogger(DAO_Mekanik.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Mekanik Object) {
         PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, Object.getNmmekanik());
            statement.setString(2, Object.getAlamatmekanik());
            statement.setString(3, Object.getNotelpmekanik());
            statement.setInt(4, Object.getKdmekanik());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil di ubah!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO_Mekanik.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                statement.close();
            } catch (SQLException ex){
                Logger.getLogger(DAO_Mekanik.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Mekanik> getAll() {
        List<Mekanik> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Mekanik>();
            statement = connection.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Mekanik m = new Mekanik();
                m.setKdmekanik(rs.getInt("kd_mekanik"));
                m.setNmmekanik(rs.getString("nama_mekanik"));
                m.setAlamatmekanik(rs.getString("alamat"));
                m.setNotelpmekanik(rs.getString("no_telp"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Mekanik> getcari(String key) {
        List<Mekanik> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Mekanik>();
            statement = connection.prepareStatement(CARI);
            statement.setString(1, "%"+key+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Mekanik m = new Mekanik();
                m.setKdmekanik(rs.getInt("kd_mekanik"));
                m.setNmmekanik(rs.getString("nama_mekanik"));
                m.setAlamatmekanik(rs.getString("alamat"));
                m.setNotelpmekanik(rs.getString("no_telp"));
                list.add(m);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
}
