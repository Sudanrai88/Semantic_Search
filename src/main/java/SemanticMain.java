import org.apache.commons.lang3.time.StopWatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SemanticMain {
    public List<String> listVocabulary = new ArrayList<>();  //List that contains all the vocabularies loaded from the csv file.
    public List<double[]> listVectors = new ArrayList<>(); //Associated vectors from the csv file.
    public List<Glove> listGlove = new ArrayList<>();
    public final List<String> STOPWORDS;

    public SemanticMain() throws IOException {
        STOPWORDS = Toolkit.loadStopWords();
        Toolkit.loadGLOVE();
    }


    public static void main(String[] args) throws IOException {
        StopWatch mySW = new StopWatch();
        mySW.start();
        SemanticMain mySM = new SemanticMain();
        mySM.listVocabulary = Toolkit.getListVocabulary();
        mySM.listVectors = Toolkit.getlistVectors();
        mySM.listGlove = mySM.CreateGloveList();

        List<CosSimilarityPair> listWN = mySM.WordsNearest("penis");
        Toolkit.PrintSemantic(listWN, 5);

        listWN = mySM.WordsNearest("phd");
        Toolkit.PrintSemantic(listWN, 5);

        List<CosSimilarityPair> listLA = mySM.LogicalAnalogies("russia", "uk", "china", 5);
        Toolkit.PrintSemantic("china", "uk", "london", listLA);

        listLA = mySM.LogicalAnalogies("woman", "man", "king", 5);
        Toolkit.PrintSemantic("woman", "man", "king", listLA);

        listLA = mySM.LogicalAnalogies("banana", "apple", "red", 3);
        Toolkit.PrintSemantic("banana", "apple", "red", listLA);
        mySW.stop();

        if (mySW.getTime() > 2000)
            System.out.println("It takes too long to execute your code!\nIt should take less than 2 second to run.");
        else
            System.out.println("Well done!\nElapsed time in milliseconds: " + mySW.getTime());
    }

    public List<Glove> CreateGloveList() {
        List<Glove> listResult = new ArrayList<>();

        for (int i = 0; i < listVocabulary.size(); i++) {
            if (STOPWORDS.contains(listVocabulary.get(i))) {
                continue;
            } else {
                Glove gloveObject = new Glove(listVocabulary.get(i), new Vector(listVectors.get(i)));
                listResult.add(gloveObject);
            }
        }
        return listResult;
    }


    public List<CosSimilarityPair> WordsNearest(String _word) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();

        Vector _wordV = new Vector(new double[] {});
        //_word -> listGlove
        boolean doesExist = false;

        for (int i = 0; i < listGlove.size(); i++) { //vector representation of the word if it exists, or error if it dosent exist
            if (listGlove.get(i).getVocabulary().equals("error")) { //Within lisGlove
                _wordV = listGlove.get(i).getVector();
            }
            if (_word.equals(listGlove.get(i).getVocabulary())) {
                _wordV = listGlove.get(i).getVector();
                doesExist = true;
                break;
            }
        }
        if(doesExist == false) {
            _word = "error";
        }
        for (int i = 0; i < listGlove.size(); i++) {
            if (_word.equals(listGlove.get(i).getVocabulary())) {
                continue;
            }
            double a = listGlove.get(i).getVector().cosineSimilarity(_wordV);
            listCosineSimilarity.add(new CosSimilarityPair(_word, listGlove.get(i).getVocabulary(), a));
        } HeapSort.doHeapSort(listCosineSimilarity);
        return listCosineSimilarity;
    }

    public List<CosSimilarityPair> WordsNearest(Vector _vector) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();

        for (int i = 0; i < listGlove.size(); i++) {
            if (_vector.equals(listGlove.get(i).getVector())) {
                continue;
            }
            double a = listGlove.get(i).getVector().cosineSimilarity(_vector);
            listCosineSimilarity.add(new CosSimilarityPair(_vector, listGlove.get(i).getVocabulary(), a));
        } HeapSort.doHeapSort(listCosineSimilarity);
        return listCosineSimilarity;

    }

    /**
     * Method to calculate the logical analogies by using references.
     * <p>
     * Example: uk is to london as china is to XXXX.
     *       _firISRef  _firTORef _secISRef
     * In the above example, "uk" is the first IS reference; "london" is the first TO reference
     * and "china" is the second IS reference. Moreover, "XXXX" is the vocabulary(ies) we'd like
     * to get from this method.
     * <p>
     * If _top <= 0, then returns an empty listResult.
     * If the vocabulary list does not include _secISRef or _firISRef or _firTORef, then returns an empty listResult.
     *
     * @param _secISRef The second IS reference
     * @param _firISRef The first IS reference
     * @param _firTORef The first TO reference
     * @param _top      How many vocabularies to include.
     */
    public List<CosSimilarityPair> LogicalAnalogies(String _secISRef, String _firISRef, String _firTORef, int _top) {
        List<CosSimilarityPair> listResult = new ArrayList<>();

        Vector firstRef = new Vector(new double[]{});
        Vector secondRef = new Vector(new double[]{});
        Vector firstToRef = new Vector(new double[]{});
        int foundWords = 0; //increments to make sure that the three arguments are found
        //if not return empty List
        if (_top < 0) {
            return listResult;
        }

        for (int i = 0; i < listGlove.size(); i++) { //vector representation of the word if it exists
            if (_firISRef.equals(listGlove.get(i).getVocabulary())) {
                firstRef = listGlove.get(i).getVector();
                foundWords++;
            }
            if (_secISRef.equals(listGlove.get(i).getVocabulary())) {
                secondRef = listGlove.get(i).getVector();
                foundWords++;
            }
            if (_firTORef.equals(listGlove.get(i).getVocabulary())) {
                firstToRef = listGlove.get(i).getVector();
                foundWords++;
            }
        }
        if(foundWords != 3) {
            return listResult;
        }
//china = secIsRef    -   UK = firIsRef    +   London = firToRef
        Vector diffVec = secondRef.subtraction(firstRef); // subtracts first ref from second ref
        listResult = WordsNearest(diffVec.add(firstToRef)); //This is the algo written above.
        //Heapsort completed here
        //Remove the words _firISRef,_firTORef, _secISRef
        for (int i = 0; i < listResult.size(); i++) {
            if (_firISRef.equals(listResult.get(i).getWord2())) { //Check if
                listResult.remove(i);
                i--; //The list will move back 1, therefore i-- to iterate over the replaced value
            } else if (_secISRef.equals(listResult.get(i).getWord2())) {
                listResult.remove(i);
                i--;
            } else if (_firTORef.equals(listResult.get(i).getWord2())) {
                listResult.remove(i);
                i--;
            }
        }
        return listResult.subList(0, _top);
    }
}