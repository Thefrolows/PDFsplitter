import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Media;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class pdfSplitter {
    public static void main(String[] args) throws IOException, PrinterException {
        File file = new File("TestPDF");
        PDDocument document = PDDocument.load(file);

        Splitter splitter = new Splitter();
        List<PDDocument> pages = splitter.split(document);
        Iterator<PDDocument> iterator = pages.listIterator();
        int i = 1;
        PDDocument pdd = new PDDocument();
        pdd.addPage(document.getPage(document.getNumberOfPages()-1));
        pdd.addPage(document.getPage(document.getNumberOfPages()-2));
        pdd.save("duplex2");
        PrinterJob loPrinterJob = PrinterJob.getPrinterJob();
        PageFormat loPageFormat  = loPrinterJob.defaultPage();
        Paper loPaper = loPageFormat.getPaper();
        //display the print dialog

        //PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet(Sides.DUPLEX);
//        PrintRequestAttributeSet attributeSet1 = new HashPrintRequestAttributeSet();
//        attributeSet1.add(Sides.DUPLEX);

        //attributeSet.add(Sides.DUPLEX);
        //loPrinterJob.setPrintable( new PDFPrintable(pdd));
//        loPrinterJob.print( new HashPrintRequestAttributeSet(Sides.TWO_SIDED_LONG_EDGE));
//        loPrinterJob.printDialog(attributeSet);
//        if(attributeSet.equals(attributeSet1)==true){
//            System.out.println(attributeSet1.toString());
//        }

        //loPrinterJob.print(attributeSet);
//        if (loPrinterJob.printDialog(attributeSet)) {
//            try {
//                loPrinterJob.print(attributeSet);
//            } catch (PrinterException e) {
//                e.printStackTrace();
//            }
//        }
//////        while (iterator.hasNext()) {
//            if (i == pages.size() - 1) {
//                PDDocument pd = pages.get(pages.size()-2);
//                pd.addPage(pages.get(pages.size()-1).getPage(0));
//                pd.save("duplex.pdf");
//                break;
//            }
//            PDDocument pd = iterator.next();
//            pd.save(i++ + ".pdf");
//            System.out.println("coздан файл " + i);
//        }
        String tray = "1";

// Handle human-readable names, see PRINTER_TRAY_ALIASES usage below for context.  Adjust as needed.
        List<String> PRINTER_TRAY_ALIASES = Arrays.asList("", "Tray ", "Paper Cassette ");

// Get default printer
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

// Attributes to be provided at print time
        PrintRequestAttributeSet pset = new HashPrintRequestAttributeSet();
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        Media[] supported = (Media[]) printService.getSupportedAttributeValues(Media.class, null, null);
        for(Media m : supported) {
            for(String pta : PRINTER_TRAY_ALIASES) {
                // Matches "1", "Tray 1", or "Paper Cassette 1"
                if (m.toString().trim().equalsIgnoreCase(pta + tray)) {
                    attributes.add(m);
                    break;
                }
            }
        }

        System.out.println("длинна документа " + pages.size());
        System.out.println("Multiple PDF's created");
        document.close();
    }
}
