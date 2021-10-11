import java.util.*;

public class SecurityDB extends SecurityDBBase {
    private SecurityDBHashtable test;
    private int numPlanes;
    private int numPassengersPerPlane;

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
        this.test = new SecurityDBHashtable(finalSize);
        UnsortedTab
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
        return test.hashFunction(key);
    }

    /**
     *
     * @return
     */    
    @Override
    public int size() {
        return test.hashTableSize;
    }

    /**
     *
     * @param passportId passenger's passport ID
     * @return
     */
    @Override
    public String get(String passportId) {
        return passportId;
    }

    @Override
    public boolean remove(String passportId) {
        return false;
    }

    @Override
    public boolean addPassenger(String name, String passportId) {
        return true;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int getIndex(String passportId) {
        return 0;
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

        //System.out.println(db.calculateHashCode("Asb23f"));

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
