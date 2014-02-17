package checkout.data;

import checkout.Entry;
import checkout.PriceList;

import java.util.ArrayList;
import java.util.List;

public class PriceListBuilder {
    private EntryBuilder entryBuilder;

    private List<Entry> entries = new ArrayList<Entry>();


    public PriceList build() {
        if (entryBuilder != null){
            entries.add(entryBuilder.build());
            entryBuilder = null;
        }

        PriceList priceList = new PriceList();
        for (Entry entry : entries){
            priceList.addEntry(entry);
        }

        return priceList;
    }

    public EntryBuilder addEntry() {

        if (entryBuilder != null){
            entries.add(entryBuilder.build());
        }

        entryBuilder = new EntryBuilder();

        return entryBuilder;
    }

    public EntryBuilder addEntry(String itemCode){
        addEntry().withItemCode(itemCode);
        return entryBuilder;
    }
}
