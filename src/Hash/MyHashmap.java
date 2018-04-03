package Hash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by leiwang on 4/2/18.
 */
//TODO needs more work
public class MyHashmap {
    public static void main(String args[]) {

    }

    private class HashEntry
    {
        private int key;
        private int value;
        HashEntry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private class HashMapImpl {
        final int SIZE_OF_TABLE = 128;
        List<List<HashEntry>> table;// = new ArrayList<>();

        HashMapImpl() {
            table = new ArrayList<>(SIZE_OF_TABLE);
            for (int i = 0; i < table.size(); i ++) {
                table.set(i,null);
            }
        }

        public void put(int key, int value) {
            int index = hashCodeNew(key);
            HashEntry hashEntry = new HashEntry(key, value);

            List<HashEntry> chain = table.get(index);
            if (chain == null) {
                chain = new ArrayList<>();
            }

            Iterator<HashEntry> it = chain.iterator();
            while(it.hasNext()){
                if(it.next().key == key){
                    it.remove();
                    break;
                }
            }
            chain.add(hashEntry);
            table.add(chain);
        }

        public int get(int key) {
            int index = hashCodeNew(key);
            if (table.get(index) != null) {
                for(HashEntry entry : table.get(index)){
                    if(entry.key == key)
                        return entry.value;
                }
            }
            throw new NoSuchElementException("The key: \"" + key + "\" was not found in the map!");
        }


        private int hashCodeNew(int h) {
            //mask out sign bit
            return (h & Integer.MAX_VALUE) % SIZE_OF_TABLE;
        }

//        private boolean equals() {
//
//        }
    }
}
