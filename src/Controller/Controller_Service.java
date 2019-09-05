package Controller;

import DAO.DAO_Service;
import DAO.Model_DAO;
import Model.Service;
import View.MService;
import java.awt.Color;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Controller_Service {
    
    MService form;
    Model_DAO<Service> model;
    List<Service> list;
    String[] header;
    
    public Controller_Service(MService form){
        this.form = form;
        model = new DAO_Service();
        list = model.getAll();
        header = new String[]{"Kode Service","Nama Service", "Harga Service"};
        
        form.getTblservice().setShowGrid(true);
        form.getTblservice().setShowVerticalLines(true);
        form.getTblservice().setGridColor(Color.BLACK);
    }
    
    public void tampilurutankode(){
        Service s = new Service();
        model.autonumber(s);
        form.getTxtkdservice().setText(String.valueOf(model.autonumber(s)));
    }
    
    public void reset(){
        form.getTxtnmservice().setText("");
        form.getTxthargaservice().setText("");
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
        for (Service s : list){
            data[0] = s.getKdservice();
            data[1] = s.getNmservice();
            data[2] = s.getHargaservice();
            tableModel.addRow(data);
    }
        form.getTblservice().setModel(tableModel);
    }
    
    public void isiField(int row){
        form.getTxtkdservice().setText(String.valueOf(list.get(row).getKdservice()));
        form.getTxtnmservice().setText(list.get(row).getNmservice());
        form.getTxthargaservice().setText(String.valueOf(list.get(row).getHargaservice()));
    }
    
    public void insert(){
        Service s = new Service();
        s.setKdservice(Integer.parseInt(form.getTxtkdservice().getText()));
        s.setNmservice(form.getTxtnmservice().getText());
        s.setHargaservice(Integer.parseInt(form.getTxthargaservice().getText()));
        model.insert(s);
    }
    
    public void update(){
        Service s = new Service();
        s.setKdservice(Integer.parseInt(form.getTxtkdservice().getText()));
        s.setNmservice(form.getTxtnmservice().getText());
        s.setHargaservice(Integer.parseInt(form.getTxthargaservice().getText()));
        model.update(s);
    }
    
    public void delete(){
        if(!form.getTxtkdservice().getText().trim().isEmpty()){
            int kode = Integer.parseInt(form.getTxtkdservice().getText());
            model.delete(kode);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    
    public void isiTableCari(){
        list = model.getcari(form.getTxtkdservice().getText().trim());
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, header);
        Object[] data = new Object[header.length];
        for (Service s : list){
            data[0] = s.getKdservice();
            data[1] = s.getNmservice();
            data[2] = s.getHargaservice();
            
            tableModel.addRow(data);
            
            form.getTxtnmservice().setText(s.getNmservice());
            form.getTxthargaservice().setText(String.valueOf(s.getHargaservice()));
        }
        form.getTblservice().setModel(tableModel);
    }

    public void reset2() {
        form.getTxtnmservice().setText("");
        form.getTxthargaservice().setText("");
    }
}
