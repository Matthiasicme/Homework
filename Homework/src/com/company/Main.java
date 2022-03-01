package com.company;


import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Joke> jokes = new LinkedList<>();



        while (true) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("How many jokes to download?");
            jokes.addAll(downloadJokes(Integer.parseInt(scanner.nextLine())));
            System.out.println("You have " + jokes.size() + " jokes at your list.");

            SortJokeListByLength(jokes);
            System.out.println("The shortest joke is this one: ");
            System.out.println(jokes.get(0));

            System.out.println("Do you want to know how many two-part jokes were downloaded? YES or NO");
            String twoPart;
            twoPart = scanner.nextLine();
            if (twoPart.equals("yes")) {
                int twoPartJokesCounter = countTwoPartJokes(jokes);
                System.out.println("You downloaded " + twoPartJokesCounter + " two-part jokes:");
                System.out.println(jokes);
            }

            List<Joke> sexistJokes = getSexistJokes(jokes);

            if (!sexistJokes.isEmpty()) {
                String sexist;
                System.out.println("Do you want to see downloaded sexist jokes? YES or NO");
                sexist = scanner.nextLine();
                if (sexist.equals("yes")) {
                    System.out.println("Downloaded sexist jokes: ");
                    System.out.println(sexistJokes);
                }
            }



            String delete;
            System.out.println("Do you want to delete downloaded jokes? YES or NO");
            delete = scanner.nextLine();
            if (delete.equals("YES")) {

                jokes.clear();
            }
            System.out.println("Do you want to download more jokes? YES or NO");
            String downloadMore = scanner.nextLine();
            if (!downloadMore.equals("YES")) {
                break;
            }
        }
    }


    private static List<Joke> downloadJokes(int size) {
        JokeAPI connector = new JokeAPI();
        List<Joke> jokes = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            try {
                jokes.add(connector.getJoke("Any"));
            } catch (Exception e) {
                System.out.println("Sorry, there is an error");
                e.printStackTrace();
            }
        }
        return jokes;
    }

    private static void SortJokeListByLength(List<Joke> jokes) {
        Comparator<Joke> jokeComparator = Comparator.comparing(Joke::getJokeLength);

        Collections.sort(jokes, jokeComparator);

    }


    private static List<Joke> getSexistJokes(List<Joke> jokes) {
        List<Joke> sexistJokes = new LinkedList<>();

        for(Iterator<Joke> iter = jokes.iterator();iter.hasNext();) {
            Joke element = iter.next();
            if( element.sexist ) {
                sexistJokes.add(element);
            }
        }

        return sexistJokes;
    }


    private static int countTwoPartJokes(List<Joke> jokes) {
        int twoPartJokesCounter = 0;
        for(Iterator<Joke> iter = jokes.iterator();iter.hasNext();) {
            Joke element = iter.next();
            if( element.setup != null ) {
                twoPartJokesCounter++;
            }
        }
        return twoPartJokesCounter;
    }
}
