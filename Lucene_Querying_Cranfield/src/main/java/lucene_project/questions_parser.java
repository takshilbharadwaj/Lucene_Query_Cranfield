// When this file is run, it reads the cran.qry and stores them in proper format in questions_refined.txt.

package lucene_project;
import java.io.*;
import java.util.ArrayList;

public class questions_parser {
    public static void questionsParse() throws IOException {
//        cran.qry file is read and parsed as input for the BufferedReader.
        File file = new File("resource_files/cran.qry");
        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);

//        ArrayLists are declared for the purpose of logic building for saving the sentences in proper format in the resultant file.
        ArrayList all_questions = new ArrayList();
        ArrayList temp = new ArrayList();
        ArrayList all_lines = new ArrayList();
        String line;

//        While loop below stores the sentences in all_lines ArrayList, it is done so as to tackle the line breaks
//        present for a single question.
        while ((line = reader.readLine()) != null){
            all_lines.add(line);}

//        This for loop joins the different lines of the same question together, which were divided by the line break.
        for(int i = 0;i < all_lines.size();i++){
            String new_sentence = "";
            line = (String) all_lines.get(i);

//            If there is ".I" in the sentence, the previous sentence is joined together and saved in all_questions ArrayList.
            if(line.contains(".I") == true){
                for(int j = 0; j<temp.size();j++){
                    new_sentence = new_sentence + " " +temp.get(j);
                }
                temp.clear();
                all_questions.add(new_sentence);
            }

//            Since, the loop is running one step ahead, the last question doesn't get read. For reading the last question,
//            the last index of all_lines ArrayList is used as reference for saving rather than ".I".
            else if(i == (all_lines.size()-1)){
                for(int k = 0; k<temp.size();k++){
                    new_sentence = new_sentence + " " + temp.get(k);
                }
                temp.clear();
                all_questions.add(new_sentence);
            }

//            This condition takes the acquired part of a single question and stores in a temporary ArrayList, where the items
//            in the indexes are joined together in the conditions above.
            else if (line.contains(".I") != true) {
                if (line.contains(".W") != true){
                    temp.add(line);
                }
            }
        }
        all_questions.remove(0);
        System.out.println("----------------------------------------------------------------\nQUESTIONS STORED AS SINGLE LINES IN ARRAYLIST AND SAVED IN FILE.\n----------------------------------------------------------------");

//        File "questions_refined.txt" is formed for writing the final questions.
        File file_open = new File("questions_refined.txt");
        FileOutputStream outputStream = new FileOutputStream(file_open);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

//        The items from all_questions Arraylist are stored in the file sequentially
        for(int i = 0; i < all_questions.size();i++){
            writer.write((String) all_questions.get(i));
            writer.newLine();
        }
        writer.close();
        System.out.println("-----------------------------\nQUESTIONS_REFINED.TXT FORMED.\n-----------------------------");

    }
}