package DAO;

import Koneksi.Database;
import Model.Sparepart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DAO_Sparepart implements Model_DAO<Sparepart>{
    
    public DAO_Sparepart(){
        connection = Database.KoneksiDB();
    }
    
    Connection connection;
    String INSERT = "INSERT INTO sparepart(kd_sparepart,nama_sparepart,harga_sparepart,stok) values (?,?,?,?)";
    String UPDATE = "UPDATE sparepart SET nama_sparepart=?, harga_sparepart=?, stok=? WHERE kd_sparepart=?";
    String DELETE = "DELETE FROM sparepart WHERE kd_sparepart=?";
    String SELECT = "SELECT * FROM sparepart";
    String CARI = "SELECT * FROM sparepart WHERE kd_sparepart LIKE ?";
    String COUNTER = "SELECT max(kd_sparepart) as kode FROM sparepart";
    
    @Override
    public int autonumber(Sparepart Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Sparepart Object) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CARI);
            statement.setString(1, Object.getKdsparepart());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                JOptionPane.showMessageDialog(null,"Data sudah pernah di simpan!");
            else{
                PreparedStatement statement1 = null;
                statement1 = connection.prepareStatement(INSERT);
                statement1.setString(1, Object.getKdsparepart());
                statement1.setString(2, Object.getNamasparepart());
                statement1.setInt(3, Object.getHargasparepart());
                statement1.setInt(4, Object.getStok());
                statement1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil di simpan!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                statement.close();
            }catch (SQLException ex) {
                Logger.getLogger(DAO_Sparepart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Sparepart Object) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, Object.getNamasparepart());
            statement.setInt(2, Object.getHargasparepart());
            statement.setInt(3, Object.getStok());
            statement.setString(4, Object.getKdsparepart());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil di ubah!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO_Sparepart.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(DAO_Sparepart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Sparepart> getAll() {
        List<Sparepart> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Sparepart>();
            statement = connection.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Sparepart s = new Sparepart();
                s.setKdsparepart(rs.getString("kd_sparepart"));
                s.setNamasparepart(rs.getString("nama_sparepart"));
                s.setHargasparepart(rs.getInt("harga_sparepart"));
                s.setStok(rs.getInt("stok"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Sparepart> getcari(String key) {
        List<Sparepart> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Sparepart>();
            statement = connection.prepareStatement(CARI);
            statement.setString(1, "%"+key+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Sparepart s = new Sparepart();
                s.setKdsparepart(rs.getString("kd_sparepart"));
                s.setNamasparepart(rs.getString("nama_sparepart"));
                s.setHargasparepart(rs.getInt("harga_sparepart"));
                s.setStok(rs.getInt("stok"));
                list.add(s);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public String autonumber2(){
        PreparedStatement statement= null;
        int nomor_berikutnya = 0;
        String urutan = "";
        try{
            String COUNTER = "SELECT ifnull(max(convert(right(kd_sparepart, 3),signed integer)),0) as kode,"
                    + "ifnull(length(max(convert(right(kd_sparepart, 3)+1, signed integer))),0) as panjang from sparepart";
            
            statement = connection.prepareStatement(COUNTER);
            ResultSet rs2 = statement.executeQuery();
            if (rs2.next()){
                nomor_berikutnya = rs2.getInt("kode") + 1;
                if(rs2.getInt("kode")!=0){
                    if(rs2.getInt("panjang")==1){
                        urutan = "00" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==2){
                        urutan = "0" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==3){
                        urutan = String.valueOf(nomor_berikutnya);
                    }
                }
                else {
                    urutan = "001";
                }
            }else
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urutan;
    }
}
    
