class TrieNode {
    TrieNode[] child;
    int wordCount;
    boolean wordEnd;

    public TrieNode() {
        wordEnd = false;
        wordCount = 0;
        child = new TrieNode[26];
    }
}

class Trie {
    TrieNode root;
    boolean reverse;

    public Trie(boolean reverse) {
        this.root = new TrieNode();
        this.reverse = reverse;
    }

    public void insert(String word) {
        word = word.toLowerCase();
        if(reverse) word = reverseWord(word);
        insertWord(root, word);
    }

    public boolean search(String word) {
        word = word.toLowerCase();
        if(reverse) word = reverseWord(word);
        return searchWord(root, word);
    }

    public void delete(String word) {
        word = word.toLowerCase();
        if(reverse) word = reverseWord(word);
        deleteWord(root, word, 0);
    }

    public int prefix(String prefix) {
        prefix = prefix.toLowerCase();
        return countPrefix(root, prefix);
    }

    private void insertWord(TrieNode root, String word) {
        TrieNode cur = root;

        for(char c : word.toCharArray()) {
            if(cur.child[c-'a'] == null) {
                TrieNode newNode = new TrieNode();
                cur.child[c-'a'] = newNode;
            }

            cur = cur.child[c-'a'];
            cur.wordCount++;
        }

        cur.wordEnd = true;
     }

     private boolean searchWord(TrieNode root, String word) {
        TrieNode cur = root;

        for(char c : word.toCharArray()) {
            if(cur.child[c-'a'] == null) {
                return false;
            } else {
                cur = cur.child[c-'a'];
            }
        }

        return cur.wordEnd;
     }

     private boolean isEmpty(TrieNode root) {
        for(int i = 0; i < 26; i++) {
            if(root.child[i] != null) 
                return false;
        }
        return true;
     }

     private TrieNode deleteWord(TrieNode root, String word, int depth) {
        if(root == null) 
            return null;

        if(depth == word.length()) {
            if(root.wordEnd) 
                root.wordEnd = false;

            if(isEmpty(root))
                root = null;
            
            return root;
        }

        int index = word.charAt(depth) - 'a';
        root.child[index].wordCount--;
        root.child[index] = deleteWord(root.child[index], word, depth+1);

        if(isEmpty(root) && !root.wordEnd) {
            root = null;
        }

        return root;
     }

     private int countPrefix(TrieNode root, String prefix) {
        TrieNode cur = root;

        for(char c : prefix.toCharArray()) {
            if(cur.child[c-'a'] == null) {
                return 0;
            } else {
                cur = cur.child[c-'a'];
            }
        }

        return cur.wordCount;
     }

     private String reverseWord(String word) {
        String res = "";
        char ch;

        for(int i = 0; i < word.length(); i++) {
            ch = word.charAt(i);
            res = ch + res;
        }

        return res;
     }
}


public class TrieModel {

    public static void main(String[] args) {
        Trie t = new Trie(false);

        t.insert("HELLO");
        t.insert("CAT");
        t.insert("HI");
        t.insert("het");

        System.out.println(t.search("cat"));

        t.delete("het");

        System.out.println(t.prefix("h"));
    }
    

}
