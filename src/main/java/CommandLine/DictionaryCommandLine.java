package CommandLine;

import CommandLine.DictionaryManagement;

import java.util.HashMap;
import java.util.Map;

public class DictionaryCommandLine {

    DictionaryManagement dictM = new DictionaryManagement();
    /*
     * ham có chức năng hiển thị kết quả danh sách dữ liệu từ điển trên màn hình
     */
    public void showAllWords()
    {
        Dictionary temp = dictM.getNewD();
        System.out.println("No\t|English\t|Vietnamese");
        int count = 1;
        for(Map.Entry<String, String> item : temp.dictArr.entrySet())
        {
            System.out.println(count + "\t" + item.getKey() + "\t" + item.getValue());
        }
    }

    /*
    *chức năng gọi hàm insertFromCommandline() và showAllWords()
     */
    public void dictionaryBasic()
    {
        dictM.insertFromCommandline();
        this.showAllWords();
    }

    public void dictionaryAdvanced()
    {
        dictM.insertFromFile();
        this.showAllWords();
        dictM.dictionaryLookup();
    }

    public HashMap dictionarySearcher(String targetSearch)
    {
        if (targetSearch.equalsIgnoreCase("")) return new HashMap<>();
        return dictM.searcher(targetSearch);
    }
}
