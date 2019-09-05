package DAO;

import Koneksi.Database;
import Model.WO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DAO_WO implements Model_DAO<WO>{

    Connection connection;
    public DAO_WO(){
        connection = Database.KoneksiDB();
    }
    
    @Override
    public int autonumber(WO Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(WO Object) {
        PreparedStatement statement =null;
        try{
            String SELECT = "SELECT * FROM wo WHERE no_wo = ?";
            statement = connection.prepareStatement(SELECT);
            statement.setString(1, Object.getNowo());
            ResultSet rs = statement.executeQuery();
            if(rs.next()) //jika data sudah pernah disimpan
            {  
                JOptionPane.showMessageDialog(null,"Data sudah pernah disimpan!");
            }
            else //jika data belum pernah disimpan
            {
                //insert kedalam tabel buktipesan
                PreparedStatement statement2;
                String INSERT = "insert into wo(no_wo,tgl_wo,keluhan,jarak_tempuh,plat_motor,kd_mekanik) values(?,?,?,?,?,?)";
                statement2 = connection.prepareStatement(INSERT);
                statement2.setString(1, Object.getNowo());
                statement2.setString(2, Object.getTglwo());
                statement2.setString(3, Object.getKeluhan());
                statement2.setInt(4, Object.getJarak());
                statement2.setString(5, Object.getKdmotor());
                statement2.setString(6, Object.getKdmekanik());
                
                statement2.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data transaksi berhasil disimpan"); 
            }
        } catch (Exception e) {
                    e.printStackTrace();
        }
        finally
        {
            try
            {
                statement.close();
            }
            catch (Exception ex)
            {
                Logger.getLogger(DAO_WO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void insert_detiltransaksi(WO object) {
        PreparedStatement statement;
        try{
            //insert ke dalam tabel detil wo
            String INSERTDETIL = "insert into detil_wo(no_wo,kd_service,harga_service) values(?,?,?)";
            statement = connection.prepareStatement(INSERTDETIL);
            statement.setString(1, object.getNowo());
            statement.setString(2, object.getKdservice());
            statement.setInt(3, object.getHargaservice());
            statement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String autonumber2(){
        PreparedStatement statement= null;
        int nomor_berikutnya = 0;
        String urutan = "";
        try{
            String COUNTER = "SELECT ifnull(max(convert(right(no_wo, 5),signed integer)),0) as kode,"
                    + "ifnull(length(max(convert(right(no_wo, 5)+1, signed integer))),0) as panjang from wo";
            
            statement = connection.prepareStatement(COUNTER);
            ResultSet rs2 = statement.executeQuery();
            if (rs2.next()){
                nomor_berikutnya = rs2.getInt("kode") + 1;
                if(rs2.getInt("kode")!=0){
                    if(rs2.getInt("panjang")==1){
                        urutan = "WO" + "0000" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==2){
                        urutan = "WO" + "000" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==3){
                        urutan = "WO" + "00" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==4){
                        urutan = "WO" + "0" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==5){
                        urutan = "WO" + nomor_berikutnya;
                    }
                }
                else {
                    urutan = "WO" + "00001";
                }
            }else
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urutan;
    }
    
    public List<WO> isicombomekanik() {
        PreparedStatement statement=null;
        List<WO> list = null;
        try {
            list = new ArrayList<WO>();
            String TAMPILMEKANIK = "SELECT * FROM mekanik";
            statement = connection.prepareStatement(TAMPILMEKANIK);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                WO w = new WO();
                w.setKdmekanik(rs.getString("kd_mekanik"));
                w.setNamamekanik(rs.getString("nama_mekanik"));
                list.add(w);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<WO> isicomboplat() {
        PreparedStatement statement;
        List<WO> list = null;
        try {
            list = new ArrayList();
            String TAMPILPLAT = "SELECT * FROM motor order by plat_motor";
            statement = connection.prepareStatement(TAMPILPLAT);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                WO w = new WO();
                w.setPlatmotor(rs.getString("plat_motor"));
                list.add(w);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<WO> isicomboservice() {
        PreparedStatement statement =null;
        List<WO> list = null;
        try {
            list = new ArrayList<WO>();
            String TAMPILSERVICE = "SELECT * FROM service";
            statement = connection.prepareStatement(TAMPILSERVICE);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                WO w = new WO();
                w.setKdservice(rs.getString("kd_service"));
                w.setNamaservice(rs.getString("nama_service"));
                list.add(w);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public List<WO> getplat_motor(String id) {
        PreparedStatement statement = null;
        List<WO> list = null;
        try
        {
            list = new ArrayList<WO>();
            String CARIPLAT = "SELECT * FROM motor where plat_motor=?";
            statement = connection.prepareStatement(CARIPLAT);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                WO w = new WO();
                w.setMerk(rs.getString("merk"));
                w.setWarna(rs.getString("warna"));
                w.setTahun(rs.getString("tahun"));
                w.setNamapemilik(rs.getString("nama_pemilik"));
                w.setAlamat(rs.getString("alamat"));
                w.setNotelp(rs.getString("no_telp"));
                list.add(w);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<WO> getservice(String nama_service) {
        PreparedStatement statement;
        List<WO> list = null;
        try
        {
            list = new ArrayList();
            String CARISERVICE = "SELECT * FROM service where nama_service=?";
            statement = connection.prepareStatement(CARISERVICE);
            statement.setString(1, nama_service);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                WO w = new WO();
                w.setKdservice(rs.getString("kd_service"));
                list.add(w);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<WO> getsparepart(String nama_sparepart) {
        PreparedStatement statement;
        List<WO> list = null;
        try
        {
            list = new ArrayList();
            String CARISPAREPART = "SELECT * FROM sparepart where nama_sparepart=?";
            statement = connection.prepareStatement(CARISPAREPART);
            statement.setString(1, nama_sparepart);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                WO w = new WO();
                w.setKdsparepart(rs.getString("nama_sparepart"));
                list.add(w);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public void update(WO Object) {
        PreparedStatement statement = null;
        try{
            String CARI = "SELECT a.*, b.*, c.*, d.*, e.* FROM wo a, motor b, mekanik c, service d, sparepart e where a.kd_motor=b.kd_motor and  a.kd_mekanik=c.kd_mekanik and  a.kd_service=d.kd_service and  a.kd_sparepart=e.kd_sparepart and no_wo like ?";
            statement = connection.prepareStatement(CARI);
            statement.setString(1, Object.getNowo());
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                String UPDATE = "UPDATE wo SET tgl_wo=?,keluhan=?,jarak_tempuh=?, kd_motor=?,kd_mekanik=?, kd_sparepart=?, kd_service=? WHERE no_wo=?";
                statement = connection.prepareStatement(UPDATE);
                statement.setString(1, Object.getNowo());
                statement.setString(2, Object.getTglwo());
                statement.setString(3, Object.getKeluhan());
                statement.setString(4, Object.getKdmekanik());
                statement.setString(5, Object.getKdmotor());
                statement.setString(6, Object.getKdservice());
                statement.setString(7, Object.getKdsparepart());
                statement.setInt(8, Object.getJarak());
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            } 
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException ex){
                Logger.getLogger(DAO_WO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WO> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WO> getcari(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<WO> getNamaMekanik(String kode){
        PreparedStatement statement = null;
        List<WO> list = null;
        try{
            String GETNAMA = "SELECT * FROM mekanik WHERE kd_mekanik = ?";
            list = new ArrayList();
            statement = connection.prepareStatement(GETNAMA);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                WO w = new WO();
                w.setNamamekanik(rs.getString("nama_mekanik"));
                
                list.add(w);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return list;
    }
    
    public List<WO> getKdMekanik(String nama){
        PreparedStatement statement = null;
        List<WO> list = null;
        try{
            String GETNAMA = "SELECT * FROM mekanik WHERE nama_mekanik = ?";
            list = new ArrayList();
            statement = connection.prepareStatement(GETNAMA);
            statement.setString(1, nama);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                WO w = new WO();
                w.setKdmekanik(rs.getString("kd_mekanik"));
                
                list.add(w);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return list;
    }
    
    public List<WO> getHarga(String kode){
        PreparedStatement statement = null;
        List<WO> list = null;
        try{
            String HARGA = "SELECT harga_service FROM service WHERE kd_service = ?";
            list = new ArrayList();
            statement = connection.prepareStatement(HARGA);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                WO w = new WO();
                w.setHargaservice(rs.getInt("harga_service"));
                
                list.add(w);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return list;
    }
}
