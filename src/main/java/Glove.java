public class Glove {
    private String strVocabulary;
    private Vector vecVector;

    public Glove(String _vocabulary, Vector _vector) {

        this.strVocabulary = _vocabulary;
        this.vecVector = _vector;
    }

    public String getVocabulary() {

        return this.strVocabulary;
    }

    public Vector getVector() {

        return this.vecVector;
    }

    public void setVocabulary(String _vocabulary) {

        strVocabulary = _vocabulary;
    }

    public void setVector(Vector _vector) {

        vecVector = _vector;
    }
}
