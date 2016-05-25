import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
//    private List<String> elems;
    // for hash function
    private Node[] nodes;
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
//        System.out.println("is ")
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }
    
    private static class Node {
        public Node next;
        public String s;
        Node(String string) {
            this.s = string;
            this.next = null;
        }
        Node(String string, Node nextNode)
        {
            this.s = string;
            this.next = nextNode;
        }
    }
    private void processQuery(Query query) {
        int n = 0;
        if (!query.type.equals("check"))
            n = hashFunc(query.s);
        loop: switch (query.type) {
            case "add":
                Node c = nodes[n];
                if (c == null)
                {
                    nodes[n] = new Node(query.s);
                    break loop;
                } else if (c.s.equals(query.s)) {
                    break loop;
                } else {                
                while (c.next != null)
                {
                    if (c.next.s.equals(query.s))
                        break loop;
                    c = c.next;
                }
                c.next = new Node(query.s);
                break;
                }
            case "del":
                if (nodes[n] == null) break;
                Node cur = nodes[n];
                if (cur.s.equals(query.s))
                {
                    nodes[n] = cur.next;
                    break loop;
                }
                while (cur.next != null)
                {
                    if (cur.next.s.equals(query.s))
                    {
                        cur.next = cur.next.next;
                        break loop;
                    }
                    cur = cur.next;
                }
                break;
            case "find":
                Node cc = nodes[n];
                boolean answer = false;
                while (cc != null)
                {
                    if (cc.s.equals(query.s))
                        answer = true;
                    cc = cc.next;
                }
                writeSearchResult(answer);
//                writeSearchResult(elems.contains(query.s));
                break;
            case "check":
                Node ccc = nodes[query.ind];
//                if (cc == null)
//                {
//                    out.println("");
//                    break loop;
//                }
                while (ccc != null)
                {
                    out.print(ccc.s + " ");
                    ccc = ccc.next;
                }
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
//        elems = new ArrayList<>();
        
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();            // number of queries input
        nodes = new Node[bucketCount];
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
