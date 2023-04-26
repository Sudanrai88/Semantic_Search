# Semantic_Search

The program, after providing a specific word by the user, is able to find a list of words that are semnatically close to it. E.g. after inputting the word "Computer" the system should list "Software", "Technology","Internet", "Computing" and "Devices" as the top 5 closest words. The program also finds the relation between two give words e.g. if "London" is the capital of the "UK", then the system will identify that "Beijin" is the capital of "China";  or if the colour of "Apple" is "Red", then the colour of "Banana" should be "Yellow".

'Global Vectors for Word Representation (GloVe) generates the Distributional Semantic Model (DSM) file and was trained based on Wikipedia 2014 + Gigaword 5 which contains 6 billion tokens. For this project (due to it being an assignment) only 38,534 unique words has been included. We find the similarity of two words using the cosine similarity of the given word and the next closest words to it. This is done by extracting semantic information from the vector representations of the word, the closeness of said words can be measured as mentioned before by the CosineSimilarity (CS) ---> WordsNearest(String _word); The highest CS is the closest words to the "Computer".

Another interesting phenomenon is that vector operations can be used to interpret
the relationship between words. For example, let −→V UK be the vector representation of
the word "UK", −→V London be the vector representation of the word "London", −→V China be
the vector representation of the word "China" and −→V Bei jing be the vector representation of the word "Beijing", then −→V Bei jing −−→V China ≈ −→V London −−→V UK. In other words,−→V Bei jing ≈−→V China −−→V UK +−→V London.−→V China −−→V UK +
−→V London will produce a new vector, and it is highly unlikely that this new vector (denoted as −→V New) will have exactly the same elements as −→V Bei jing. Therefore we use the code from cosine similarity where it takes in a WordsNearest(String _word) and take in a WordsNearest(Vector _vector) to give us the closest word to the vector which should be "Beijing". 
