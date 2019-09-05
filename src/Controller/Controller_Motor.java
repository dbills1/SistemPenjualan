package Controller;

import DAO.DAO_Motor;
import DAO.Model_DAO;
import Model.Motor;
import View.MMotor;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Controller_Motor {

    MMotor form;
    Model_DAO<Motor> model;
    List<Motor> list;
    String[] header;
    
    public Controller_Motor(MMotor form){
        this.form = form;
        model = new DAO_Motor();
        list = model.getAll();
        header = new String[]{"Kode Motor", "Plat Motor", "Merk", "Warna", "Tahun", "Nama Pemilik", "Alamat", "No Telp"};
        
        form.getTblmotor().setShowGrid(true);
        form.getTblmotor().setShowVerticalLines(true);
        form.getTblmotor().setGridColor(Color.BLACK);
    }
    
    public void tampilurutankode(){
        Motor m = new Motor();
        model.autonumber(m);
        form.getTxtkdmotor().setText(String.valueOf(model.autonumber(m)));
    }
    
    public void reset(){
        form.getTxtplatmotor().setText("");
        form.getTxtmerk().setText("");
        form.getTxtwarna().setText("");
        form.getTxttahun().setText("");
        form.getTxtnamapemilik().setText("");
        form.getTxtalamat().setText("");
        form.getTxtnotelp().setText("");
        form.getTxtkdmotor().requestFocus();
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
        for (Motor m : list){
            data[0] = m.getKdmotor();
            data[1] = m.getPlatmotor();
            data[2] = m.getMerk();
            data[3] = m.getWarna();
            data[4] = m.getTahun();
            data[5] = m.getNamapemilik();
            data[6] = m.getAlamat();
            data[7] = m.getNotelp();
            tableModel.addRow(data);
        }
        form.getTblmotor().setModel(tableModel);
    }
    
    public void isiField(int row){
        form.getTxtkdmotor().setText(String.valueOf(list.get(row).getKdmotor()));
        form.getTxtplatmotor().setText(list.get(row).getPlatmotor());
        form.getTxtmerk().setText(list.get(row).getMerk());
        form.getTxtwarna().setText(list.get(row).getWarna());
        form.getTxttahun().setText(list.get(row).getTahun());
        form.getTxtnamapemilik().setText(list.get(row).getNamapemilik());
        form.getTxtalamat().setText(list.get(row).getAlamat());
        form.getTxtnotelp().setText(list.get(row).getNotelp());
    }
    
    public void insert(){
        Motor m = new Motor();
        m.setKdmotor(Integer.parseInt(form.getTxtkdmotor().getText()));
        m.setPlatmotor(form.getTxtplatmotor().getText());
        m.setMerk(form.getTxtmerk().getText());
        m.setWarna(form.getTxtwarna().getText());
        m.setTahun(form.getTxttahun().getText());
        m.setNamapemilik(form.getTxtnamapemilik().getText());
        m.setAlamat(form.getTxtalamat().getText());
        m.setNotelp(form.getTxtnotelp().getText());
        model.insert(m);
    }
    
    public void update(){
        Motor m = new Motor();
        m.setKdmotor(Integer.parseInt(form.getTxtkdmotor().getText()));
        m.setPlatmotor(form.getTxtplatmotor().getText());
        m.setMerk(form.getTxtmerk().getText());
        m.setWarna(form.getTxtwarna().getText());
        m.setTahun(form.getTxttahun().getText());
        m.setNamapemilik(form.getTxtnamapemilik().getText());
        m.setAlamat(form.getTxtalamat().getText());
        m.setNotelp(form.getTxtnotelp().getText());
        
        model.update(m);
    }
    
    public void delete(){
        if(!form.getTxtkdmotor().getText().trim().isEmpty()){
            int kode = Integer.parseInt(form.getTxtkdmotor().getText());
            model.delete(kode);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    
    public void isiTableCari(){
        list = model.getcari(form.getTxtkdmotor().getText().trim());
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, header);
        Object[] data = new Object[header.length];
        for (Motor m : list){
            data[0] = m.getKdmotor();
            data[1] = m.getPlatmotor();
            data[2] = m.getMerk();
            data[3] = m.getWarna();
            data[4] = m.getTahun();
            data[5] = m.getNamapemilik();
            data[6] = m.getAlamat();
            data[7] = m.getNotelp();
            
            tableModel.addRow(data);
            
            form.getTxtplatmotor().setText(m.getPlatmotor());
            form.getTxtmerk().setText(m.getMerk());
            form.getTxtwarna().setText(m.getWarna());
            form.getTxttahun().setText(m.getTahun());
            form.getTxtnamapemilik().setText(m.getNamapemilik());
            form.getTxtalamat().setText(m.getAlamat());
            form.getTxtnotelp().setText(m.getNotelp());
            
        }
        form.getTblmotor().setModel(tableModel);
    }

    public void reset2() {
        form.getTxtplatmotor().setText("");
        form.getTxtmerk().setText("");
        form.getTxtwarna().setText("");
        form.getTxttahun().setText("");
        form.getTxtnamapemilik().setText("");
        form.getTxtalamat().setText("");
        form.getTxtnotelp().setText("");
    }
}

