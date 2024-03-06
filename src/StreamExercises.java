import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StreamExercises {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("src/dane.txt");

        List<String> dates = Files.readAllLines(path);

//        1.Wyświetl komentarze w kolejności chronologicznej
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(array -> array[2])
                .map(s -> LocalDate.parse(s, dateTimeFormatter))
                .sorted()
                .forEach(System.out::println);
        System.out.println();

//        2.Wyświetl tylko te komentarze, które mają więcej niż 10 polubień
            dates.stream()
                    .skip(1)
                    .map(s -> s.split(";"))
                    .map(Arrays::asList)
                    .filter(list -> Integer.parseInt(list.get(3)) > 10)
                    .forEach(list -> System.out.println(list.get(1).concat(" ").concat(list.get(3))
                            .concat(" ").concat("polubien")));
        System.out.println();

//        3.Znajdź najdłuższy oraz najkrótszy komentarz
        Optional<String> maxComment = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(Arrays::asList)
                .map(s -> s.get(1))
                .max(Comparator.comparingInt(String::length));
        System.out.println(maxComment.get());
        Optional<String> minComment = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(Arrays::asList)
                .map(s -> s.get(1))
                .min(Comparator.comparingInt(String::length));
        System.out.println(minComment.get());
        System.out.println();

//        4.Oblicz średnią liczbę polubień dla wszystkich komentarzy.
        Double averageLikeNum = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(s -> s[3])
                .map(Integer::parseInt)
                .collect(Collectors.averagingInt(Integer::intValue));
        System.out.println(averageLikeNum);

        double averageLikeNum2 = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .mapToInt(s -> Integer.parseInt(s[3]))
                .average()
                .orElse(0.0);
        System.out.println(averageLikeNum2);

//        5.Oblicz ile komentarzy zostało dodanych przez mężczyzn i kobiety.
        long ladiesComments = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(s -> s[0])
                .filter(s -> s.endsWith("a"))
                .count();
        System.out.println("Komentarze kobiet: " + ladiesComments);

        Map<Boolean, Long> mapComments = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(s -> s[0])
                .collect(Collectors.groupingBy(s -> s.endsWith("a"), Collectors.counting()));
        //  .collect(Collectors.partitioningBy(s -> s.endsWith("a")));
        System.out.println("Komentarze kobiet: " + mapComments.get(true));
        System.out.println("Komentarze mezczyzn: " + mapComments.get(false));

//        6.Oblicz łączną liczbę polubień dla wszystkich komentarzy.
        int sumOfLikes = dates.stream()
                .skip(1)
                .map(s -> s.split(";")[3])
                .mapToInt(Integer::parseInt)
                .sum();
        System.out.println(sumOfLikes);
        System.out.println();

//        7.Znajdź wszystkich użytkowników, którzy zostawili komentarze o długości przekraczającej 50 znaków.
        dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .filter(s -> s[1].length() > 50)
                .map(s -> s[0])
                .forEach(System.out::println);
        System.out.println();

//        8.Posortuj komentarze według liczby polubień w kolejności malejącej.
          dates.stream()
                  .skip(1)
                  .map(s -> s.split(";"))
                  .sorted(Comparator.comparing(s -> Integer.parseInt(s[3])))
                  .sorted((s1, s2) -> (Integer.valueOf(Integer.parseInt(s2[3]))).compareTo(Integer.parseInt(s1[3])))
                  .forEach(s -> System.out.println(s[1] + " " + s[3]));
        System.out.println();

//        9.Wyświetl komentarze dodane w październiku 2023.
          dates.stream()
                  .skip(1)
                  .map(s -> s.split(";"))
                  .filter(s -> LocalDate.parse(s[2], dateTimeFormatter).getMonthValue() == 10)
                  .forEach(s -> System.out.println(s[1] + s[2]));
        System.out.println();

//        10.Wyświetl unikalne daty, w których zostawiono komentarze.
        List<String> uniqueDates = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(Arrays::asList)
                .map(l -> l.get(2))
                .distinct()
                .toList();
        System.out.println(uniqueDates);

//        11.Oblicz sumę polubień dla komentarzy zostawionych w listopadzie 2023.
        int sum = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(Arrays::asList)
                .filter(s -> (LocalDate.parse(s.get(2), dateTimeFormatter).getMonthValue() == 11
                        && LocalDate.parse(s.get(2), dateTimeFormatter).getYear() == 2023))
                .map(s -> s.get(3))
                .map(s -> Integer.parseInt(s))
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Sum of likes in 11 2023: " + sum);

//        12.Wyświetl wszystkie unikalne imiona użytkowników.
        String uniqueNames = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(s -> s[0])
                .distinct()
                .collect(Collectors.joining(", "));
        System.out.println(uniqueNames);

//        13.Oblicz średnią długość komentarzy.
        Double averageLenOfComments = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(s -> s[1])
                .map(s -> s.toCharArray().length)
                .collect(Collectors.averagingInt(Integer::intValue));
        System.out.println("Srednia dlugosc komentarza: " + averageLenOfComments);

//        14.Oblicz średnią liczbę słów na komentarz.
        Double averageNumOfWordPerComment = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(s -> s[1])
                .map(s -> s.split(" "))
                .map(s -> s.length)
                .collect(Collectors.averagingInt(Integer::intValue));
        System.out.println("Srednia liczba slow na komentarz: " + averageNumOfWordPerComment);
        System.out.println();

//        15.Wyświetl komentarze, które zawierają przynajmniej jedno słowo zaczynające się na literę "s".
             dates.stream()
                     .skip(1)
                     .map(s -> s.split(";"))
                     .map(s -> Arrays.asList(s))
                     .map(s -> s.get(1))
                     .filter(s -> s.contains(" s") || s.startsWith("S"))
                     .forEach(s -> System.out.println(s));
        System.out.println();

//        16.Wyświetl komentarze użytkowników, których imiona zawierają literę "a" (bez względu na wielkość liter).
        dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .filter(s -> s[0].contains("a") || s[0].contains("A"))
                .forEach(s -> System.out.println(s[0] + " " + s[1]));
        System.out.println();
//        17.Posortuj komentarze według długości komentarza, a następnie wybierz 5 najdłuższych.
        dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(s -> s[1])
                .sorted(Comparator.comparing(String::length).reversed())
                .limit(5)
                .forEach(System.out::println);
        System.out.println();

//        18.Posortuj komentarze alfabetycznie według nazw użytkowników, a następnie wybierz 5 pierwszych komentarzy w alfabetycznym porządku.
        dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .sorted(Comparator.comparing(s -> s[0]))
               // .map(s -> s[1])
                .limit(5)
                .forEach(s -> System.out.println(s[0] + " " + s[1]));
        System.out.println();

//        19.Znajdź wszystkich użytkowników, którzy zostawili komentarze o długości przekraczającej średnią długość komentarzy.
        Double averLenOfComments = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .map(Arrays::asList)
                .map(s -> s.get(1) )
                .collect(Collectors.averagingInt(String::length));
        System.out.println(averLenOfComments);

        List<String> usersWithCommAboveAverNum = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .filter(s -> s[1].length() > averLenOfComments)
                .map(s -> s[0])
                .toList();
        System.out.println("Uzytkownicy ktorzy pozostawili komentarze powyzej sredniej liczby komentarzy: "
                + usersWithCommAboveAverNum);
        System.out.println();

//        20.Znajdź datę, w której zostawiono najwięcej komentarzy.
        Map<String, Integer> sortedMap = dates.stream()
                .skip(1)
                .map(s -> s.split(";"))
                .collect(Collectors.toMap(s -> s[2], s -> Integer.parseInt(s[3]), Integer::sum))
                .entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                .collect(Collectors.toMap(
                                     Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (e1, e2) -> e1,
                                        LinkedHashMap::new));
        Map.Entry<String, Integer> maxEntry = sortedMap.entrySet().iterator().next();


        System.out.println("Data " + maxEntry.getKey() +" ma najwiecej komentarzy: " + maxEntry.getValue());

    }
}
