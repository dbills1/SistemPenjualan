package DAO;

import Koneksi.Database;
import Model.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DAO_Service implements Model_DAO<Service> {

    public DAO_Service() {
        connection = Database.KoneksiDB();
    }
    
    Connection connection;
    String INSERT = "INSERT INTO service(kd_service,nama_service,harga_service) values(?,?,?)";
    String UPDATE = "UPDATE service SET nama_service=?, harga_service=? WHERE kd_service=?";
    String DELETE = "DELETE FROM service WHERE kd_service=?";
    String SELECT = "SELECT * FROM service";
    String CARI = "SELECT * FROM service WHERE kd_service LIKE ?";
    String COUNTER = "SELECT max(kd_service) as kode FROM service";
    
    @Override
    public int autonumber(Service Object) {
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
    public void insert(Service Object) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CARI);
            statement.setInt(1, Object.getKdservice());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                JOptionPane.showMessageDialog(null,"Data sudah pernah di simpan!");
            else{
                PreparedStatement statement1 = null;
                statement1 = connection.prepareStatement(INSERT);
                statement1.setInt(1, Object.getKdservice());
                statement1.setString(2, Object.getNmservice());
                statement1.setInt(3, Object.getHargaservice());
                statement1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil di simpan!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                statement.close();
            }catch (SQLException ex) {
                Logger.getLogger(DAO_Service.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Service Object) {
         PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, Object.getNmservice());
            statement.setInt(2, Object.getHargaservice());
            statement.setInt(3, Object.getKdservice());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil di ubah!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO_Service.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(DAO_Service.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Service> getAll() {
        List<Service> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Service>();
            statement = connection.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Service s = new Service();
                s.setKdservice(rs.getInt("kd_service"));
                s.setNmservice(rs.getString("nama_service"));
                s.setHargaservice(rs.getInt("harga_service"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Service> getcari(String key) {
        List<Service> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Service>();
            statement = connection.prepareStatement(CARI);
            statement.setString(1, "%"+key+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Service s = new Service();
                s.setKdservice(rs.getInt("kd_service"));
                s.setNmservice(rs.getString("nama_service"));
                s.setHargaservice(rs.getInt("harga_service"));
                list.add(s);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
    

