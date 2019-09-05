package Controller;

import DAO.DAO_Mekanik;
import DAO.Model_DAO;
import Model.Mekanik;
import View.MMekanik;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Controller_Mekanik {
    
    MMekanik form;
    Model_DAO<Mekanik> model;
    List<Mekanik> list;
    String[] header;
    
    public Controller_Mekanik(MMekanik form){
        this.form = form;
        model = new DAO_Mekanik();
        list = model.getAll();
        header = new String[]{"Kode Mekanik", "Nama Mekanik", "Alamat", "No Telp"};
        
        form.getTblmekanik().setShowGrid(true);
        form.getTblmekanik().setShowVerticalLines(true);
        form.getTblmekanik().setGridColor(Color.black);
    }
    
    public void tampilurutankode(){
        Mekanik m = new Mekanik();
        model.autonumber(m);
        form.getTxtkdmekanik().setText(String.valueOf(model.autonumber(m)));
    }
     
    public void reset(){
        form.getTxtnmmekanik().setText("");
        form.getTxtalamatmekanik().setText("");
        form.getTxtnotelpmekanik().setText("");
        tampilurutankode();
        isiTable();
    }
     
    public void isiTable(){
        list = model.getAll();
        
        DefaultTableModel tableModel = new  DefaultTableModel(new Object[][]{}, header){
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return false;
            }
        };
        
        Object[] data = new Object[header.length];
        for (Mekanik m : list){
            data[0] = m.getKdmekanik();
            data[1] = m.getNmmekanik();
            data[2] = m.getAlamatmekanik();
            data[3] = m.getNotelpmekanik();
            tableModel.addRow(data);
        }
        form.getTblmekanik().setModel(tableModel);
    }
     
    public void isiField(int row){
        form.getTxtkdmekanik().setText(String.valueOf(list.get(row).getKdmekanik()));
        form.getTxtnmmekanik().setText(list.get(row).getNmmekanik());
        form.getTxtalamatmekanik().setText(list.get(row).getAlamatmekanik());
        form.getTxtnotelpmekanik().setText(list.get(row).getNotelpmekanik());
    }
      
    public void insert(){
        Mekanik m = new Mekanik();
        m.setKdmekanik(Integer.parseInt(form.getTxtkdmekanik().getText()));
        m.setNmmekanik(form.getTxtnmmekanik().getText());
        m.setAlamatmekanik(form.getTxtalamatmekanik().getText());
        m.setNotelpmekanik(form.getTxtnotelpmekanik().getText());
        model.insert(m);
    }
    
    public void update(){
        Mekanik m = new Mekanik();
        m.setKdmekanik(Integer.parseInt(form.getTxtkdmekanik().getText()));
        m.setNmmekanik(form.getTxtnmmekanik().getText());
        m.setAlamatmekanik(form.getTxtalamatmekanik().getText());
        m.setNotelpmekanik(form.getTxtnotelpmekanik().getText());
        model.update(m);
    }
    
    public void delete(){
        if(!form.getTxtkdmekanik().getText().trim().isEmpty()){
            int kode = Integer.parseInt(form.getTxtkdmekanik().getText());
            model.delete(kode);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    
    public void isiTableCari(){
        list = model.getcari(form.getTxtkdmekanik().getText().trim());
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, header);
        Object[] data = new Object[header.length];
        for (Mekanik m : list){
            data[0] = m.getKdmekanik();
            data[1] = m.getNmmekanik();
            data[2] = m.getAlamatmekanik();
            data[3] = m.getNotelpmekanik();
            
            form.getTxtnmmekanik().setText(m.getNmmekanik());
            form.getTxtalamatmekanik().setText(m.getAlamatmekanik());
            form.getTxtnotelpmekanik().setText(m.getNotelpmekanik());
            
            tableModel.addRow(data);
        }
        form.getTblmekanik().setModel(tableModel);
    }

    public void reset2() {
        form.getTxtnmmekanik().setText("");
        form.getTxtalamatmekanik().setText("");
        form.getTxtnotelpmekanik().setText("");
    }
}
