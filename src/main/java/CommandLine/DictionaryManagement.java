package CommandLine;

import java.io.*;
import java.util.*;
import java.lang.Comparable;

public class DictionaryManagement {
    Dictionary newD = new Dictionary();
    /*
    *Setter, getter
     */

    public Dictionary getNewD() {
        return newD;
    }

    public void setNewD(Dictionary newD) {
        this.newD = newD;
    }

    /*
    //ham chuc nang nhap lieu
     */
    public  void  insertFromCommandline()
    {
        //nhap so word muon them vao mang
        int numOfWord = new Scanner(System.in).nextInt();
        for (int i = 0; i < numOfWord; i++)
        {
            String target = new Scanner(System.in).nextLine();
            String explain = new Scanner(System.in).nextLine();
            Word temp = new Word();
            temp.setWord_target(target);        //set gia tri cua target
            temp.setWord_explain(explain);      //set gia tri cua explain
            newD.setter(temp);              //them vao hashmap
        }
    }
    public void insertFromFile()
    {
        String  line = null;
        String address = "src/main/resources/data/dictionaries.txt";
        try {
            InputStreamReader inputSR = new InputStreamReader(new FileInputStream(address), "UTF8");
            BufferedReader buffR = new BufferedReader(inputSR);
            String[] cat;
            while ((line= buffR.readLine()) != null)
            {
                cat = line.split("\t");
                if (cat.length >=2 ){
                    Word temp = new Word();
                    temp.setWord_target(cat[0]);
                    temp.setWord_explain(cat[1]);
                    newD.setter(temp);
                }

            }
            buffR.close();
        }
        catch (FileNotFoundException ex )
        {
            System.out.println("Unable to open file '" + address + "'");
        }
        catch (IOException ex ) {
            ex.printStackTrace();
        }
    }
    /*
    *chức năng tra cứu từ điển bằng dòng lệnh
     */
    public Word dictionaryLookup()
    {
        Word result = null;
        String targetInput = new Scanner(System.in).nextLine();
        Dictionary cloneD = this.getNewD();
        for (Map.Entry<String, String> item : cloneD.dictArr.entrySet())
        {
            if (item.getKey().equalsIgnoreCase(targetInput))
            {
                result.setWord_target(item.getKey());
                result.setWord_explain(item.getValue());
            }
        }
        return result;
    }

    public Word dictionaryLookup(Word searchWord)
    {
        Word result = null;
        Dictionary cloneD = this.getNewD();
        for (Map.Entry<String, String> item : cloneD.dictArr.entrySet())
        {
            if (item.getKey().equalsIgnoreCase(searchWord.getWord_target()))
            {
                result.setWord_target(item.getKey());
                result.setWord_explain(item.getValue());
            }
        }
        return result;
    }

    public void addData(Word wordAdd)
    {
        newD.setter(wordAdd);
    }

    public void deleteData(String targetData)
    {
        newD.delete(targetData);
    }


    public HashMap<String, String> searcher(String targetSearch)
    {
        HashMap<String, String> searchArr = new HashMap<>();
        Word temp = new Word();
        HashMap<String, String> cloneD = newD.arrayReturn();
        for (Map.Entry<String, String> item : cloneD.entrySet())
        {
            if (item.getKey().startsWith(targetSearch))
            {
                temp.setWord_target(item.getKey());
                temp.setWord_explain(item.getValue());
                searchArr.put(temp.getWord_target(), temp.getWord_explain());
            }
        }
        return searchArr;
    }

    public void dictionaryExportToFile()
    {
        try
        {
            FileOutputStream fileOS = new FileOutputStream("src/main/resources/data/dictionaries.txt");
            OutputStreamWriter outSW = new OutputStreamWriter(fileOS, "UTF8");
            BufferedWriter buffWT = new BufferedWriter(outSW);
            for (Map.Entry<String, String> item : newD.dictArr.entrySet())
            {
                buffWT.write(item.getKey() + "\t" + item.getValue());
                buffWT.newLine();
            }
            buffWT.flush();
            buffWT.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public boolean dictionaryExportToFile(Word word)
    {
        if (!newD.dictArr.containsKey(word.getWord_target())) {
            String data = word.getWord_target() + "\t" + word.getWord_explain();
            try {
                File file = new File("src/main/resources/data/dictionaries.txt");
                FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return true;
        }
        return false;
    }

    public HashMap<String, String> dictionarySearcher(String targetSearch)
    {
         if (targetSearch.trim().equals("")) return new HashMap<>();
        return this.searcher(targetSearch);
    }
}
