package ucsd.w2.hw3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class PhoneBook {

    private FastScanner in = new FastScanner();
    // Keep list of all existing (i.e. not deleted yet) contacts.
//    private List<Contact> contacts = new ArrayList<>();
    Contact[] contacts;
    public static int M;
    public static long a, b, p;
    public static void main(String[] args) {
        new PhoneBook().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }

    private static int hashValue(int input) {
        int out =  (int)(Math.abs((a * input + b) % p) % M);
        return out;
    }
    private static int next(int n) {
        if (n < M - 1 && n >= 0)
            return (n + 1);
        else
            return 0;
    }
    private void processQuery(Query query) {
        /*
        if (query.type.equals("add")) {
            // if we already have contact with such number,
            // we should rewrite contact's name
            boolean wasFound = false;
            for (Contact contact : contacts)
                if (contact.number == query.number) {
                    contact.name = query.name;
                    wasFound = true;
                    break;
                }
            // otherwise, just add it
            if (!wasFound)
                contacts.add(new Contact(query.name, query.number));
        } else if (query.type.equals("del")) {
            for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); )
                if (it.next().number == query.number) {
                    it.remove();
                    break;
                }
        } else {
            String response = "not found";
            for (Contact contact: contacts)
                if (contact.number == query.number) {
                    response = contact.name;
                    break;
                }
            writeResponse(response);
        }   */ 
        if (query.type.equals("add"))
        {
            int n = hashValue(query.number);
            Contact cur = contacts[n];
            while (cur != null)
            {
                if (cur.number == query.number)
                {
                    cur.name = query.name;
                    return;              //overwrite the corresponding name
                }
                n = next(n);
                cur = contacts[n];
            }
            contacts[n] = new Contact(query.name, query.number);
        } else if (query.type.equals("del")) {
            int n = hashValue(query.number);
            Contact cur = contacts[n];
            while (cur != null)
            {
                if (cur.number == query.number)
                {
                    contacts[n] = null;
                    return;              //overwrite the corresponding name
                }
                n = next(n);
                cur = contacts[n];
            }
        } else {
            int n = hashValue(query.number);
            Contact cur = contacts[n];
            String response = "not found";
            while (cur != null)
            {
                if (cur.number == query.number)
                {
                    response = cur.name;
                    break;              //overwrite the corresponding name
                }
                n = next(n);
                cur = contacts[n];
            }
            writeResponse(response);
        }
            
    }
    public void processQueries() {
        int queryCount = in.nextInt();
        M = 2 * queryCount;
        contacts = new Contact[M];       // set the load factor to 0.5;
        p = 10000019;
//        p = (int)Math.pow(10.0, 7.0) + 1;
//        while(!isPrime(p)) p++;
        a = (long)(Math.random() * p) % p;
        b = (long)(Math.random() * p) % p;
//        System.out.println("a = " + a + " b = " + b + " p = " + p);
        for (int i = 0; i < queryCount; ++i)
            processQuery(readQuery());
    }

    static class Contact {
        String name;
        int number;

        public Contact(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }

    static class Query {
        String type;
        String name;
        int number;

        public Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        public Query(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
