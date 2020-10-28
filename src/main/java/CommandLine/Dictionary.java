package CommandLine;

import java.util.HashMap;
import java.util.Scanner;

public class Dictionary {
        HashMap<String , String > dictArr = new HashMap<>();
        int length = 0;

        /*
         *Setter, getter
         */
        public HashMap<String, String> arrayReturn()
        {
            return dictArr;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        //set gia tri vao hashmap
        public void setter(Word newWord) {
            if (!dictArr.containsKey(newWord.getWord_target()))
                dictArr.put(newWord.getWord_target(), newWord.getWord_explain());
            else System.out.println("value existed!");
        }
        /*
         * sửa dữ liệu phục vu cho DictionaryManagement
         */
        public void edit(String targetData, String explainData)
        {
                dictArr.put(targetData, explainData);
        }

        public void delete(String targetData)
        {
            if (dictArr.containsKey(targetData))
            {
                dictArr.remove(targetData);
            }
        }
        public boolean getExplain(String target)
        {
            if (dictArr.containsKey(target)) return  true;
            return  false;
        }


        /*
         *lấy dữ liệu
         */
        public Word getter(String key) {
            Word newWord = new Word();
            if ((dictArr.containsKey(key)))
            {
                newWord.setWord_target(key);
                newWord.setWord_explain(dictArr.get(key));
                return newWord;
            }
            return null;
        }
}
