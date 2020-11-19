// Main file to run all the classes in required sequence.
package lucene_project;

import org.apache.lucene.queryparser.classic.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class main_class {
    public static void main(String[] args) throws IOException, ParseException {

//        Creating required folders.
        String folderName1 = "all_documents";
        String folderName2 = "indexes";

        Path path1 = Paths.get(folderName1);
        Path path2 = Paths.get(folderName2);

        if (!Files.exists(path1)) {
            Files.createDirectory(path1);}
            if (!Files.exists(path2)){
                Files.createDirectory(path2);}

//        Setting up flags for respective valid arguments parsed.
                ArrayList analyzerArgument = new ArrayList();
                analyzerArgument.add("Standard");
                analyzerArgument.add("Custom");
                analyzerArgument.add("Simple");
                analyzerArgument.add("English");
                if (!analyzerArgument.contains(args[0])) {
                    System.out.println("Wrong Analyzer argument parsed. Please check documentation.");
                    System.exit(0);
                }
                ArrayList similarityArgument = new ArrayList();
                similarityArgument.add("BM25");
                similarityArgument.add("Default");
                similarityArgument.add("TFIDF");
                if (!similarityArgument.contains(args[1])) {
                    System.out.println("Wrong Similarity argument parsed. Please check documentation.");
                    System.exit(0);
                }

//        For parsing the documents into separate files.
                parser_cranfield documents_parsed = new parser_cranfield();
                documents_parsed.cranfield_Parse();

//        For building the index based on the 1400 files containing the required documents.
                index_creator reqdIndex = new index_creator();
                reqdIndex.indexCreator(args[0]);

//        Formatting the questions as proper input structure for querying.
                questions_parser questions_formed = new questions_parser();
                questions_formed.questionsParse();

//        Querying the indexed files based on the questions fetched from cran.qry.
                query_cranfield querying = new query_cranfield();
                querying.queryCranfield(args[0], args[1]);
    }
}
