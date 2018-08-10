package codingdojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * While shopping online in a Store, the Cart stores the Items you intend to buy
 */
public class Cart implements ModelObject {
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> unavailableItems = new ArrayList<>();
    private int weight = 0;

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void addItems(Collection<Item> items) {
        this.items.addAll(items);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + displayItems(items) +
                "unavailable=" + displayItems(unavailableItems) +
                '}';
    }

    private String displayItems(List<Item> items) {
        StringBuffer itemDisplay = new StringBuffer("\n");
        for (Item item : items) {
            itemDisplay.append(item.toString());
            itemDisplay.append("\n");
        }
        return itemDisplay.toString();
    }

    @Override
    public void saveToDatabase() {
        throw new UnsupportedOperationException("missing from this exercise - shouldn't be called from a unit test");
    }

    public Collection<Item> getUnavailableItems() {
        return unavailableItems;
    }

    public void updateUnavailableItems() {
        for (Item item : getItems()) {
            if ("EVENT".equals(item.getType())) {
                markAsUnavailable(item);
            }
        }
    }

    public void updateUnavailableItems(Store store) {
        if ( store != null) {
            ArrayList<Item> newItems = new ArrayList<>();
            for (Item item : getItems()) {
                if ("EVENT".equals(item.getType()) || !store.hasItem(item)) {
                    markAsUnavailable(item);
                }
                if ("EVENT".equals(item.getType()) && store.hasItem(item)) {
                    newItems.add(store.getItem(item.getName()));
                }
                weight += item.getWeight();
            }
            for (Item item : getUnavailableItems()) {
                weight -= item.getWeight();
            }
            addItems(newItems);
        } else {
            updateUnavailableItems();
        }
    }

    private void markAsUnavailable(Item item) {
        this.unavailableItems.add(item);
    }

    public long getWeight() {
        return weight;
    }
}
