package dictionaryAction;

import CommandLine.DictionaryManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class searchAction extends DictionaryManagement {
    public ArrayList<String> getSearchs(String  target)
    {
        HashMap<String, String> words = this.dictionarySearcher(target);
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, String> item : words.entrySet())
        {
            result.add(item.getKey());
        }
        return result;
    }

}
