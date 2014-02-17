package checkout;


import java.util.HashMap;
import java.util.Map;

public class PriceList {
    Map<String, Entry> entries = new HashMap<String, Entry>();

    public PriceList addEntry(Entry entry) {
        entries.put(entry.getItemCode(), entry);
        return this;
    }

    public Entry findEntry(String itemCode){
        return entries.get(itemCode);
    }


}
