package Controller;

import DAO.DAO_WO;
import DAO.Model_DAO;
import Model.WO;
import View.TWo;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import static java.util.Collections.list;
import java.util.HashMap;
import net.sf.jasperreports.engine.util.JRLoader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.view.JasperViewer;

public class Controller_TWO {
    TWo form;
    DAO_WO model;
    List<WO> list;
    
    String[] header = new String[]{"No WO","Tanggal WO", "Plat Motor", "Nama Pemilik", "Service"};
    DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, header){
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }        
    };
    
    public Controller_TWO(TWo form){
        this.form = form;
        model = new DAO_WO();
        form.getTblwo().setModel(tableModel);
        form.getTblwo().setShowGrid(true);
        form.getTblwo().setShowVerticalLines(true);
        form.getTblwo().setGridColor(Color.gray);
    }
    
    public void tampilurutankode(){
        form.getTxtnowo().setText(String.valueOf(model.autonumber2()));
    }
    
    public void tampilurutankode2() throws SQLException
    {
        if(form.getCmboservice().getSelectedIndex() > 0)
        {
                    form.getCmboservice().getSelectedItem().toString();
            
        }
    }
    
    public void reset(){
        SimpleDateFormat tgl = new SimpleDateFormat("yyy-MM-dd");
        form.getTxttglwo().setText(String.valueOf(tgl.format(new Date())));
        
        form.getCmbomekanik().setSelectedIndex(0);
        form.getTxtkeluhan().setText("");
        form.getCmboplatmtr().setSelectedIndex(0);
        form.getCmboservice().setSelectedIndex(0);
        
        isicombomekanik();
        isicomboplat();
        isicomboservice();
        isiTable();
        tampilurutankode();
        

        tableModel.setRowCount(0);
        form.getTxtnowo().requestFocus();
    }
    
    public void resetrow(){
        int[] selectedRows = form.getTblwo().getSelectedRows();
        if(selectedRows.length > 0){
            for(int i = selectedRows.length - 1; i>=0; i--){
                tableModel.removeRow(selectedRows[i]);
            }
        }
        form.getTxtnowo().requestFocus();
    }
    
    public void isiTable() {
        int jmlbaris = tableModel.getRowCount();
        int i,datasama = 0;
        int stok=1;
        
        if(!form.getTxtnowo().getText().isEmpty()) {
            if(jmlbaris == 0) {
                //jika posisi jtable belum ada isi sama sekali
                tableModel.addRow(new Object[]{
                    form.getTxtnowo().getText(),
                    form.getTxttglwo().getText(),
                    form.getCmboplatmtr().getSelectedItem().toString(),
                    form.getTxtnmpemilik().getText(),
                    form.getCmboservice().getSelectedItem().toString()
                });
            } else {
                //jika sudah ada didalam jtable, maka cek validasi
                //tidak boleh add kode barang yang sama
                for(i=0; i<jmlbaris; i++) {
                    if(form.getTxtnowo().getText().equals(tableModel.getValueAt(i, 2).toString())) {
                        datasama = 1;
                        JOptionPane.showMessageDialog(null, "No Wo " + tableModel.getValueAt(i, 2).toString()
                                + " sudah pernah ada, dan akan diupdate dengan data tebaru");
                        
                        tableModel.setValueAt(form.getTxtnowo().getText(), i, 0);
                        tableModel.setValueAt(form.getTxttglwo().getText(), i, 1);
                        tableModel.setValueAt(form.getCmboplatmtr().getSelectedItem().toString(), i, 2);
                        tableModel.setValueAt(form.getTxtnmpemilik().getText(), i, 3);
                        tableModel.setValueAt(form.getCmboservice().getSelectedItem().toString(), i, 4);
                        break;
                    } else {
                        datasama = 0;
                    }
                }
                //jika kode barang belum pernah ada didalam list, maka add data ke dalam jtable
                if (datasama == 0) {
                    tableModel.addRow(new Object[] {
                        form.getTxtnowo().getText(),
                        form.getTxttglwo().getText(),
                        form.getCmboplatmtr().getSelectedItem().toString(),
                        form.getTxtnmpemilik().getText(),
                        form.getCmboservice().getSelectedItem().toString()
                    });
                }
            }
        }
    }
    
    public void isiField(int row){
        form.getCmboplatmtr().setSelectedItem(tableModel.getValueAt(row, 2).toString());
        form.getTxtnmpemilik().setText(String.valueOf(tableModel.getValueAt(row, 3)));
    }
    
    public void isicombomekanik(){
        form.getCmbomekanik().removeAllItems();
        form.getCmbomekanik().addItem("--PILIH--");
        for(WO w : model.isicombomekanik())
        {
            form.getCmbomekanik().addItem(w.getNamamekanik());
        }
    }
    
    public void isicomboplat(){
        form.getCmboplatmtr().removeAllItems();
        form.getCmboplatmtr().addItem("--PILIH--");
        for(WO w : model.isicomboplat()){
            form.getCmboplatmtr().addItem(w.getPlatmotor());
        }
    }
    
    public void tampilplat(){
        
        if(form.getCmboplatmtr().getSelectedIndex() != 0)
        {
            for (WO b : model.getplat_motor(form.getCmboplatmtr().getSelectedItem().toString()))
            {
                form.getTxtmerk().setText(b.getMerk());
                form.getTxtwarna().setText(b.getWarna());
                form.getTxttahun().setText(b.getTahun());
                form.getTxtnmpemilik().setText(b.getNamapemilik());
                form.getTxtalamat().setText(b.getAlamat());
                form.getTxtnotelp().setText(b.getNotelp().toString());
            }
        }
    }
    
    public void isicomboservice(){
        form.getCmboservice().removeAllItems();
        form.getCmboservice().addItem("--PILIH--");
        for(WO w : model.isicomboservice()){
            form.getCmboservice().addItem(w.getNamaservice());
        }
    }
    
    public void tampilservice(){
        for(WO w : model.getservice(form.getCmboservice().getSelectedItem().toString())){
            form.getCmboservice().setSelectedItem(w.getNamaservice());
        }
    }
    
    public String getKodeMekanik(String nama){
        String kode = null;
        for(WO w : model.getKdMekanik(nama)){
            kode = w.getKdmekanik();
        }
        
        return kode;
    }
    
    public void simpan_wo(){
        WO w = new WO();
        w.setNowo(form.getTxtnowo().getText());
        w.setTglwo(form.getTxttglwo().getText());
        w.setKeluhan(form.getTxtkeluhan().getText());
        w.setJarak(Integer.parseInt(form.getTxtjarak().getText()));
        w.setKdmotor(form.getCmboplatmtr().getSelectedItem().toString());
        w.setKdmekanik(getKodeMekanik(form.getCmbomekanik().getSelectedItem().toString()));
        model.insert(w);
    }
    
    public void simpan_detilwo() {
        int jmlbaris = tableModel.getRowCount();
        int i=0;
        for (i=0; i<jmlbaris; i++) {
            WO w = new WO();
            
            w.setNowo(tableModel.getValueAt(i, 0).toString());
            w.setKdservice(getKdService(tableModel.getValueAt(i, 4).toString()));
            w.setHargaservice(getHarga(getKdService(tableModel.getValueAt(i, 4).toString())));
            model.insert_detiltransaksi(w);
            
        }
    }
    
    public int getHarga(String kode){
        int harga = 0;
        for(WO w : model.getHarga(kode)){
            harga = w.getHargaservice();
        }
        
        return harga;
    }
    
    public String getKdService(String nama){
        String kode = null;
        list = model.getservice(nama);
        for(WO w : list){
            kode = w.getKdservice();
        }
        
        return kode;
    }

    public void update(){
        WO w = new WO();
        w.setNowo(form.getTxtnowo().getText());
        w.setTglwo(form.getTxttglwo().getText());
        w.setKeluhan(form.getTxtkeluhan().getText());
        w.setJarak(Integer.parseInt(form.getTxtjarak().getText()));
        w.setPlatmotor(form.getCmboplatmtr().getSelectedItem().toString());
        model.update(w);
    }

    public void reset2() {
        isicomboservice();
    }
    
    public void reset3(){
        form.getTxtnmpemilik().setText("");
        form.getTxtmerk().setText("");
        form.getTxtwarna().setText("");
        form.getTxttahun().setText("");
        form.getTxtalamat().setText("");
        form.getTxtnotelp().setText("");
        form.getTxtjarak().setText("");
    }
    
    public void cetak(){
        try{
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/ReportWO.jasper";
            HashMap parameter = new HashMap();
            parameter.put("no_wo", (form.getTxtnowo().getText()));
            
            JasperReport jp = (JasperReport) JRLoader.loadObject(path);
            JasperPrint print = JasperFillManager.fillReport(jp,parameter,conn);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
            OutputStream output = new FileOutputStream(new File("C:\\Report\\WorkOrder-"+(form.getTxtnowo().getText())+ ".pdf"));
            JasperExportManager.exportReportToPdfStream(print, output);
            
            File xlxs = new File("C:\\Report\\WorkOrder-"+(form.getTxtnowo().getText())+ ".xlsx");
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE, xlxs);
            xlsxExporter.exportReport();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Data Tidak Dapat dicetak!"+ex.getMessage(),
            "Cetak Data",JOptionPane.ERROR_MESSAGE);
        }
    }
}