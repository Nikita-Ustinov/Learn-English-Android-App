package alpha.english.nikitaustinov.learnenglish;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;

public class Word {

    static LinkedList<Word> AllWords = new LinkedList<>();

    String EnglishWord;
    String Translate;
    String Example;
    int Week;
    Boolean IsAdditional;


    public Word(String word, String translate, String example, int week) {
        EnglishWord = word;
        Translate = translate;
        Week = week;
        IsAdditional = false;
        Example = example;
    }

    public Word(String word, String translate, String example, int week,  boolean isAdditional) {
        EnglishWord = word;
        Translate = translate;
        Week = week;
        IsAdditional = isAdditional;
        Example = example;
    }

    public static void unpackIntermediate(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.intermediate);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String [] stringArray = input.split("\n");
        boolean justExit = false;
        boolean isAdditionalWord = false;
        int week = 0;
        String word= null;
        String translate = null;
        String example = null;
        int counter = 0;
        for(int i=1; i<stringArray.length; i++) {
            if(stringArray[i].length() < 3) {
                if(stringArray[i].length() == 2) {
                    isAdditionalWord = true;
                    stringArray[i] = stringArray[i].substring(0, stringArray[i].length()-1);
                }
                else
                    isAdditionalWord = false;
                week = Integer.parseInt(stringArray[i]);
                justExit = true;
                Log.d("MyDebug","Неделя "+String.valueOf(week));
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                String[] wordAndTranslate = stringArray[i].split("=");
                word = toUpperCaseFirstLetter(deleteFirstSpaces(deleteLastSpaces(wordAndTranslate[0])));
                translate = toUpperCaseFirstLetter(deleteLastSpaces(deleteFirstSpaces(wordAndTranslate[1])));
                if(wordAndTranslate.length == 3) {
                    example = toUpperCaseFirstLetter(deleteFirstSpaces(deleteLastSpaces(wordAndTranslate[2])));
                }
                Word newWord = new Word(word, translate, example, week, isAdditionalWord);

                AllWords.add(newWord);
                counter++;
                Log.d("MyDebug","Извлечено "+String.valueOf(counter)+" слово   "+word);
            }
            else {
                justExit = false;
            }
        }
        Log.d("MyTag","Успешное извлечение Intermediate файла");
    }

    public static void unpackIntermediate2(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.intermediate_2);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String [] stringArray = input.split("\n");
        boolean justExit = false;
        boolean isAdditionalWord = false;
        int week = 0;
        String word= null;
        String translate = null;
        String example = null;
        int counter = 0;
        for(int i=1; i<stringArray.length; i++) {
            if(stringArray[i].length() < 3) {
                if(stringArray[i].length() == 2) {
                    isAdditionalWord = true;
                    stringArray[i] = stringArray[i].substring(0, stringArray[i].length()-1);
                }
                else
                    isAdditionalWord = false;
                week = Integer.parseInt(stringArray[i]);
                justExit = true;
                Log.d("MyDebug","Неделя "+String.valueOf(week));
            }
            if((!justExit)&&(!stringArray[i].contains("null"))){
                String[] wordAndTranslate = stringArray[i].split("=");
                word =toUpperCaseFirstLetter(deleteLastSpaces(wordAndTranslate[0]));
                translate =toUpperCaseFirstLetter(deleteFirstSpaces(wordAndTranslate[1]));
                if(wordAndTranslate.length == 3) {
                    example = toUpperCaseFirstLetter(deleteFirstSpaces(deleteLastSpaces(wordAndTranslate[2])));
                }
                Word newWord = new Word(word, translate, example, week, isAdditionalWord);

                AllWords.add(newWord);
                counter++;
                Log.d("MyDebug","Извлечено "+String.valueOf(counter)+" слово  "+word);
            }
            else {
                justExit = false;
            }
        }
        Log.d("MyTag","Успешное извлечение Intermediate файла");
    }

    static String toUpperCaseFirstLetter(String input) {
        String output = null;
        String firstLetter = input.substring(0,1);
        firstLetter = firstLetter.toUpperCase();
        output = firstLetter + input.substring(1);
        return output;
    }

    static String deleteLastSpaces(String input) {
        String output = input;
        while(output.charAt(output.length()-1) == ' ') {
            output = output.substring(0, output.length()-1);
        }
        return output;
    }

    static String deleteFirstSpaces(String input) {
        String output = input;
        String flag = output.substring(0,1);
        while(flag.equals(" ")) {
            output = output.substring(1, output.length());
            flag = output.substring(0,1);
        }
        return output;
    }

    public static String convertStreamToString(BufferedReader inBR) throws IOException {

        String endFlag;
        String output = null;
        while((endFlag=inBR.readLine()) !=null) {
            output += endFlag+"\n";
            output += inBR.readLine()+"\n";
        }
        return output;
    }

    public static LinkedList<Word> getListOfWords(boolean[] isNeedToShowWeek, boolean isMain, boolean isAdditional) {
        LinkedList<Word> output = new LinkedList<>();
        for(int i=0; i<isNeedToShowWeek.length; i++) {
            if(isNeedToShowWeek[i]){
                output.addAll(getWeek(i,isMain , isAdditional));
            }
        }
        output = shuffleList(output);
        return output;
    }

    static LinkedList<Word> getWeek(int weekNumber, boolean isMain, boolean isAdditional) {
        LinkedList<Word> output = new LinkedList<>();
        Word templ;
        weekNumber++;
        if(isAdditional && isMain) {
            for (int i = 0; i < AllWords.size(); i++) {
                templ = AllWords.get(i);
                if (templ.Week == weekNumber) {
                    output.add(templ);
                }
            }
        }
        else if(isMain) {
            for (int i = 0; i < AllWords.size(); i++) {
                templ = AllWords.get(i);
                if (templ.Week == weekNumber && templ.IsAdditional == false) {
                    output.add(templ);
                }
            }
        }
        else {
            for (int i = 0; i < AllWords.size(); i++) {
                templ = AllWords.get(i);
                if (templ.Week == weekNumber && templ.IsAdditional == true) {
                    output.add(templ);
                }
            }
        }
        return output;
    }


    static LinkedList<Word> shuffleList(LinkedList<Word> list) {
        int elementNumber;
        Word templ;
        for(int i=0; i<list.size()-1; i++) {
            elementNumber = (int)(Math.random()*(list.size()-i-1));
            templ = list.get(list.size()-i-1);
            list.set(list.size()-i-1, list.get(elementNumber));
            list.set(elementNumber, templ);
        }
        return list;
    }

    public static void clearChach() {
        AllWords = new LinkedList<>();
    }
}
