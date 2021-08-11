package controller;

import model.EPA;

import java.io.*;
import java.lang.UnsupportedOperationException;
import java.util.HashSet;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author  Thomas Alexander Hövelmann
 *
 * @see EPAController
 */
public class IOController {
    /**
 	 * The path where the serialized model is to be stored. Data at this path is overwritten.
 	 */
    public static final String SAVE_FILE_PATH = "model.ser";

    /**
 	 * The main path where the generated pdf-files should be stored. Data at this path is not overwritten.
 	 */
    public static final String SAVE_PDF_PATH = "medicalReport.pdf";

    /**
     * The associated {@link EPAController}; only way to access the model and other controllers.
     */
    private final EPAController ePAController;

    /**
     * Constructs a new {@code IOController}-object with the specified identification number {@link EPAController}.
     * Responsible for all I/O-operations with regard to files.
     *
     * @param ePAController {@link #ePAController}
     */
    public IOController(EPAController ePAController) {
        this.ePAController = ePAController;
    }

    /**
 	 * Loads and returns a serialized model stored at {@link #SAVE_FILE_PATH}.
     *
     * @return Loaded model or {@code null} if an other exception than {@code FileNotFoundException} occurred.
     * @throws FileNotFoundException if there is no such file, like {@link #SAVE_FILE_PATH}.
 	 */
    public EPA load() throws FileNotFoundException {
        InputStream fIS = null;
        try {
            fIS = new FileInputStream(IOController.SAVE_FILE_PATH);
            ObjectInputStream oIS = new ObjectInputStream(fIS);
            return (EPA)oIS.readObject();
        }
        catch (FileNotFoundException fNFEx) {
            throw new FileNotFoundException();
        }
        catch (IOException | ClassNotFoundException iOCNFEx) {
            ePAController.prompt("FEHLER!\nLaden der elektronischen Patientenakten fehlgeschlagen.");
            System.err.println(iOCNFEx);
        }
        finally {
            try {
                if(fIS != null) {
                    fIS.close();
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Serializes the model and stores it at {@link #SAVE_FILE_PATH}. If this operation fails, an error message is
     * displayed.
     */
    public void save() {
        OutputStream fOS = null;
        try {
            fOS = new FileOutputStream(IOController.SAVE_FILE_PATH);
            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            EPA model = ePAController.getEPA();
            oOS.writeObject(model);
        }
        catch (IOException iOEx) {
            ePAController.prompt("FEHLER!\nSpeichern der elektronischen Patientenakten fehlgeschlagen.");
            System.err.println(iOEx);
        }
        finally {
            try {
                if(fOS != null) {
                    fOS.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
 	 * Creates a pdf-file containing the specified data and stores it at {@link #SAVE_PDF_PATH}. Note: {@code null} is
     * interpreted like "".
     *
     * TODO: possible exceptions are to be identified.
 	 * @param data The content of the pdf-file to be created
 	 */
    public void savePDF(String data) throws UnsupportedOperationException {
        HashSet<String> usedFileNames = new HashSet<>();
        for(File file : new File("./").listFiles()) {
            if(file.isFile()) {
                String fileName = file.getName();
                if(fileName.startsWith("medicalReport") && fileName.endsWith(".pdf")) {
                    usedFileNames.add(fileName);
                }
            }
        }
        int nameModifier = 1;
        String pdfSaveFileName = IOController.SAVE_PDF_PATH;
        while(usedFileNames.contains(pdfSaveFileName)) {
            pdfSaveFileName = IOController.SAVE_PDF_PATH.replaceFirst(".pdf","(" + nameModifier++ + ").pdf");
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfSaveFileName));
            document.open();
            document.addTitle("Medizinischer Bericht");
            document.addSubject("Medizinischer Bericht");
            document.addKeywords("PDF, EPA, Medizinischer Bericht");
            document.addAuthor(ePAController.getEPA().getLoadedPatient().getName());
            document.addCreator(ePAController.getEPA().getLoadedPatient().getName());
            Paragraph paragraph = new Paragraph(data);
            document.add(paragraph);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
