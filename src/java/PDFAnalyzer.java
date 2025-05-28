import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.json.JSONObject;

import java.io.File;
import java.io.FilenameFilter;

public class PDFAnalyzer {
    public static void main(String[] args) {
        File pdfFolder = new File("pdfs");


        if (!pdfFolder.exists() || !pdfFolder.isDirectory()) {
            System.out.println("'pdfs' Folder not found");
            return;
        }

        File[] pdfFiles = pdfFolder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".pdf");
            }
        });

        if (pdfFiles == null || pdfFiles.length == 0) {
            System.out.println("No PDF files found in the 'pdfs' folder.");
            return;
        }

        for (File pdfFile : pdfFiles) {
            try (PDDocument document = PDDocument.load(pdfFile)) {
                // Load the PDF file using PDFBox
                PDDocumentInformation info = document.getDocumentInformation();

                JSONObject json = new JSONObject();

                json.put("PDFVersion", document.getVersion()); // PDF Header Version

                json.put("Producer", info.getProducer());
                json.put("Creator", info.getCreator());
                json.put("CreationDate", info.getCreationDate());
                json.put("Modification Date", info.getModificationDate());
                json.put("Title", info.getTitle());
                json.put("Author", info.getAuthor());
                json.put("Producer", info.getProducer());
                json.put("Subject", info.getSubject());
                json.put("Keywords", info.getKeywords());

            } catch (Exception e) {
                // Handle errors
                System.err.println("Error processing file: " + pdfFile.getName());
                e.printStackTrace();
            }
        }

    }
}
