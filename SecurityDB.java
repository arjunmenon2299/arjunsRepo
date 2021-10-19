import java.util.*;


class Passenger {
    private String name;
    private String passportID;
    private int passengerHash;

    public Passenger(String name, String passportID) {
        super();
        this.name = name;
        this.passportID = passportID;
        this.passengerHash = calculateHash(passportID);
    }

    @Override
    public int hashCode() {
        return passengerHash;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Passenger) {
            return (this.passengerHash) == (((Passenger)o).passengerHash);
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public int getPassengerHash() {
        return passengerHash;
    }

    public void setPassengerHash(String key) {
        this.passengerHash = calculateHash(key);
    }

    public int calculateHash(String key) {
        int sum = 0;
        int hash = 0;
        int ascii = 0;
        int[] hashArray = new int[key.length()];

        for (int i = 0; i < key.length(); i++) {
            ascii = (int) key.charAt(i);
            hashArray[i] = ascii;
        }

        for (int i = 0; i < hashArray.length; i++) {
            sum += hashArray[i];
            hashArray[i] = sum + 1;
        }

        for (int i = 0; i < hashArray.length; i++) {
            hash += hashArray[i];
        }
        return hash;
    }

}

/**
 * Node ADT
 */
class Node<String, Passenger> {

    public String key;
    public Passenger value;
    public Node<String, Passenger> next;
    public int hashCode;

    public Node(String key, Passenger value, int hashCode) {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }
    
    public String getKey() {
        return key;
    }

    public Passenger getValue() {
        return value;
    }

    public Node<String, Passenger> getNext() {
        return next;
    }

    public long calculateHash(String key) {
        long sum = 0;
        long hash = 0;
        long ascii = 0;
        int keyLen = key.toString().length();
        long[] hashArray = new long[keyLen];

        for (int i = 0; i < keyLen; i++) {
            ascii = (int) key.toString().charAt(i);
            hashArray[i] = ascii;
        }

        for (int i = 0; i < hashArray.length; i++) {
            sum += hashArray[i];
            hashArray[i] = sum + 1;
        }

        for (int i = 0; i < hashArray.length; i++) {
            hash += hashArray[i];
        }
        return hash;
    }
}

/**
 * Map ADT
 */
class Map <String, Passenger> {
    public List<Node<String, Passenger>> bucketArray;
    public int bucketCount;
    public int size;

    public Map(int size) {
        this.bucketCount = 20;
        this.size = size;

        //create empty chains
        for (int i = 0; i < bucketCount; i++) {
            bucketArray.add(null);
        }
    }  
    
    public int size() { return size; }

    public boolean isEmpty() { return size() == 0; }

    private final int hashCode(String id) {
        return (int) calculateHash(id);
    }

    int getBucketIndex(String id){
        int hashCode = hashCode(id);
        int index = hashCode % bucketCount;
        // key.hashCode() coule be negative.
        index = index < 0 ? index * -1 : index;
        return index;
    }

     // Method to remove a given key
     public Passenger remove(String id) {
         // Apply hash function to find index for given key
         int bucketIndex = getBucketIndex(id);
         int hashCode = hashCode(id);
         // Get head of chain
         Node<String, Passenger> head = bucketArray.get(bucketIndex);
  
         // Search for key in its chain
         Node<String, Passenger> prev = null;
         while (head != null) {
             // If Key found
             if (head.getKey().equals(id) && hashCode == head.hashCode) {}
  
             // Else keep moving in chain
             prev = head;
             head = head.next;
         }
  
         // If key was not there
         if (head == null)
             return null;
  
         // Reduce size
         size--;
  
         // Remove key
         if (prev != null)
             prev.next = head.next;
         else
             bucketArray.set(bucketIndex, head.next);
  
         return head.value;
     }

    // Returns value for a key
    public Passenger get(String key) {
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
       
        Node<String, Passenger> head = bucketArray.get(bucketIndex);
 
        // Search key in chain
        while (head != null) {
            if (head.key.equals(key) && head.hashCode == hashCode)
                return head.value;
            head = head.next;
        }
 
        // If key not found
        return null;
    }


    // Adds a key value pair to hash
    public void add(String key, Passenger value) {
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        Node<String, Passenger> head = bucketArray.get(bucketIndex);
 
        // Check if key is already present
        while (head != null) {
            if (head.key.equals(key) && head.hashCode == hashCode) {
                head.value = value;
                return;
            }
            head = head.next;
        }
 
        // Insert key in chain
        size++;
        head = bucketArray.get(bucketIndex);
        Node<String, Passenger> newNode
            = new Node<String, Passenger>(key, value, hashCode);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);
 
