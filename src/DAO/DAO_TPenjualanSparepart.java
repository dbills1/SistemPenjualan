package DAO;

import Koneksi.Database;
import Model.TPenjualanSparepart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DAO_TPenjualanSparepart implements Model_DAO<TPenjualanSparepart> {

    Connection connection;
    
    public DAO_TPenjualanSparepart(){
        connection = Database.KoneksiDB();
    }
    
    @Override
    public int autonumber(TPenjualanSparepart Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(TPenjualanSparepart Object) {
        PreparedStatement statement = null;
        try{
            String CHECK = "SELECT * FROM penjualan WHERE no_penjualan = ?";
            statement = connection.prepareStatement(CHECK);
            statement.setString(1, Object.getNo_penjualan());
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                PreparedStatement statement2;
                String INSERT = "INSERT INTO penjualan(no_penjualan, tgl_penjualan) VALUES(?, ?)";
                statement2 = connection.prepareStatement(INSERT);
                statement2.setString(1, Object.getNo_penjualan());
                statement2.setString(2, Object.getTanggal());
                statement2.executeUpdate();
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try{
                statement.close();
            }catch (SQLException ex) {
                Logger.getLogger(DAO_TPenjualanSparepart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void insert_detil(TPenjualanSparepart object){
        PreparedStatement statement = null;
        try{
            String INSERTDETIL = "INSERT INTO detil_penjualan(no_penjualan, kd_sparepart, jumlah_pesan, harga_pesan) VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(INSERTDETIL);
            statement.setString(1, object.getNo_penjualan());
            statement.setString(2, object.getKode_sparepart());
            statement.setInt(3, object.getQty());
            statement.setInt(4, object.getHarga());
            statement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void update_stok(TPenjualanSparepart object){
        PreparedStatement statement;
        int stok = 0;
        try{
            String SELECTSTOK = "SELECT stok FROM sparepart WHERE kd_sparepart = ?";
            statement = connection.prepareStatement(SELECTSTOK);
            statement.setString(1, object.getKode_sparepart());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                if(rs.getInt("stok") <= 0){
                    JOptionPane.showMessageDialog(null, "Stok Sparepart " + rs.getString("nama_sparepart") + " Kosong!");
                } else {
                    stok = rs.getInt("stok") - object.getQty();
                    
                    PreparedStatement statement2;
                    String UPDATESTOK = "UPDATE sparepart SET stok = ? WHERE kd_sparepart = ?";
                    statement2 = connection.prepareStatement(UPDATESTOK);
                    statement2.setInt(1, stok);
                    statement2.setString(2, object.getKode_sparepart());
                    statement2.executeUpdate();
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(TPenjualanSparepart Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TPenjualanSparepart> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TPenjualanSparepart> getcari(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String autonumber2(){
        PreparedStatement statement;
        int nomor_berikutnya = 0;
        String urutan = "";
        try{
            String COUNTER = "SELECT ifnull(max(convert(right(no_penjualan, 5),signed integer)),0) as kode,"
                    + "ifnull(length(max(convert(right(no_penjualan, 5)+1, signed integer))),0) as panjang from penjualan";
            
            statement = connection.prepareStatement(COUNTER);
            ResultSet rs2 = statement.executeQuery();
            if (rs2.next()){
                nomor_berikutnya = rs2.getInt("kode") + 1;
                if(rs2.getInt("kode")!=0){
                    if(rs2.getInt("panjang")==1){
                        urutan = "PS" + "0000" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==2){
                        urutan = "PS" + "000" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==3){
                        urutan = "PS" + "00" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==4){
                        urutan = "PS" + "0" + nomor_berikutnya;
                    } else if(rs2.getInt("panjang")==5){
                        urutan = "PS" + nomor_berikutnya;
                    }
                }
                else {
                    urutan = "PS" + "00001";
                }
            }else
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urutan;
    }

    public List<TPenjualanSparepart> isicombosparepart(){
        PreparedStatement statement;
        List<TPenjualanSparepart> list = null;
        try{
            list = new ArrayList();
            String TAMPILSPAREPART = "select * from sparepart order by nama_sparepart";
            statement = connection.prepareStatement(TAMPILSPAREPART);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                TPenjualanSparepart k = new TPenjualanSparepart();
                k.setNama_sparepart(rs.getString("nama_sparepart"));
                list.add(k);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<TPenjualanSparepart> getsparepart(String nama){
        PreparedStatement statement;
        List<TPenjualanSparepart> list = null;
        try{
            list = new ArrayList();
            String CARISPAREPART = "select * from sparepart where nama_sparepart= ?";
            statement = connection.prepareStatement(CARISPAREPART);
            statement.setString(1, nama);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            }else{
                TPenjualanSparepart k = new TPenjualanSparepart();
                k.setHarga(rs.getInt("harga_sparepart"));
                k.setStok(rs.getInt("stok"));
                list.add(k);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<TPenjualanSparepart> getkdsparepart(String namasparepart){
        PreparedStatement statement;
        List<TPenjualanSparepart> list = null;
        try{
            list = new ArrayList();
            String CARISPAREPART = "select * from sparepart where nama_sparepart= ?";
            statement = connection.prepareStatement(CARISPAREPART);
            statement.setString(1, namasparepart);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                TPenjualanSparepart k = new TPenjualanSparepart() ;
                k.setHarga(rs.getInt("harga_sparepart"));
                k.setKode_sparepart(rs.getString("kd_sparepart"));
                list.add(k);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
