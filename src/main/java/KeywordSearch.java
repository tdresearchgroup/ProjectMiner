import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import java.util.HashMap;
import java.util.Map;

public class KeywordSearch {

    /**
     * tracks Ticket_number, keyword, and keyword_count for a single project.
     */
    private Table<Integer, String, Integer> keywordFrequency;

    public KeywordSearch(){
        keywordFrequency = TreeBasedTable.create();
    }

    public void importer(){

    }
}
