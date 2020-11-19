// This file contains analyzer_custom class which is being used for the purpose Analyzer in index_creator.

package lucene_project;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.File;
import java.util.Scanner;

public class analyzer_custom extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        try {
//            stopWords_list.txt is read using Scanner.
//            This file contains stop words which are to be filtered.
            File file = new File("resource_files/stopWords_list.txt");
            Scanner scan = new Scanner(file);
            String doc_line = scan.nextLine();

//            Stop words are filtered and saved in array which are stored as CharArraySet.
            String[] addition_stopwords = doc_line.split(" ");
            CharArraySet stopWords = StopFilter.makeStopSet(addition_stopwords);

//            Tokenizer is used for tokenizing the text in the document.
            StandardTokenizer tokenizer = new StandardTokenizer();

//            Using lowercase filter, stop filter with custom Stop words and stemming filter on the tokens.
            TokenStream tokens = new LowerCaseFilter(tokenizer);
            tokens = new StopFilter(tokens, stopWords);
            tokens = new PorterStemFilter(tokens);
            return new TokenStreamComponents(tokenizer, tokens);
        }
        catch (Exception e) {
            System.out.println(e.getClass());
        }
        return null;
    }
}