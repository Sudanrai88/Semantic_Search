
public class Vector {

    private double[] doubElements;

    public Vector(double[] _elements) {
        this.doubElements = _elements;
    }


    public double getElementatIndex(int _index) {
        try {
            return doubElements[_index];
        }
        catch(Exception IndexOutOfBoundsException) {
            return -1;
        }
    }

    public void setElementatIndex(double _value, int _index) {
        try {
            doubElements[_index] = _value;
        }
        catch (Exception IndexOutOfBoundsException) {
            doubElements[doubElements.length - 1] = _value;
        }
    }

    public double[] getAllElements() {

            return doubElements;
    }

    public int getVectorSize() {

        return doubElements.length;
    }

    public Vector reSize(int _size) {

        if (_size == getVectorSize() || _size <= 0) {
            return this;
        }
        Vector V1 = new Vector(new double[_size]);
        Vector V2 = new Vector(doubElements);

        if (_size < doubElements.length) {
            for (int i = 0; i < _size; i++) {
                V1.doubElements[i] = V2.doubElements[i];
            } return V1;
        } else {
            for (int i = 0; i < doubElements.length; i++) {
                V1.doubElements[i] = V2.doubElements[i];
                for (int j = doubElements.length; j < _size; j++) {
                    V1.doubElements[j] = -1;
                }
            }
            return V1;
        }
    }

    public Vector add(Vector _v) {

        // resize the vectors to the same size
        Vector v1 = _v;
        Vector v2 = this;
        if (_v.doubElements.length > doubElements.length) {
            v1 = _v;
            v2 = this.reSize(_v.doubElements.length);
        } else if (_v.doubElements.length < doubElements.length) {
            v1 = _v.reSize(doubElements.length);
            v2 = this;
        }

        for (int i = 0; i < v1.doubElements.length; i++) {
            v1.doubElements[i] = v1.doubElements[i] + v2.doubElements[i];
        } return v1;
    }


    public Vector subtraction(Vector _v) {


            // resize the vectors to the same size
            Vector v1 = _v;
            Vector v2 = this;
            if (_v.doubElements.length > doubElements.length) {
                v1 = _v;
                v2 = this.reSize(_v.doubElements.length);
            } else if (_v.doubElements.length < doubElements.length) {
                v1 = _v.reSize(doubElements.length);
                v2 = this;
            }

            for (int i = 0; i < v1.doubElements.length; i++) {
                v1.doubElements[i] = v2.doubElements[i] - v1.doubElements[i];
            } return v1;
        }

    public double dotProduct(Vector _v) {

        Vector v1 = _v;
        Vector v2 = this;
        if (_v.doubElements.length > doubElements.length) {
            v1 = _v;
            v2 = this.reSize(_v.doubElements.length);
        } else if (_v.doubElements.length < doubElements.length) {
            v1 = _v.reSize(doubElements.length);
            v2 = this;
        }
        double total = 0;
        for (int i = 0; i < v1.doubElements.length; i++) {
            total += v2.doubElements[i] * v1.doubElements[i];
        }
        return total;
    }

    public double cosineSimilarity(Vector _v) {

        //dot.Product of two vectors / length of vector. Sum of all elements ^2. Square root and then * with the other vector
        Vector v1 = _v;
        Vector v2 = this;
        if (_v.doubElements.length > doubElements.length) {
            v1 = _v;
            v2 = this.reSize(_v.doubElements.length);
        } else if (_v.doubElements.length < doubElements.length) {
            v1 = _v.reSize(doubElements.length);
            v2 = this;
        }

        double productTotal = v1.dotProduct(v2);
        double lengthOfVector = 0;
        double lengthOfVector2 = 0;

        for (int i = 0; i < v1.getVectorSize(); i++) {
            lengthOfVector += v1.doubElements[i] * v1.doubElements[i];
            lengthOfVector2 += v2.doubElements[i] * v2.doubElements[i];
        }
        lengthOfVector = Math.sqrt(lengthOfVector) * Math.sqrt(lengthOfVector2);

        return productTotal/lengthOfVector;

    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;

        if (doubElements.length == v.doubElements.length) {
            for (int i = 0; i < doubElements.length; i++) {
                if (doubElements[i] != v.doubElements[i] ) {
                    boolEquals = false;
                }
            }
        }return boolEquals;
    }


    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
