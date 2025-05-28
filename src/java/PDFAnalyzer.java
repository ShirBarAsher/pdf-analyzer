import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.json.JSONObject;
import org.apache.pdfbox.cos.COSDocument;
import java.io.FileWriter;

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

        // Create output directory
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        for (File pdfFile : pdfFiles) {
            try (PDDocument document = PDDocument.load(pdfFile)) {
                // Load the PDF file using PDFBox
                PDDocumentInformation info = document.getDocumentInformation();

                JSONObject json = new JSONObject();
                COSDocument cosDoc = document.getDocument();


                json.put("PDFVersion", document.getVersion()); // PDF Header Version

                json.put("Producer", info.getProducer());
                json.put("Creator", info.getCreator());
                json.put("CreationDate", info.getCreationDate());
                json.put("Modification Date", info.getModificationDate());
                json.put("Title", info.getTitle());
                json.put("Author", info.getAuthor());
                json.put("Subject", info.getSubject());
                json.put("Keywords", info.getKeywords());
                json.put("PageCount", document.getNumberOfPages());

                json.put("IndirectObjectsCount", cosDoc.getObjects().size());

                json.put("fileSizeBytes", pdfFile.length());//Example of a "Document Fingerprint"
                json.put("ImageCount", JSONObject.NULL); // Placeholder
                json.put("fontList", JSONObject.NULL);// Placeholder


                // Save JSON to file
                String jsonFileName = "output/" + pdfFile.getName().replace(".pdf", ".json");
                try (FileWriter fileWriter = new FileWriter(jsonFileName)) {
                    fileWriter.write(json.toString(4));
                    System.out.println("Saved JSON to: " + jsonFileName);
                } catch (Exception e) {
                    System.err.println("Error writing JSON for " + pdfFile.getName());
                    e.printStackTrace();
                }


            } catch (Exception e) {
                // Handle errors
                System.err.println("Error processing file: " + pdfFile.getName());
                e.printStackTrace();
            }
        }

    }
}
