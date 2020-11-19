This repository focuses on performing query on Cranfield Dataset and comparing different analyzers.
here, SimpleAnalyzer, StandardAnalyzer and a custom built analyzer are being used and the performance is beign tracked using trec_eval-9.0.7.

To build from scratch, please follow the commands below in a linux environment.

Make sure you have git, Java 11 and maven installed.

NOTE : The java classes for each projects are commented for clearer description.

1. Clone the repository in a target folder.
```
git clone https://github.com/TBagZzz/Lucene_Query_Cranfield.git
```

2. Download and build trec_eval-9.0.7 inside the same target folder.
```
wget https://trec.nist.gov/trec_eval/trec_eval-9.0.7.tar.gz
tar -xzf trec_eval-9.0.7.tar.gz
cd trec_eval-9.0.7/
make
cd ..
```
Your trec_eval is ready !

3. Now, it is needed to build jar files for each maven project and get the final respective resultant file.

i. Below are the commands wwith combination of 4 Analyzers (Custom, Standard, Simple, English) and 2 Scoring functions (BM25, TFIDF):
```
cd lucene_querying_cranfield/Lucene_Querying_Cranfield/
mvn package
java -jar target/Lucene_Querying_Cranfield-1.0.jar Simple BM25
java -jar target/Lucene_Querying_Cranfield-1.0.jar Simple TFIDF
java -jar target/Lucene_Querying_Cranfield-1.0.jar Custom BM25
java -jar target/Lucene_Querying_Cranfield-1.0.jar Custom TFIDF
java -jar target/Lucene_Querying_Cranfield-1.0.jar Standard BM25
java -jar target/Lucene_Querying_Cranfield-1.0.jar Standard TFIDF
java -jar target/Lucene_Querying_Cranfield-1.0.jar English BM25
java -jar target/Lucene_Querying_Cranfield-1.0.jar English TFIDF
cd ../..
```
Files formed for trec_eval :
i.    results_SimpleAnalyzer_BM25.txt
ii.   results_SimpleAnalyzer_TFIDF.txt
iii.  results_CustomAnalyzer_BM25.txt
iv.   results_CustomAnalyzer_TFIDF.txt
v.    results_StandardAnalyzer_BM25.txt
vi.   results_StandardAnalyzer_TFIDF.txt
vii.  results_EnglishAnalyzer_BM25.txt
viii. results_EnglishAnalyzer_TFIDF.txt

4. Now, for evaluating the different analyzers, we will use trec_eval.

i.
```
cd trec_eval-9.0.7/
./trec_eval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_SimpleAnalyzer_BM25.txt
./trec_eval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_SimpleAnalyzer_TFIDF.txt
./trec_eval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_CustomAnalyzer_BM25.txt
./trec_eval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_CustomAnalyzer_TFIDF.txt
./trec_eval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_StandardAnalyzer_BM25.txt
./trec_eval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_StandardAnalyzer_TFIDF.txt
./trec_eval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_EnglishAnalyzer_BM25.txt
./trec_eval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_EnglishAnalyzer_TFIDF.txt
```
This will show all the evaluation values obtained using trec_eval.

ii. For filtered set of values, to fetch map value and p 0.5 and p 10, following set of commands to be executed.
```
./trec_eval -m map -m P.5,10 ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_SimpleAnalyzer_BM25.txt
./trec_eval -m map -m P.5,10 ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_SimpleAnalyzer_TFIDF.txt
./trec_eval -m map -m P.5,10 ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_CustomAnalyzer_BM25.txt
./trec_eval -m map -m P.5,10 ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_CustomAnalyzer_TFIDF.txt
./trec_eval -m map -m P.5,10 ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_StandardAnalyzer_BM25.txt
./trec_eval -m map -m P.5,10 ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_StandardAnalyzer_TFIDF.txt
./trec_eval -m map -m P.5,10 ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_EnglishAnalyzer_BM25.txt
./trec_eval -m map -m P.5,10 ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/resource_files/QRelsCorrectedforTRECeval ../Lucene_Query_Cranfield/Lucene_Querying_Cranfield/results_EnglishAnalyzer_TFIDF.txt
```
