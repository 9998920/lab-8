package ua.opnu;

import java.util.Arrays;
import java.util.function.Predicate;


// Завдання 1: Клас MyOptional<T>

class MyOptional<T> {
    private final T value;
    private final boolean present;

    public MyOptional() {
        this.value = null;
        this.present = false;
    }

    public MyOptional(T value) {
        if (value == null) {
            throw new IllegalArgumentException("MyOptional не приймає null");
        }
        this.value = value;
        this.present = true;
    }

    public boolean isPresent() {
        return present;
    }

    public boolean isEmpty() {
        return !present;
    }

    public T get() {
        if (!present) {
            throw new IllegalStateException("Значення відсутнє");
        }
        return value;
    }

    public T orElse(T defaultValue) {
        return present ? value : defaultValue;
    }

    @Override
    public String toString() {
        return present ? "MyOptional[value=" + value + "]" : "MyOptional[empty]";
    }
}


// Завдання 2: Клас BookData з Comparable<BookData>

class BookData implements Comparable<BookData> {
    private String title;
    private String author;
    private int reviews;
    private double total;

    public BookData(String title, String author, int reviews, double total) {
        this.title = title;
        this.author = author;
        this.reviews = reviews;
        this.total = total;
    }

    public double getRating() {
        return reviews == 0 ? 0 : total / reviews;
    }

    @Override
    public int compareTo(BookData other) {
        int ratingComparison = Double.compare(other.getRating(), this.getRating());
        if (ratingComparison != 0) {
            return ratingComparison;
        }
        return this.title.compareTo(other.title);
    }

    @Override
    public String toString() {
        return title + " (Рейтинг: " + getRating() + ")";
    }
}

// Завдання 3: Клас Printer з узагальненим методом

class Printer {
    public <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
}

// Завдання 6: Узагальнені кортежі (Tuples)

class GenericTwoTuple<T, V> {
    public final T first;
    public final V second;

    public GenericTwoTuple(T first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

class GenericThreeTuple<T, V, S> {
    private final GenericTwoTuple<T, V> twoTuple;
    public final S third;

    public GenericThreeTuple(T first, V second, S third) {
        this.twoTuple = new GenericTwoTuple<>(first, second);
        this.third = third;
    }

    public T getFirst() {
        return twoTuple.first;
    }

    public V getSecond() {
        return twoTuple.second;
    }

    @Override
    public String toString() {
        return "(" + getFirst() + ", " + getSecond() + ", " + third + ")";
    }
}

// Головний клас з методом main

public class Main {

    // Завдання 4: Узагальнений метод filter

    @SuppressWarnings("unchecked")
    public static <T> T[] filter(T[] input, Predicate<T> p) {
        T[] result = (T[]) new Object[input.length];

        int counter = 0;
        for (T element : input) {
            if (p.test(element)) {
                result[counter] = element;
                counter++;
            }
        }

        return Arrays.copyOfRange(result, 0, counter, (Class<? extends T[]>) input.getClass());
    }

    // Завдання 5: Узагальнений метод contains

    public static <T extends Comparable<T>, V extends T> boolean contains(T[] array, V element) {
        for (T current : array) {
            if (current.compareTo(element) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("=== Завдання 1: MyOptional ===");
        MyOptional<String> middleName = new MyOptional<>();
        System.out.println(middleName);
        System.out.println("isPresent: " + middleName.isPresent());
        System.out.println("orElse: " + middleName.orElse("немає"));

        MyOptional<String> username = new MyOptional<>("admin");
        System.out.println(username);
        System.out.println("isPresent: " + username.isPresent());
        System.out.println("get(): " + username.get());
        System.out.println("orElse: " + username.orElse("guest"));

        try {
            middleName.get();
        } catch (IllegalStateException ex) {
            System.out.println("Очікуваний виняток: " + ex.getMessage());
        }

        try {
            new MyOptional<String>(null);
        } catch (IllegalArgumentException ex) {
            System.out.println("Правильно не дозволив null: " + ex.getMessage());
        }

        System.out.println("\n=== Завдання 2: BookData Comparable ===");
        BookData[] books = {
                new BookData("Book A", "Author X", 100, 450), // Рейтинг: 4.5
                new BookData("Book B", "Author Y", 50, 250),  // Рейтинг: 5.0
                new BookData("Book C", "Author Z", 10, 45)    // Рейтинг: 4.5
        };
        Arrays.sort(books);
        for (BookData b : books) {
            System.out.println(b);
        }

        System.out.println("\n=== Завдання 3: Printer ===");
        Printer myPrinter = new Printer();
        Integer[] intArray = {1, 2, 3};
        String[] stringArray = {"Hello", "World"};
        myPrinter.printArray(intArray);
        myPrinter.printArray(stringArray);

        System.out.println("\n=== Завдання 4: filter ===");
        String[] words = {"Apple", "Banana", "Cherry", "Dog"};
        // Фільтруємо слова довші за 5 символів
        String[] longWords = filter(words, s -> s.length() > 5);
        System.out.println(Arrays.toString(longWords));

        System.out.println("\n=== Завдання 5: contains ===");
        String[] dict = {"Java", "Python", "C++"};
        System.out.println("Містить 'Java'? " + contains(dict, "Java"));
        System.out.println("Містить 'Ruby'? " + contains(dict, "Ruby"));

        System.out.println("\n=== Завдання 6: Generic Tuples ===");
        GenericTwoTuple<String, Integer> person = new GenericTwoTuple<>("Глєб", 18);
        System.out.println("TwoTuple: " + person);

        GenericThreeTuple<String, String, Double> studentData =
                new GenericThreeTuple<>("Гармаш", "АІ-254", 95.5);
        System.out.println("ThreeTuple: " + studentData);
    }
}