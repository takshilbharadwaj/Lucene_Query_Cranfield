// This file is used for indexing the acquired document files in all_documents folder.

package lucene_project;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class index_creator {
    static void indexCreator(String whichAnalyzer) throws IOException {
        {
//         The analyzer used in this file is a custom analyzer whose configuration can be seen in analyzer_custom class.
            Analyzer analyzer = null;
            if(whichAnalyzer.equals("Standard")){
            analyzer = new StandardAnalyzer();
            }
            else if(whichAnalyzer.equals("Custom")){
            analyzer = new analyzer_custom();
            }
            else if(whichAnalyzer.equals("Simple")){
                analyzer = new SimpleAnalyzer();
            }
            else if(whichAnalyzer.equals("English")){
                analyzer = new EnglishAnalyzer();
            }

//        The folder name for saving the index files.
            String directoryName = "indexes";

//        Storing the names of all the documents in all_documents folder for the purpose of reading in the for loop below.
            File cran_folder = new File ("all_documents");
            String [] cran_pathnames;
            cran_pathnames = cran_folder.list();

//        ArrayList declaration for storing the documents read from the files.
            ArrayList documents = new ArrayList();
            Integer count = 0;

//        The for loop runs for all the paths saved in cran_pathnames and reads the file.
            for (String pathname : cran_pathnames)
            {
                // Load the contents of the file
                String content = new String(Files.readAllBytes(Paths.get("all_documents/"+pathname)));

//            temp stores the document of the current iteration and is added to the documents ArrayList which contains
//            all the documents fetched.
                Document temp = new Document();
                temp.add(new StringField("filename", pathname, Field.Store.YES));
                temp.add(new TextField("content", content, Field.Store.YES));
                documents.add(temp);
                //System.out.println(pathname+" INDEXED.");
                count++;
            }

//         The allocated directory is opened and all the fetched documents are written in the search index.
            Directory directory = FSDirectory.open(Paths.get(directoryName));
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter index_writer = new IndexWriter(directory, config);
            index_writer.addDocuments(documents);

//        index_reader and directory objects are closed.
            index_writer.close();
            directory.close();
            System.out.println("---------------------------------\n"+"NUMBER OF DOCUMENTS INDEXED :"+count+"\n---------------------------------");
        }
    }
}
