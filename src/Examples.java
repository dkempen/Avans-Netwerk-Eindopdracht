import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Examples {

    Examples() {
        objectStreamExample();
        recursionExample();
        comparableComparatorExample();
    }

    private void objectStreamExample() {
        try {
            File file = new File(System.getProperty("user.dir") + "/resources/object.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            int number = 10;
            outputStream.writeObject(number);
            System.out.println("Wrote number " + number + " to " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File(System.getProperty("user.dir") + "/resources/object.txt");
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            int number = (int) inputStream.readObject();
            System.out.println("Read number " + number + " from " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recursionExample() {
        // Fibbonacci
        int index = 10;
        System.out.println("\nThe Fibonacci number at index " + index + " is " + fib(index));

        // Palindrome
        System.out.println("\nCheck if the word racecar is a palindrome");
        if (isPalindrome("racecar"))
            System.out.println("The word racecar is a palindrome");
        else
            System.out.println("The word racecar is not a palindrome");

        System.out.println("\nCheck if the word racecars is a palindrome");
        if (isPalindrome("racecars"))
            System.out.println("The word racecars is a palindrome");
        else
            System.out.println("The word racecars is not a palindrome");
    }

    private long fib(long index) {
        if (index == 0) // Base case
            return 0;
        else if (index == 1) // Base case
            return 1;
        else // Reduction and recursive calls
            return fib(index - 1) + fib(index - 2);
    }

    private boolean isPalindrome(String word) {
        return isPalindrome(word, 0, word.length() - 1);
    }

    private boolean isPalindrome(String word, int low, int high) {
        if (high <= low) // Base case
            return true;
        else if (word.charAt(low) != word.charAt(high)) // Base case
            return false;
        else // Reduction and recursive calls
            return isPalindrome(word, low + 1, high - 1);
    }

    private void comparableComparatorExample() {
        List<Game> games = new ArrayList<>();
        games.add(new Game("Counter-Strike: Global Offensive", "FPS", 2012, 8.3));
        games.add(new Game("Grand Theft Auto V", "Open World", 2013, 9.6));
        games.add(new Game("Fortnite", "FPS", 2017, 8.4));
        games.add(new Game("Rust", "Survival", 2013, 7.0));
        games.add(new Game("Planet Coaster", "Simulatie", 2016, 9.7));

        // Sorteer op natuurlijke manier m.b.v. sort() en Comparable<Game>:
        Collections.sort(games);
        System.out.println("\nsort 1 - op natuurlijke wijze (Comparable) mbv Collection.sort():\n" + games);

        // Definieer een Comparator m.b.v. een lambda expressie:
        games.sort((Game f1, Game f2) -> f1.getTitle().compareTo(f2.getTitle()));
        System.out.println("\nsort 2 - Comparator comparing op titel:\n" + games);

        games.sort(Comparator.comparing(Game::getYear).thenComparing(Game::getTitle));
        System.out.println("\nsort 3 - Comparator comparing op year en dan op titel:\n" + games);

        // Je kan ook een lambda expressie geven die dezelfde signatuur heeft als de compareTo methode
        // uit het Comparator interface.
        //   reverse sort:
        games.sort((Game f1, Game f2) -> f2.getTitle().compareTo(f1.getTitle()));
        System.out.println("\nsort 4 - reverse titel met lambda:\n" + games);

        //Lambda expression with type information removed (moet int zijn).
        games.sort((f1, f2) -> (int) (f1.getScore() * 10 - (f2.getScore() * 10)));
        System.out.println("\nsort 5 - op score met lambda:\n" + games);

        // class::method is a Java 8 method reference
        games.sort(Comparator.comparing(Game::getGenre));

        // De forEach methode
        System.out.println("Foreach:");
        games.forEach(g -> System.out.println(g.getTitle() + " in " + g.getYear()));
    }

    class Game implements Comparable<Game> {

        private String title;
        private String genre;
        private int year;
        private double score;

        Game(String title, String genre, int year, double score) {
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.score = score;
        }

        @Override
        public String toString() {
            return title + ", " + genre + ", " + year + ", " + score + "\n";
        }

        @Override
        public int compareTo(Game o) {
            return title.compareTo(o.title);
        }

        String getTitle() {
            return title;
        }

        String getGenre() {
            return genre;
        }

        int getYear() {
            return year;
        }

        double getScore() {
            return score;
        }
    }
}
