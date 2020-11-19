// This file is used for querying the indexed documents.

package lucene_project;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.io.*;
import java.nio.file.Paths;


public class query_cranfield
{
    static void queryCranfield(String whichAnalyzer, String whichSimilarity) throws IOException, ParseException {

//         The analyzer used in this file is according to the parsed argument (Custom analyzer can be observed in
//         analyzer_custom class).
        Analyzer analyzer = null;
        String AnalyzerName = null;
        String fileName = null;
        File file_output = null;
        if(whichAnalyzer.equals("Standard")){
            analyzer = new StandardAnalyzer();
            AnalyzerName = "StandardAnalyzer";
        }
        else if(whichAnalyzer.equals("Custom")){
            analyzer = new analyzer_custom();
            AnalyzerName = "CustomAnalyzer";
        }
        else if(whichAnalyzer.equals("Simple")){
            analyzer = new SimpleAnalyzer();
            AnalyzerName = "SimpleAnalyzer";
        }
        else if(whichAnalyzer.equals("English")){
            analyzer = new EnglishAnalyzer();
            AnalyzerName = "EnglishAnalyzer";
        }


//        The folder name for fetching the index files and the maximum number of results to be shown are set to 10.
        String directoryName = "indexes";
        int num_results = 10;

//        DirectoryReader is used for forming objects for reading and searching the index files.
        Directory directory = FSDirectory.open(Paths.get(directoryName));
        DirectoryReader index_reader = DirectoryReader.open(directory);
        IndexSearcher index_searcher = new IndexSearcher(index_reader);

//        The respective resultant file is written according to the arguments parsed, with results acquired from
//        querying in format of Trec_Eval.
        if(whichSimilarity.equals("BM25") || whichSimilarity.equals("Default")){
            index_searcher.setSimilarity(new BM25Similarity());
            fileName = "results_" + AnalyzerName + "_BM25.txt";
            file_output = new File(fileName);
        }
        else if(whichSimilarity.equals("TFIDF")) {
            index_searcher.setSimilarity(new ClassicSimilarity());
            fileName = "results_" + AnalyzerName + "_TFIDF.txt";
            file_output = new File(fileName);
        }

        FileOutputStream outputStream = new FileOutputStream(file_output);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

//        The query parser object is created while keeping the search field set to "content".
        QueryParser parser = new QueryParser("content", analyzer);
        parser.setAllowLeadingWildcard(true);

//        questions_refined.txt is read for fetching the questions for purpose of querying.
        String queryString = "";
        File file_input = new File("questions_refined.txt");
        FileInputStream fileStream = new FileInputStream(file_input);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);

//        The while loop below is used to parse one question a time and the result of the query search is being printed
//        below and stored in the resultant file.
        String line;
        int query_counter = 0;
        while ((line = reader.readLine()) != null){

            query_counter++;
//            System.out.println("QUERY NUMBER : "+query_counter);
//            System.out.println("ENTERED QUESTION : "+line);
            queryString = line;

//                The whitespaces are removed from beginning and ending of query, if present and the query is parsed.
            queryString = queryString.trim();
            Query query = parser.parse(queryString);

//                The results are stored in match object.
            ScoreDoc[] match = index_searcher.search(query, num_results).scoreDocs;
//            System.out.println("Documents: " + match.length);

//                The resultants in match are written in the resultant file using the for loop below.
            for (int i = 0; i < match.length; i++)
            {
                Document document_match = index_searcher.doc(match[i].doc);
                String doc_id = document_match.get("filename");

//                    The index of the document is fetched from the name of the document by removing "id_document_"
//                    and ".txt" from it.
                doc_id = doc_id.replace("id_document_","");
                doc_id = doc_id.replace(".txt","");
                String write_info = query_counter + " " + 0 + " " + doc_id + " " + (i+1) + " " + match[i].score + " " + "STANDARD";
                writer.write(write_info);
                writer.newLine();
//                System.out.println(i+1 + "." + document_match.get("filename") + " " + match[i].score );
            }
//            System.out.println();
            }
//        index_reader, writer and directory objects are closed.
        index_reader.close();
        writer.close();
        directory.close();
        System.out.println("Query Results stored in "+ fileName +".\nFORMAT : [queryID, 0, DocumentID, ranking, Relevancy Score]");
    }
}
