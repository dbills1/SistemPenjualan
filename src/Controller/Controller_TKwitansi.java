package Controller;

import DAO.DAO_Kwitansi;
import Model.Kwitansi;
import View.TKwitansiwo;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Controller_TKwitansi {
    
    TKwitansiwo form;
    DAO_Kwitansi model;
    List<Kwitansi> list;
    
    String[] header;
    
    String[] header2 = new String[]{"Kode Sparepart", "Nama Sparepart", "Qty", "Harga"};
    DefaultTableModel tableModel1 = new DefaultTableModel(new Object[][]{}, header2){
        public boolean isCellEditable(int rowIndex, int columnIndex){
            return false;
        }
    };
    
    
    public Controller_TKwitansi(TKwitansiwo form){
        this.form = form;
        model = new DAO_Kwitansi();
        
        header = new String[]{"No Kwitansi", "Tanggal Kwitansi", "No WO", "Plat Motor", "Nama Pemilik", "Nama Service","Harga"};
        
        form.getTblkwitansiwo().setShowGrid(true);
        form.getTblkwitansiwo().setShowVerticalLines(true);
        form.getTblkwitansiwo().setGridColor(Color.blue);
        form.getTblsparepart().setModel(tableModel1);
        form.getTblsparepart().setShowGrid(true);
        form.getTblsparepart().setShowVerticalLines(true);
        form.getTblsparepart().setGridColor(Color.blue);
    }
    
    public void tampilurutankode(){
        form.getTxtnokwitansi().setText(model.autonumber2());
    }
    
    public void reset(){
        SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd");
        form.getTxttglkwitansiwo().setText(String.valueOf(tgl.format(new java.util.Date())));
        
        form.getTotal().setText("Rp. 0");
        form.getTxttotalservice().setText("");
        form.getTxttotalsparepart().setText("");
        form.getTxtqty().setText("");
        form.getTxtstok().setText("");
        
        tampilurutankode();
        isicombowo();
        isicombosparepart();
        
        
        tableModel1.setRowCount(0);
        form.getTxtnokwitansi().requestFocus();
        form.getTxttglkwitansiwo().setEditable(false);
    }
    
    public void reset2(){
        form.getTxttotalservice().setText("Rp. 0");
        form.getTxttotalsparepart().setText("Rp. 0");
        form.getTxtnokwitansi().requestFocus();
    }
    
    public void reset3(){
        form.getTxtqty().setText("");
        form.getTxtstok().setText("");
        form.getTxtnokwitansi().requestFocus();
    }
    
    public void isiTableKwitansi(){
        list = model.getcari(form.getCmbwo().getSelectedItem().toString());
        int hitung = 0;
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, header);
        
        Object[] data = new Object[header.length];
        for(Kwitansi k : list){
            System.out.println("Starting Looping Table Kwitansi -> " + form.getCmbwo().getSelectedItem().toString());
            data[0] = form.getTxtnokwitansi().getText();
            data[1] = form.getTxttglkwitansiwo().getText();
            data[2] = form.getCmbwo().getSelectedItem().toString();
            data[3] = k.getPlat();
            data[4] = k.getNmmotor();
            data[5] = k.getNmservice();
            data[6] = k.getHargaservice();
            hitung = hitung + k.getHargaservice();
            tableModel.addRow(data);
        }
        form.getTxttotalservice().setText(String.valueOf(hitung));
        form.getTblkwitansiwo().setModel(tableModel);
    }
    
    public void isicombowo(){
        form.getCmbwo().removeAllItems();
        form.getCmbwo().addItem("-PILIH-");
        for(Kwitansi k : model.isicombowo()){
            form.getCmbwo().addItem(k.getNowo());
        }
    }
    
    public void isicombosparepart(){
        form.getCmbosparepart().removeAllItems();
        form.getCmbosparepart().addItem("-PILIH-");
        for(Kwitansi k : model.isicombosparepart()){
            form.getCmbosparepart().addItem(k.getNmsparepart());
        }
    }
    
    public String tampilkdsparepart(){
        String kode = null;
        if(form.getCmbosparepart().getSelectedIndex() > 0){
            for(Kwitansi k : model.getkdsparepart(form.getCmbosparepart().getSelectedItem().toString())){
                kode = k.getKodesparepart();
                System.out.println(kode);
            }
        }
        
        return kode;
    }
    
    public int getHargaSparepart(){
        int harga = 0;
        if(form.getCmbosparepart().getSelectedIndex() > 0){
            for(Kwitansi k : model.getkdsparepart(form.getCmbosparepart().getSelectedItem().toString())){
                harga = k.getHargasparepart();
                System.out.println(harga);
            }
        }
        
        return harga;
    }
    
    public void tampilstok(){
        for(Kwitansi k : model.getsparepart(form.getCmbosparepart().getSelectedItem().toString())){
            form.getTxtstok().setText(String.valueOf(k.getStok()));
        }
    }
    
    public void isiTableSparepart(){
        int jmlbaris = tableModel1.getRowCount();
        int i,datasama = 0;
        int stok=1;
        int hitung = 0;
        
        if((form.getCmbosparepart().getSelectedIndex() > 0) && (!form.getTxtqty().getText().isEmpty())){
            if(Integer.valueOf(form.getTxtstok().getText()) <= 0){
                JOptionPane.showMessageDialog(null, "Stok Kosong!");
            } else if(Integer.parseInt(form.getTxtqty().getText()) > Integer.parseInt(form.getTxtstok().getText())){
                JOptionPane.showMessageDialog(null, "Stok yang tersedia hanya " + form.getTxtstok().getText());
            } else {
                if(jmlbaris == 0) {
                    //jika posisi jtable belum ada isi sama sekali
                    tableModel1.addRow(new Object[]{
                        tampilkdsparepart(),
                        form.getCmbosparepart().getSelectedItem().toString(),
                        form.getTxtqty().getText(),
                        String.valueOf(getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText()))
                    });

                    hitung = hitung + (getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText()));
                    form.getTxttotalsparepart().setText(String.valueOf(hitung));


                } else {
                    //jika sudah ada didalam jtable, maka cek validasi
                    //tidak boleh add kode barang yang sama
                    for(i=0; i<jmlbaris; i++) {
                        if(tampilkdsparepart().equals(tableModel1.getValueAt(i, 0).toString())) {
                            datasama = 1;
                            JOptionPane.showMessageDialog(null, "No Wo " + tableModel1.getValueAt(i, 2).toString()
                                    + " sudah pernah ada, dan akan diupdate dengan data tebaru");

                            tableModel1.setValueAt(form.getTxtqty().getText(), i, 2);
                            tableModel1.setValueAt(String.valueOf(getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText())), i, 3);
                            break;
                        } else {
                            datasama = 0;
                        }
                    }
                    //jika kode barang belum pernah ada didalam list, maka add data ke dalam jtable
                    if (datasama == 0) {
                        tableModel1.addRow(new Object[] {
                            tampilkdsparepart(),
                            form.getCmbosparepart().getSelectedItem().toString(),
                            form.getTxtqty().getText(),
                            String.valueOf(getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText()))
                        });

                        hitung = Integer.parseInt(form.getTxttotalsparepart().getText()) + (getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText()));
                        form.getTxttotalsparepart().setText(String.valueOf(hitung));
                    }
                }
            }
        }
    }
    
    public void simpan_kwitansi(){
        Kwitansi k = new Kwitansi();
        k.setNowo(form.getCmbwo().getSelectedItem().toString());
        k.setTglkwitansi(form.getTxttglkwitansiwo().getText());
        k.setNokwitansi(form.getTxtnokwitansi().getText());
        model.insert(k);
    }
    
    public void simpan_detilkwitansi(){
        Kwitansi k = new Kwitansi();
        
        int jml_baris = tableModel1.getRowCount();
        for(int i = 0; i < jml_baris; i++){
            k.setNokwitansi(form.getTxtnokwitansi().getText());
            k.setKodesparepart(tableModel1.getValueAt(i, 0).toString());
            k.setQty(Integer.parseInt(tableModel1.getValueAt(i, 2).toString()));
            k.setHargasparepart(Integer.parseInt(tableModel1.getValueAt(i, 3).toString()));
            
            model.insert_detilkwitansi(k);
            model.update_stok(k);
        }
    }
    
    public void hitung_grand(){
        int total = Integer.parseInt(form.getTxttotalservice().getText());
        int jml_baris = tableModel1.getRowCount();
        int harga_sparepart = 0;
        for(int i = 0; i < jml_baris; i++){
            harga_sparepart = Integer.parseInt(tableModel1.getValueAt(i, 3).toString());
            total = total + harga_sparepart;
        }
        
        form.getTotal().setText(String.valueOf(total));
    }

    public void resetRow() {
        int[] selectedRows = form.getTblsparepart().getSelectedRows();
        if(selectedRows.length > 0){
            for(int i = selectedRows.length - 1; i>=0; i--){
                tableModel1.removeRow(selectedRows[i]);
            }
        }
    }
    
    public void cetak(){
        try{
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/ReportKwitans.jasper";
            HashMap parameter = new HashMap();
            parameter.put("no_wo", (form.getCmbwo().getSelectedItem().toString()));
            
            JasperReport jp = (JasperReport) JRLoader.loadObject(path);
            JasperPrint print = JasperFillManager.fillReport(jp,parameter,conn);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
            OutputStream output = new FileOutputStream(new File("C:\\Report\\Kwitansi-"+(form.getTxtnokwitansi().getText())+ ".pdf"));
            JasperExportManager.exportReportToPdfStream(print, output);
            
            File xlxs = new File("C:\\Report\\Kwitansi-"+(form.getTxtnokwitansi().getText())+ ".xlsx");
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