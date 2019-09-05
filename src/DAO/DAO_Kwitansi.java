package DAO;

import Koneksi.Database;
import Model.Kwitansi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DAO_Kwitansi implements Model_DAO<Kwitansi>{
    
    Connection connection;
    public DAO_Kwitansi(){
        connection = Database.KoneksiDB();
    }

    @Override
    public void insert(Kwitansi Object) {
        PreparedStatement statement;
        try{
            String SELECT = "select * from kwitansi where no_kwitansi=?";
            statement = connection.prepareStatement(SELECT);
            statement.setString(1, Object.getNokwitansi());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Data sudah pernah disimpan");
            }else{
                PreparedStatement statement1;
                String INSERT = "insert into kwitansi(no_kwitansi,no_wo,tgl_kwitansi) values(?,?,?)";
                statement1 = connection.prepareStatement(INSERT);
                statement1.setString(1, Object.getNokwitansi());
                statement1.setString(2, Object.getNowo());
                statement1.setString(3, Object.getTglkwitansi());
                statement1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data kwitansi sudah berhasil disimpan");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void insert_detilkwitansi(Kwitansi object){
        PreparedStatement statement;
        try{
            String INSERTDETIL = "INSERT INTO detil_kwitansi(no_kwitansi, kd_sparepart, jumlahpesan, hargapesan) VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(INSERTDETIL);
            statement.setString(1, object.getNokwitansi());
            statement.setString(2, object.getKodesparepart());
            statement.setInt(3, object.getQty());
            statement.setInt(4, object.getHargasparepart());
            statement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String autonumber2(){
        PreparedStatement statement;
        int nomor_berikutnya = 0;
        String urutan = "";
        try{
            String COUNTER = "SELECT ifnull(max(convert(right(no_kwitansi, 5),signed integer)),0) as kode,"
                    + "ifnull(length(max(convert(right(no_kwitansi, 5)+1, signed integer))),0) as panjang from kwitansi";
            
            statement = connection.prepareStatement(COUNTER);
            ResultSet rs2 = statement.executeQuery();
            if (rs2.next()){
                nomor_berikutnya = rs2.getInt("kode") + 1;
                if(rs2.getInt("kode")!=0){
                    if(rs2.getInt("panjang")==1){
                        urutan = "KW" + "0000" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==2){
                        urutan = "KW" + "000" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==3){
                        urutan = "KW" + "00" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==4){
                        urutan = "KW" + "0" + nomor_berikutnya;
                    } else if (rs2.getInt("panjang")==5){
                        urutan = "KW" + nomor_berikutnya;
                    }
                }
                else {
                    urutan = "KW" + "00001";
                }
            }else
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urutan;
    }

    public List<Kwitansi> isicombowo(){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String TAMPILWO = "select * from wo order by no_wo";
            statement = connection.prepareStatement(TAMPILWO);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Kwitansi k = new Kwitansi();
                k.setNowo(rs.getString("no_wo"));
                list.add(k);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> getnowo(String nowo){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String CARINOWO = "select * from wo where no_wo= ?";
            statement = connection.prepareStatement(CARINOWO);
            statement.setString(1, nowo);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Kwitansi k = new Kwitansi();
                k.setKodewo(rs.getString("no_wo"));
                list.add(k);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Kwitansi> isicomboplat(){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String TAMPILPLAT = "select * from motor order by plat_motor";
            statement = connection.prepareStatement(TAMPILPLAT);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Kwitansi k = new Kwitansi();
                k.setPlat(rs.getString("plat_motor"));
                list.add(k);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> getplat(String plat){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String CARIPLAT = "select * from motor  ";
            statement = connection.prepareStatement(CARIPLAT);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Kwitansi k = new Kwitansi();
                k.setKodeplat(rs.getString("plat_motor"));
                list.add(k);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> getnmmotor(Integer kode){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String CARIPLAT2 = "select * from motor where kd_motor= ?";
            statement = connection.prepareStatement(CARIPLAT2);
            statement.setInt(1, kode);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            }else{
                Kwitansi k = new Kwitansi() ;
                k.setKodeplat(rs.getString("merk"));
                list.add(k);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> isicomboservice(String kode){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String TAMPILSERVICE = "select * from service order by nama_service";
            statement = connection.prepareStatement(TAMPILSERVICE);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Kwitansi k = new Kwitansi();
                k.setNmservice(rs.getString("nama_service"));
                list.add(k);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> isicomboservice2(){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String TAMPILSERVICE = "select * from service order by nama_service";
            statement = connection.prepareStatement(TAMPILSERVICE);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Kwitansi k = new Kwitansi();
                k.setNmservice(rs.getString("nama_service"));
                list.add(k);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> getkdservice(String namaservice){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String CARISERVICE = "select * from service where nama_service= ?";
            statement = connection.prepareStatement(CARISERVICE);
            statement.setString(1, namaservice);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Kwitansi k = new Kwitansi() ;
                k.setKodeservice(rs.getString("kd_service"));
                k.setNmservice(rs.getString("nama_service"));
                k.setHargaservice(rs.getInt("harga_service"));
                list.add(k);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> isicombosparepart(){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String TAMPILSPAREPART = "select * from sparepart order by nama_sparepart";
            statement = connection.prepareStatement(TAMPILSPAREPART);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Kwitansi k = new Kwitansi();
                k.setNmsparepart(rs.getString("nama_sparepart"));
                list.add(k);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> isicombosparepart2(String kode){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String TAMPILSPAREPART = "select * from sparepart where kd_sparepart= ? order by nama_sparepart";
            statement = connection.prepareStatement(TAMPILSPAREPART);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Kwitansi k = new Kwitansi();
                k.setNmsparepart(rs.getString("nama_sparepart"));
                list.add(k);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> getkdsparepart(String namasparepart){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String CARISPAREPART = "select * from sparepart where nama_sparepart= ?";
            statement = connection.prepareStatement(CARISPAREPART);
            statement.setString(1, namasparepart);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Kwitansi k = new Kwitansi() ;
                k.setKodesparepart(rs.getString("kd_sparepart"));
                k.setNmsparepart(rs.getString("nama_sparepart"));
                k.setHargasparepart(rs.getInt("harga_sparepart"));
                k.setStok(rs.getInt("stok"));
                list.add(k);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Kwitansi> getsparepart(String nama){
        PreparedStatement statement;
        List<Kwitansi> list = null;
        try{
            list = new ArrayList();
            String CARISPAREPART = "select * from sparepart where nama_sparepart= ?";
            statement = connection.prepareStatement(CARISPAREPART);
            statement.setString(1, nama);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            }else{
                Kwitansi k = new Kwitansi();
                k.setHargasparepart(rs.getInt("harga_sparepart"));
                k.setStok(rs.getInt("stok"));
                list.add(k);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public int autonumber(Kwitansi Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Kwitansi Object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Kwitansi> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Kwitansi> getcari(String key) {
        PreparedStatement statement = null;
        List<Kwitansi> list = null;
        try{
            String GETALL = "SELECT a.*, b.*, c.*, e.* FROM detil_wo a, motor b, service c, wo e WHERE e.no_wo = a.no_wo AND e.plat_motor = b.plat_motor AND a.kd_service = c.kd_service AND e.no_wo = ?";
            list = new ArrayList();
            statement = connection.prepareStatement(GETALL);
            statement.setString(1, key);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                System.out.println("Running");
                Kwitansi k = new Kwitansi();
                k.setPlat(rs.getString("e.plat_motor"));
                k.setNmservice(rs.getString("c.nama_service"));
                k.setNmmotor(rs.getString("b.nama_pemilik"));
                k.setHargaservice(rs.getInt("c.harga_service"));
                list.add(k);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return list;
    }
    
    public void update_stok(Kwitansi object){
        PreparedStatement statement;
        int stok = 0;
        try{
            String SELECTSTOK = "SELECT stok FROM sparepart WHERE kd_sparepart = ?";
            statement = connection.prepareStatement(SELECTSTOK);
            statement.setString(1, object.getKodesparepart());
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
                    statement2.setString(2, object.getKodesparepart());
                    statement2.executeUpdate();
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
