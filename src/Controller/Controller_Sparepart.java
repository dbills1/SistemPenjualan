package Controller;

import DAO.DAO_Sparepart;
import DAO.Model_DAO;
import Model.Sparepart;
import View.MSparepart;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Controller_Sparepart {
    
    MSparepart form;
    DAO_Sparepart model;
    List<Sparepart> list;
    String[] header;
    
    public Controller_Sparepart(MSparepart form){
        this.form = form;
        model = new DAO_Sparepart();
        list = model.getAll();
        header = new String[]{"Kode Sparepart", "Nama Sparepart", "Harga Sparepart", "Stok"};
        
        form.getTblsparepart().setShowGrid(true);
        form.getTblsparepart().setShowVerticalLines(true);
        form.getTblsparepart().setGridColor(Color.BLACK);
    }
    
    public void tampilurutankode(){
        form.getTxtkdsparepart().setText(model.autonumber2());
    }
    
    public void reset(){
        form.getTxtnmsparepart().setText("");
        form.getTxthargasparepart().setText("");
        form.getTxtstok().setText("");
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
        for (Sparepart s : list){
            data[0] = s.getKdsparepart();
            data[1] = s.getNamasparepart();
            data[2] = s.getHargasparepart();
            data[3] = s.getStok();
            tableModel.addRow(data);
    }
        form.getTblsparepart().setModel(tableModel);
    }
    
    public void isiField(int row){
        form.getTxtkdsparepart().setText(String.valueOf(list.get(row).getKdsparepart()));
        form.getTxtnmsparepart().setText(list.get(row).getNamasparepart());
        form.getTxthargasparepart().setText(String.valueOf(list.get(row).getHargasparepart()));
        form.getTxtstok().setText(String.valueOf(list.get(row).getStok()));
    }
    
    public void insert(){
        Sparepart s = new Sparepart();
        s.setKdsparepart(form.getTxtkdsparepart().getText());
        s.setNamasparepart(form.getTxtnmsparepart().getText());
        s.setHargasparepart(Integer.parseInt(form.getTxthargasparepart().getText()));
        s.setStok(Integer.parseInt(form.getTxtstok().getText()));
        model.insert(s);
    }
    
    public void update(){
        Sparepart s = new Sparepart();
        s.setKdsparepart(form.getTxtkdsparepart().getText());
        s.setNamasparepart(form.getTxtnmsparepart().getText());
        s.setHargasparepart(Integer.parseInt(form.getTxthargasparepart().getText()));
        s.setStok(Integer.parseInt(form.getTxtstok().getText()));
        model.update(s);
    }
    
    public void delete(){
        if(!form.getTxtkdsparepart().getText().trim().isEmpty()){
            int kode = Integer.parseInt(form.getTxtkdsparepart().getText());
            model.delete(kode);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    
    public void isiTableCari(){
        list = model.getcari(form.getTxtkdsparepart().getText().trim());
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, header);
        Object[] data = new Object[header.length];
        for (Sparepart s : list){
            data[0] = s.getKdsparepart();
            data[1] = s.getNamasparepart();
            data[2] = s.getHargasparepart();
            data[3] = s.getStok();
            
            tableModel.addRow(data);
            
            form.getTxtnmsparepart().setText(s.getNamasparepart());
            form.getTxthargasparepart().setText(String.valueOf(s.getHargasparepart()));
            form.getTxtstok().setText(String.valueOf(s.getStok()));
        }
        form.getTblsparepart().setModel(tableModel);
    }
    
    public void reset2(){
        form.getTxtnmsparepart().setText("");
        form.getTxthargasparepart().setText("");
        form.getTxtstok().setText("");
    }
}
