package DAO;

import Koneksi.Database;
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

public class DAO_Motor implements Model_DAO<Motor>{

    public DAO_Motor(){
        connection = Database.KoneksiDB();
    }
    
    Connection connection; 
    String INSERT = "INSERT INTO motor(kd_motor,plat_motor,merk,warna,tahun,nama_pemilik,alamat,no_telp) values(?,?,?,?,?,?,?,?)";
    String UPDATE = "UPDATE motor SET plat_motor=?, merk=?, warna=?, tahun=?, nama_pemilik=?, alamat=?, no_telp=? WHERE kd_motor=?";
    String DELETE = "DELETE FROM motor WHERE kd_motor=?";
    String SELECT = "SELECT * FROM motor";
    String CARI = "SELECT * FROM motor where kd_motor LIKE ?";
    String COUNTER = "SELECT max(kd_motor) as kode From motor";
    
    @Override
    public int autonumber(Motor Object) {
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
    public void insert(Motor object) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CARI);
            statement.setInt(1, object.getKdmotor());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                JOptionPane.showMessageDialog(null,"Data sudah pernah di simpan!");
            else{
                PreparedStatement statement1 = null;
                statement1 = connection.prepareStatement(INSERT);
                statement1.setInt(1, object.getKdmotor());
                statement1.setString(2, object.getPlatmotor());
                statement1.setString(3, object.getMerk());
                statement1.setString(4, object.getWarna());
                statement1.setString(5, object.getTahun());
                statement1.setString(6, object.getNamapemilik());
                statement1.setString(7, object.getAlamat());
                statement1.setString(8, object.getNotelp());
                statement1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil di simpan!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                statement.close();
            }catch (SQLException ex) {
                Logger.getLogger(DAO_Motor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Motor Object) {
        PreparedStatement statement = null;
        
        try{
            statement = connection.prepareStatement(UPDATE);
            statement.setString(5, Object.getNamapemilik());
            statement.setString(2, Object.getMerk());
            statement.setString(3, Object.getWarna());
            statement.setString(4, Object.getTahun());
            statement.setString(6, Object.getAlamat());
            statement.setString(7, Object.getNotelp());
            statement.setString(1, Object.getPlatmotor());
            statement.setInt(8, Object.getKdmotor());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                statement.close();
            } catch (SQLException ex){
                Logger.getLogger(DAO_Motor.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(DAO_Motor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Motor> getAll() {
        List<Motor> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Motor>();
            statement = connection.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Motor m = new Motor();
                m.setKdmotor(rs.getInt("kd_motor"));
                m.setPlatmotor(rs.getString("plat_motor"));
                m.setMerk(rs.getString("merk"));
                m.setWarna(rs.getString("warna"));
                m.setTahun(rs.getString("tahun"));
                m.setNamapemilik(rs.getString("nama_pemilik"));
                m.setAlamat(rs.getString("alamat"));
                m.setNotelp(rs.getString("no_telp"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Motor> getcari(String key) {
        List<Motor> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Motor>();
            statement = connection.prepareStatement(CARI);
            statement.setString(1, "%"+key+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Motor m = new Motor();
                m.setKdmotor(rs.getInt("kd_motor"));
                m.setPlatmotor(rs.getString("plat_motor"));
                m.setMerk(rs.getString("merk"));
                m.setWarna(rs.getString("warna"));
                m.setTahun(rs.getString("tahun"));
                m.setNamapemilik(rs.getString("nama_pemilik"));
                m.setAlamat(rs.getString("alamat"));
                m.setNotelp(rs.getString("no_telp"));
                list.add(m);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
}
