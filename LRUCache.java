import java.util.HashMap;


public class LRUCache {
	int capacity;
    HashMap<Integer, Node> map = new HashMap<Integer, Node>();
    Node head=null;
    Node tail=null;
    private static Object sharedLock = new Object();//this object is for shared lock for thread safety
    
    public LRUCache(int capacity)
    {
    	this.capacity=capacity;
    }
    
    public void remove(Node n){
    	//function to remove given node
        if(n==head){
        	head = n.next;
        }else{
        	n.prev.next = n.next;
        }
 
        if(n==tail){
            tail = n.prev;
        }else{
            n.next.prev = n.prev;
        }
 
    }
 
    public void insertatHead(Node n){
    	/*this function inserts the given node at head of the linked list*/
        n.next = head;
        n.prev = null;
 
        if(head!=null)
            head.prev = n;
 
        head = n;
 
        if(tail ==null)
            tail = head;
    }
    
    public int get(int key) {
    	/*function to get value if key exists in cache*/
    	synchronized(sharedLock)
    	{
    		if(map.containsKey(key)){
                Node n = map.get(key);
                remove(n);
                insertatHead(n);
                return n.value;
            }
     
            return -1;
    	}
    }
    
    public void put(int key, int value) {
    	/*if key already present, then update its value and reposition it at head*/
        synchronized(sharedLock)
        {
        	if(map.containsKey(key)){
                Node old = map.get(key);
                old.value = value;
                remove(old);
                insertatHead(old);
            }
            /*else, create a node and if capacity is not exceeded then insert this node at head*/
            /*if capacity is exceeded, then remove least recently used node at tail and then insert new node*/
            
            else{
                Node created = new Node(key, value);
                if(map.size()>=capacity){
                	//capacity exceeded, remove LRU node from both map and linked list
                    map.remove(tail.key);
                    remove(tail);
                }    
                insertatHead(created);
                map.put(key, created);
            }
        }
    }
}