        // If load factor goes beyond threshold, then
        // double hash table size
        if ((1.0 * size) / bucketCount >= 0.7) {
            List<Node<String, Passenger>> temp = bucketArray;
            bucketCount = 2 * bucketCount;
            size = 0;
            for (int i = 0; i < bucketCount; i++)
                bucketArray.add(null);
 
            for (Node<String, Passenger> headNode : temp) {
                while (headNode != null) {
                    add(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }

    public long calculateHash(String key) {
        long sum = 0;
        long hash = 0;
        long ascii = 0;
        int keyLen = key.toString().length();
        long[] hashArray = new long[keyLen];

        for (int i = 0; i < keyLen; i++) {
            ascii = (int) key.toString().charAt(i);
            hashArray[i] = ascii;
        }

        for (int i = 0; i < hashArray.length; i++) {
            sum += hashArray[i];
            hashArray[i] = sum + 1;
        }

        for (int i = 0; i < hashArray.length; i++) {
            hash += hashArray[i];
        }
        return hash;
    }
}

/**
 * MAIN SECURITY DB CLASS
 */
public class SecurityDB extends SecurityDBBase {
    private int numPlanes;
    private int numPassengersPerPlane;
    private Hashtable<Integer, Passenger> hehe;
    public Map<String, Passenger> myMap;


    /**
     * Creates an empty hashtable and a variable to count non-empty elements.
     *
     * @param numPlanes             number of planes per day
     * @param numPassengersPerPlane number of passengers per plane
     */
    public SecurityDB(int numPlanes, int numPassengersPerPlane) {
        super(numPlanes, numPassengersPerPlane);
        this.numPlanes = numPlanes;
        this.numPassengersPerPlane = numPassengersPerPlane;

        int initialSize = numPassengersPerPlane*numPlanes+1;
        int finalSize = checkNumber(initialSize);
        this.myMap = new Map<>(finalSize);

        //System.out.println("hiii");
    }

    /* Implement all the necessary methods here */
    /**
     * checks if a number is prime or not
     * @param n integer number to be checked
     * @return true if prime, false otherwise
     */
    static boolean isPrime(int n) {
        // Corner cases
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;

        // This is checked so that we can skip
        // middle five numbers in below loop
        if (n % 2 == 0 || n % 3 == 0)
            return false;

        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        return true;
    }

    /**
     * checks whether the given integer can be used to construct the hashtable
     * @param m = the integer to be checked
     * @return number worthy of being used.
     */
    static int checkNumber(int m) {
        if (isPrime(m)) {
            return m;
        } else {
            return checkNumber(m+1);
        }
    }

    static int checkSize(int n) {
        if (n < 1021) {
            return n;
        } else {
            return 1021;
        }
    }

    @Override
    public int calculateHashCode(String key) {
        int sum = 0;
        int hash = 0;
        int ascii = 0;
        int[] hashArray = new int[key.length()];

        for (int i = 0; i < key.length(); i++) {
            ascii = (int) key.charAt(i);
            hashArray[i] = ascii;
        }

        for (int i = 0; i < hashArray.length; i++) {
            sum += hashArray[i];
            hashArray[i] = sum + 1;
        }

        for (int i = 0; i < hashArray.length; i++) {
            hash += hashArray[i];
        }
        return hash;
    }

    /**
     *
     * @return
     */    
    @Override
    public int size() {
        return myMap.size();
    }

    /**
     *
     * @param passportId passenger's passport ID
     * @return
     */
    @Override
    public String get(String passportId) {
        return myMap.get(passportId).getPassportID();
    }

    @Override
    public boolean remove(String passportId) {
        if (myMap.remove(passportId) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addPassenger(String name, String passportId) {
        Passenger newPassenger = new Passenger(name, passportId);

        if (myMap.get(passportId) == null) {
            myMap.add(passportId, newPassenger);
            return true;
        }
       return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int getIndex(String passportId) {
        return myMap.getBucketIndex(passportId);
    }

    /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        The following main function is provided for simple debugging only

        Note: to enable assertions, you need to add the "-ea" flag to the
        VM options of SecurityDB's run configuration
     */
    public static void main(String[] args) {
        SecurityDB db = new SecurityDB(3, 2);

        System.out.println(db.calculateHashCode("Asb23f"));

        // add
        db.addPassenger("Rob Bekker", "Asb23f");
        db.addPassenger("Kira Adams", "MKSD23");
        db.addPassenger("Kira Adams", "MKSD24");
        db.addPassenger("Kira Adams", "MKSD2s4");
        System.out.println("size " + db.size());

        assert db.contains("Asb23f");

        // count
        assert db.count() == 3;

        // del
        db.remove("MKSD23");
        assert !db.contains("MKSD23");
        assert db.contains("Asb23f");

        // hashcodes
        assert db.calculateHashCode("Asb23f") == 1717;

        // suspicious
        db = new SecurityDB(3, 2);
        db.addPassenger("Rob Bekker", "Asb23f");
        db.addPassenger("Robert Bekker", "Asb23f");
        // Should print a warning to stderr
    }

}


// class Node<Integer, Passenger> {
//     private int key;
//     private Passenger value;
//     private Node<Integer, Passenger> next;
//     private int hashCode;

//     public int getKey() {
//         return key;
//     }

//     public void setKey(Integer key) {
//         this.key = (int)key;
//     }

//     public Passenger getValue() {
//         return value;
//     }

//     public void setValue(Passenger value) {
//         this.value = value;
//     }

//     public Node<Integer, Passenger> getNext() {
//         return next;
//     }

//     public void setNext(Node<Integer, Passenger> next) {
//         this.next = next;
//     }

//     public long getHashCode() {
//         return hashCode;
//     }

//     public void setHashCode(String key) {
//         this.hashCode = calculateHash(key);
//     }

//     public int calculateHash(String key) {
//         int sum = 0;
//         int hash = 0;
//         int ascii = 0;
//         int[] hashArray = new int[key.length()];

//         for (int i = 0; i < key.length(); i++) {
//             ascii = (int) key.charAt(i);
//             hashArray[i] = ascii;
//         }

//         for (int i = 0; i < hashArray.length; i++) {
//             sum += hashArray[i];
//             hashArray[i] = sum + 1;
//         }

//         for (int i = 0; i < hashArray.length; i++) {
//             hash += hashArray[i];
//         }
//         return hash;
//     }
// }


// abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {

// }

/* Add any additional helper classes here */

// class DoublyLinkedListNode {
//     DoublyLinkedListNode previous, next;
//     String passengerName;
//     String passengerPassportID;

//     DoublyLinkedListNode (String passengerName, String passportID) {
//         this.passengerName = passengerName;
//         this.passengerPassportID = passportID;
//         next = null;
//         previous = null;
//     }
// }

// class SecurityDBHashtable {
//     //
//     DoublyLinkedListNode[] hashTable;
//     // stores size of the hashtable
//     int hashTableSize;

//     // constructor
//     SecurityDBHashtable(int size) {
//         hashTable = new DoublyLinkedListNode[size];
//         hashTableSize = 0;
//     }

//     public boolean isEmpty() {return hashTableSize == 0;}

//     // Function to clear/delete all elements from Hash table
//     public void clear()
//     {
//         // Capacity of Hash Table
//         int hashTableLength = hashTable.length;

//         // Creating new empty Hash Table
//         // of same initial capacity
//         hashTable = new DoublyLinkedListNode[hashTableLength];
//         hashTableSize = 0;
//     }

//     public int getSize() { return hashTableSize; }

//     // Definition of Hash function
//     public int hashFunction(String key) {
//         int sum = 0;
//         int hash = 0;
//         int ascii = 0;
//         int[] hashArray = new int[key.length()];

//         for (int i = 0; i < key.length(); i++) {
//             ascii = (int) key.charAt(i);
//             hashArray[i] = ascii;
//         }

//         for (int i = 0; i < hashArray.length; i++) {
//             sum += hashArray[i];
//             hashArray[i] = sum + 1;
//         }

//         for (int i = 0; i < hashArray.length; i++) {
//             hash += hashArray[i];
//         }
//         return hash;
//     }

//     public void add(String pName, String ppID) {
//         hashTableSize++;

//         int insertPosition = hashFunction(ppID);

//         DoublyLinkedListNode node = new DoublyLinkedListNode(pName, ppID);

//         DoublyLinkedListNode startNode = hashTable[insertPosition];

//         if (hashTable[insertPosition] != null) {
//             node.next = startNode;
//             startNode.previous = node;
//         }
//         hashTable[insertPosition] = node;
//     }

//     public void remove(String ppID) {
//         try {
//             int insertPosition = hashFunction(ppID);

//             DoublyLinkedListNode startNode = hashTable[insertPosition];
//             DoublyLinkedListNode end = startNode;

//             // if passportID is found at the start
//             if (startNode.passengerPassportID.equals(ppID)) {
//                 hashTableSize--;
//                 if (startNode.next == null) {
//                     hashTable[insertPosition] = null;
//                 }
//                 startNode = startNode.next;
//                 startNode.previous = null;
//                 hashTable[insertPosition] = startNode;
//             }

//             while (end.next != null && !end.next.passengerPassportID.equals(ppID)) {
//                 end = end.next;

//                 if (end.next == null) {
//                     System.out.println("\nElement not found\n");
//                 }
//                 hashTableSize--;

//                 if (end.next.next == null) {
//                     end.next = null;
//                 }
//                 end.next.next.previous = end;
//                 end.next = end.next.next;

//                 hashTable[insertPosition] = startNode;
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
