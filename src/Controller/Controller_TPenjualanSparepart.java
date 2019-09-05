package Controller;

import DAO.DAO_TPenjualanSparepart;
import Model.TPenjualanSparepart;
import View.TPenjualan;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
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

/**
 *
 * @author sutan
 */
public class Controller_TPenjualanSparepart {
    TPenjualan form;
    DAO_TPenjualanSparepart model;
    List<TPenjualanSparepart> list;
    
    String[] header = new String[]{"Kode Sparepart", "Nama Sparepart", "Qty", "Harga"};
    DefaultTableModel tableModel1 = new DefaultTableModel(new Object[][]{}, header){
        public boolean isCellEditable(int rowIndex, int columnIndex){
            return false;
        }
    };
    
    public Controller_TPenjualanSparepart(TPenjualan form){
        this.form = form;
        model = new DAO_TPenjualanSparepart();
        
        form.getTblrincian().setModel(tableModel1);
        form.getTblrincian().setShowGrid(true);
        form.getTblrincian().setShowVerticalLines(true);
        form.getTblrincian().setGridColor(Color.blue);
    }
    
    public void reset(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        form.getTxttgl().setText(format.format(new Date()));
        
        tableModel1.setRowCount(0);
        tampilurutkode();
        isiComboSparepart();
    }

    private void tampilurutkode() {
        form.getTxtno().setText(model.autonumber2());
    }

    private void isiComboSparepart() {
        form.getCmbsparepart().removeAllItems();
        form.getCmbsparepart().addItem("-PILIH-");
        for(TPenjualanSparepart k : model.isicombosparepart()){
            form.getCmbsparepart().addItem(k.getNama_sparepart());
        }
    }
    
    public void tampilstok(){
        for(TPenjualanSparepart k : model.getsparepart(form.getCmbsparepart().getSelectedItem().toString())){
            form.getTxtstok().setText(String.valueOf(k.getStok()));
        }
    }
    
    public String tampilkdsparepart(){
        String kode = null;
        if(form.getCmbsparepart().getSelectedIndex() > 0){
            for(TPenjualanSparepart k : model.getkdsparepart(form.getCmbsparepart().getSelectedItem().toString())){
                kode = k.getKode_sparepart();
                System.out.println(kode);
            }
        }
        
        return kode;
    }
    
    public int getHargaSparepart(){
        int harga = 0;
        if(form.getCmbsparepart().getSelectedIndex() > 0){
            for(TPenjualanSparepart k : model.getkdsparepart(form.getCmbsparepart().getSelectedItem().toString())){
                harga = k.getHarga();
                System.out.println(harga);
            }
        }
        
        return harga;
    }
    
    public void isiTableSparepart(){
        int jmlbaris = tableModel1.getRowCount();
        int i,datasama = 0;
        int stok=1;
        int hitung = 0;
        
        if((form.getCmbsparepart().getSelectedIndex() > 0) && (!form.getTxtqty().getText().isEmpty())) {
            if(Integer.valueOf(form.getTxtstok().getText()) <= 0){
                JOptionPane.showMessageDialog(null, "Stok Kosong!");
            } else if(Integer.parseInt(form.getTxtqty().getText()) > Integer.parseInt(form.getTxtstok().getText())){
                JOptionPane.showMessageDialog(null, "Stok yang tersedia hanya " + form.getTxtstok().getText());
            } else {
                if(jmlbaris == 0) {
                    //jika posisi jtable belum ada isi sama sekali
                    tableModel1.addRow(new Object[]{
                        tampilkdsparepart(),
                        form.getCmbsparepart().getSelectedItem().toString(),
                        form.getTxtqty().getText(),
                        String.valueOf(getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText()))
                    });

//                    hitung = hitung + (getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText()));
//                    form.getTxttotal().setText(String.valueOf(hitung));


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
                        tableModel1.addRow(new Object[]{
                            tampilkdsparepart(),
                            form.getCmbsparepart().getSelectedItem().toString(),
                            form.getTxtqty().getText(),
                            String.valueOf(getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText()))
                        });

//                        hitung = Integer.parseInt(form.getTxttotal().getText()) + (getHargaSparepart() * Integer.parseInt(form.getTxtqty().getText()));
//                        form.getTxttotal().setText(String.valueOf(hitung));
                    }
                }
            }
        }
    }
        
    public void simpan(){
        TPenjualanSparepart t = new TPenjualanSparepart();
        t.setNo_penjualan(form.getTxtno().getText());
        t.setTanggal(form.getTxttgl().getText());
        
        model.insert(t);
    }
    
    public void simpan_detil(){
        for(int i = 0; i < tableModel1.getRowCount(); i++){
            TPenjualanSparepart t = new TPenjualanSparepart();
            t.setNo_penjualan(form.getTxtno().getText());
            t.setKode_sparepart(tableModel1.getValueAt(i, 0).toString());
            t.setQty(Integer.parseInt(tableModel1.getValueAt(i, 2).toString()));
            t.setHarga(Integer.parseInt(tableModel1.getValueAt(i, 3).toString()));
            
            model.insert_detil(t);
            model.update_stok(t);
        }
    }
    
    public void hitung_grand(){
        int total = 0;
        if(!form.getTxttotal().getText().isEmpty()){
            total = Integer.parseInt(form.getTxttotal().getText());
        }
        int jml_baris = tableModel1.getRowCount();
        int harga_sparepart = 0;
        for(int i = 0; i < jml_baris; i++){
            harga_sparepart = Integer.parseInt(tableModel1.getValueAt(i, 3).toString());
            total = total + harga_sparepart;
        }
        
        form.getTxttotal().setText(String.valueOf(total));
    }
    
    public void cetak(){
        try{
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/ReportKwitansiSparepart.jasper";
            HashMap parameter = new HashMap();
            parameter.put("no_penjualan", (form.getTxtno().getText()));
            
            JasperReport jp = (JasperReport) JRLoader.loadObject(path);
            JasperPrint print = JasperFillManager.fillReport(jp,parameter,conn);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
            OutputStream output = new FileOutputStream(new File("C:\\Report\\Kwitansi Sparepart-"+(form.getTxtno().getText())+ ".pdf"));
            JasperExportManager.exportReportToPdfStream(print, output);
            
            File xlxs = new File("C:\\Report\\Kwitansi Sparepart-"+(form.getTxtno().getText())+ ".xlsx");
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
