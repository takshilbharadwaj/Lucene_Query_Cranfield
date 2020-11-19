// When this file is run, it reads the Cranfield documents from cran.all.1400 and save its
// in all_document folder sequentially.

package lucene_project;
import java.io.*;
import java.util.Scanner;

public class parser_cranfield {
    static void cranfield_Parse(){
//        cran.all.1400 file is read.
        File file = new File("resource_files/cran.all.1400");

//       Below is the logic used for fetching the documents and saving them in different files.
        try {
//            cran.all.1400 is parsed using Scanner and read line by line.
            Scanner scan = new Scanner(file);
            String doc_line = scan.nextLine();

//            The loop is set to run 1400 times, given cran.all.1400 contains 1400 documents in total.
            for (int count = 1; count <= 1400; count++) {

//                Each file is name with initial "id_document_" + the index of the document.
                // System.out.println("Writing id_document_"+count+".txt");
                String storage_loc = "all_documents/id_document_" +count+".txt";
                File file_open = new File(storage_loc);
                FileOutputStream outputStream = new FileOutputStream(file_open);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

//                The writer below writes the index in the file.
                writer.write(doc_line);
                writer.newLine();
                doc_line = scan.nextLine();

//                The while loop checks for ".I" in the line. If the line does not contain .I, the line is being stored
//                in the same file, whereas, if .I is found, the while loop breaks and new file is produced for next document.
                while (doc_line.contains(".I") != true) {
                    writer.write(doc_line);
                    writer.newLine();
                    if (scan.hasNextLine()){
                        doc_line = scan.nextLine();
                    }
                    else
                        break;
                }
//                The file with required document is being written and closed.
                writer.close();
            }
            System.out.println("---------------------\nALL DOCUMENTS PARSED.\n---------------------");
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




