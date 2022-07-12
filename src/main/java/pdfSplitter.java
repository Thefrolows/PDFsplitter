import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;


import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import java.util.List;

public class pdfSplitter {
    public static void main(String[] args) throws IOException, PrinterException {
        File file = new File("TestPDF");
        PDDocument document = PDDocument.load(file);

        Splitter splitter = new Splitter();
        List<PDDocument> pages = splitter.split(document);


 
        PrinterJob loP = PrinterJob.getPrinterJob();
        boolean is_duplex = true; //TODO  флаг отвечающий за смену режима печати.
        if (is_duplex) { //TODO если необходима печать дуплекс.

            PDDocument pdd = new PDDocument();
            pdd.addPage(document.getPage(document.getNumberOfPages()-1));
            pdd.addPage(document.getPage(document.getNumberOfPages()-2));
            pdd.save("PDF.pdf"); //сохраняем часть документа которую нужно печатать дуплексом

            PDDocument pdDocument = new PDDocument();
            for (int i = 0; i < pages.size() - 2; i++) {
                pdDocument.addPage(document.getPage(i));
            }
            loP.setPageable(new PDFPageable(pdDocument));
          //  loP.print();

            Process p = Runtime.getRuntime().exec("./main"); // вызываем скрипт
            // скрипт при вызове смотрит в папку с файлом для печати. работает медленно
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            //File f = new File("/home/usr/LocalDuplexFile/PDF.pdf");
            //f.delete();
        }
        else { //TODO если необходимо печатать на одной стороне
            //Если нет надобности печатать с двух сторон то экономим время и печатаем средствами ждава
            loP.setPageable(new PDFPageable(document) );
            loP.print();
        }

        System.out.println("длинна документа " + pages.size());
        System.out.println("Multiple PDF's created");
        document.close();
        //TODO исправить пути.
    }
}
