package com.cs3250p1.project1;

public class StringStreamer {
    String output="";
    int linecounter=0;
    int linelimit=20;

    /* Checks if a String is empty ("") or null. */
    boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    // Counts how many times the substring appears in the larger string.
    int noOfOccurrences(String _inputStr, String _testStr) {
        if (isEmpty(_inputStr) || isEmpty(_testStr)) {
            return 0;
        }

        int i = 0, counter = 0;
        while (true) {
            i = _inputStr.indexOf(_testStr, i);
            if (i != -1) {
                counter++;
                i += _testStr.length();
            } else {
                break;
            }
        }

        return counter;
    }

    void manageLines() {
        while (linecounter >= linelimit) {
            output = output.substring(0,6) //the "<start>" part
                    +output.substring(output.indexOf("\n")+4 //the first occurence of "\n", +4 for its end
                    ,output.length());
            linecounter--;
        }
    }
    void push(String _string){
        output=output.replaceAll("</html>","");
        output+=_string;
        output+="</html>";
        linecounter+=noOfOccurrences(_string,"\n");
        manageLines();
    }

    void pushLn(String _string){
        output=output.replaceAll("</html>","");
        output+=_string;
        output+="\n";
        output+="</html>";
        linecounter+=noOfOccurrences(_string,"\n");;
        manageLines();
    }
    void nextLn(){
        output=output.replaceAll("</html>","");
        output+="\n";
        output+="</html>";
        linecounter++;
        manageLines();
    }

    String getStream()
    {
        return output;
    }
}
