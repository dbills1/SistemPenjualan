package Controller;

import Koneksi.Database;
import View.LapPembayaran;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Controller_LapPembayaran {
    LapPembayaran form;
    
    public Controller_LapPembayaran(LapPembayaran form){
        this.form = form;
    }
    
    public void reset(){
        Date date = new Date();
        form.getTgl_awal().setDate(date);
        form.getTgl_akhir().setDate(null);
    }
    
    public void preview(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            Connection conn = Database.KoneksiDB();
            String path = "src/Report/LaporanPembayaran.jasper";
            HashMap tanggalMap = new HashMap();
            tanggalMap.put("tgl_awal", format.format(form.getTgl_awal().getDate()));
            tanggalMap.put("tgl_akhir", format.format(form.getTgl_akhir().getDate()));

            JasperReport jp = (JasperReport) JRLoader.loadObject(path);
            JasperPrint print = JasperFillManager.fillReport(jp, tanggalMap, conn);
            JasperViewer.viewReport(print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak! " + e.getMessage(), 
                    "Cetak Data", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void excel(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            Connection conn = Database.KoneksiDB();
            String path = "src/Report/LaporanPembayaran.jasper";
            File excel = new File("C:/Report/LaporanPembayaran " 
                    + format.format(form.getTgl_awal().getDate()) + " - " + format.format(form.getTgl_akhir().getDate()) + ".xlsx");
            HashMap tanggalMap = new HashMap();
            tanggalMap.put("tgl_awal", format.format(form.getTgl_awal().getDate()));
            tanggalMap.put("tgl_akhir", format.format(form.getTgl_akhir().getDate()));
            
            JasperPrint print = JasperFillManager.fillReport(path, tanggalMap, conn);
            
            OutputStream output = new FileOutputStream(new File("C:/Report/LaporanPembayaran " 
                    + format.format(form.getTgl_awal().getDate()) + " - " + format.format(form.getTgl_akhir().getDate()) + ".pdf"));
            JasperExportManager.exportReportToPdfStream(print, output);
            
            JRXlsxExporter xlsx = new JRXlsxExporter();
            xlsx.setParameter(JRExporterParameter.JASPER_PRINT, print);
            xlsx.setParameter(JRExporterParameter.OUTPUT_FILE, excel);
            xlsx.exportReport();
            
            JOptionPane.showMessageDialog(null, "Cek File Pada "
                    + "C:/Report/");
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak! " + e.getMessage(), 
                    "Cetak Data", JOptionPane.ERROR_MESSAGE);
        }
    }
}
