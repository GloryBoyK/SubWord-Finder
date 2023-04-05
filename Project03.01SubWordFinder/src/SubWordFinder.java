import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * public class for project 3.01
 * @version 2/10/23
 * @author 25pandey
 */
public class SubWordFinder implements WordFinder    {
    private ArrayList<ArrayList<String>> dictionary;
    private String alpha = "abcdefghijklmnopqrstuvwxyz";


    public SubWordFinder()  {
        dictionary = new ArrayList<>();
        for(int i = 0; i < 26; i++)
            dictionary.add(new ArrayList<String>());
        populateDictionary();
    }



    /**
     * Retrieve all SubWord objects from the dictionary.
     * A SubWord is defined as a word that can be split into two
     * words that are also found in the dictionary.  The words
     * MUST be split evenly, e.g. no unused characters.
     * For example, "baseball" is a SubWord because it contains
     * "base" and "ball" (no unused characters)
     * To do this, you must look through every word in the dictionary
     * to see if it is a SubWord object
     *
     * @return An ArrayList containing the SubWord objects
     * pulled from the file words.txt
     */
    @Override
    public ArrayList<SubWord> getSubWords() {
        ArrayList<SubWord> subwords =new ArrayList<>();
        for (ArrayList<String> bucket: dictionary){
            for(String word : bucket){
                String front= "", back="";
                for(int i=2; i<word.length()-1;i++){
                    front =word.substring(0,i);
                    back=word.substring(i);
                    //ystem.out.println("DEBUG LINE 43 " + front + " ** " + back);
                    if (inDictionary(front) && inDictionary(back)){
                        subwords.add(new SubWord(word, front,back));
                        //System.out.println("Added " + subwords.get(subwords.size()-1));
                    }
                }
            }
        }
        return subwords;
    }
    /**
     * Look through the entire dictionary object to see if
     * word exists in dictionary
     *
     * @param word The item to be searched for in dictionary
     * @return true if word is in dictionary, false otherwise
     * NOTE: EFFICIENCY O(log N) vs O(N) IS A BIG DEAL HERE!!!
     * You MAY NOT use Collections.binarySearch() here; you must use
     * YOUR OWN DEFINITION of a binary search in order to receive
     * the credit as specified on the grading rubric.
     */

    @Override
    public boolean inDictionary(String word) {
        ArrayList<String> bucket = dictionary.get(alpha.indexOf(word.substring(0,1)));
        //System.out.println("DEBUG: " + alpha.indexOf(word.substring(0,1)));
        return binarySearch(bucket, 0, bucket.size()-1, word)>=0;
        //return Collections.binarySearch(bucket, word) >= 0;
    }

    private int binarySearch(ArrayList<String> list, int low, int high, String target){
        //System.out.println("DEBUG LOW AND HIGH: " + low + " " + high);
        //System.out.println(list.size());
        if (low <= high){
            int mid = (low + high)/2;

            if (list.get(mid).equals(target))
                return mid;
            else if (list.get(mid).compareTo(target)<0)
                return binarySearch(list, mid + 1, high, target);

            else
                return binarySearch(list, low, mid - 1, target);


        }
        return -1;
    }

    /**
     * Populates the dictionary from the text file contents
     * The dictionary object should contain 26 buckets, each
     * bucket filled with an ArrayList<String>
     * The String objects in the buckets are sorted A-Z because
     * of the nature of the text file words.txt
     */
    @Override
    public void populateDictionary() {
        String file1="../new_scrabble.txt";
        try {
            Scanner in = new Scanner(new File(file1));
            while (in.hasNext()) {
                String word = in.nextLine();
                int index = alpha.indexOf(word.substring(0, 1));
                dictionary.get(index).add(word);
            }
            in.close();
            for (int i = 0; i < dictionary.size(); i++) {
                Collections.sort(dictionary.get(i));
                //System.out.println("DEBUG: " + alpha.substring(i, i+1) + " " + dictionary.get(i).size());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * main method for class SubWordFinder
     * @param args
     */

    public static void main(String[] args) {
        SubWordFinder program = new SubWordFinder();
        ArrayList<SubWord> temp = program.getSubWords();
        //System.out.println("Size of temp = " + temp.size());
        System.out.println("* List of all SubWord objects *");
        for(SubWord sw : temp)
            System.out.println(sw);
        System.out.println(temp.size() + " total SubWords");
    }


}
