import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;

    public static void main(String[] args) {
        // Шаг 1: Считываем количество слов и сами слова
        int N = in.nextInt();
        in.nextLine();
        String[] words = new String[N];

        // Запись всех слов в массив
        for (int i = 0; i < N; i++) {
            words[i] = in.nextLine();
        }

        //  Шаг 2: Сортировка массива слов
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = i + 1; j < words.length; j++) {
                // Проверяем, нужно ли менять местами слова
                // Сравниваем длины слов. Если длина первого слова больше, то меняем их местами
                // Если длины равны, сравниваем их лексикографически
                if (words[i].length() > words[j].length() ||
                        (words[i].length() == words[j].length() && words[i].compareTo(words[j]) > 0)) {
                    // Временная переменная
                    String temp = words[i];
                    // Меняем местами слова в массиве
                    words[i] = words[j];
                    words[j] = temp;
                }
            }
        }


        // Шаг 3: Вывод самого длинного слов(а)
        // Проверка на существования самого длинного слова
        // Сравниваю самое короткое и самое длинное слово
        if(words[words.length-1].length() == words[0].length()){
            out.println("Все слова одной длины");
        } else {
            // Переменная для нахождения максимальной длины слова
            int mx = -1;
            // Проходим по всем словам в массиве
            for(String s: words){
                // Если текущая длина слова больше найденной максимальной длины
                if (mx < s.length()){
                    // Обновляем максимальную длину
                    mx = s.length();
                }
            }
            // Снова проходим по всем словам в массиве для поиска и вывода самых длинных слов
            out.println("Самое длинные/ое слова/о:");
            for(String s: words){
                // Если длина текущего слова равна максимальной длине
                if (mx == s.length()){
                    // Выводим самое длинное слово
                    out.println(s);
                }
            }

        }

        // Шаг 4: Группируем анаграммы
        groupAnagrams(words);

        // Шаг 5: Заменяем гласные буквы на следующий по алфавиту символ

        // Новый массив с измененными словами
        String[] newWords = new String[words.length];
        // Новое слово
        String newWord = "";
        // Массив гласный букв
        char[] glas = {'a', 'e', 'i', 'o', 'u'};
        // Счётчик для добавления слов в новый массив
        int ind = -1;

        // Перебираем все слова
        for (String s: words) {
            ind++;
            newWord = "";
            // Перебираем все символы в слове
            for (char c: s.toCharArray()){
                // Показатель гласный ли символ
                boolean isGlas = false;
                // Перебираем все гласные и сравниваем с символом
                for (char g: glas){
                    if(g == c){
                        isGlas = true;
                        break;
                    }
                }
                // Если текущий символ гласный
                if(isGlas){
                    // Заменяем гласную на следующий символ в алфавите
                    newWord += (char)(c + 1);
                } else {
                    // Иначе не меняем символ
                    newWord += c;
                }
            }
            // Добавляю новое слово в список новых слов
            newWords[ind] = newWord;
        }
        out.println("Cлова с заменой в каждом слове всех гласных букв на следующий по алфавиту символ:");
        // Вывод полученного массива
        for (String s: newWords){
            out.println(s);
        }
    }

    // Метод для группировки анаграмм
    private static void groupAnagrams(String[] words) {
        // Массив для отслеживания, сгруппировано ли слово
        boolean[] grouped = new boolean[words.length];

        out.println("Группы анаграмм:");

        // Проходим по всем словам в массиве
        for (int i = 0; i < words.length; i++) {
            // Проверяем, было ли текущее слово уже сгруппировано
            if (!grouped[i]) {
                // Начинаем новую группу анаграмм с текущего слова
                String anagramGroup = words[i];
                // Отмечаем текущее слово как сгруппированное
                grouped[i] = true;
                // Флаг для проверки наличия анаграмм
                boolean flag = false;
                // Сравниваем текущее слово с остальными словами в массиве
                for (int j = i + 1; j < words.length; j++) {
                    // Проверяем, не было ли это слово уже сгруппировано и являются ли они анаграммами
                    if (!grouped[j] && areAnagrams(words[i], words[j])) {
                        // Добавляем анаграмму в группу
                        anagramGroup += ", " + words[j];
                        // Отмечаем найденную анаграмму как сгруппированную
                        grouped[j] = true;
                        // Устанавливаем флаг, что найдены анаграммы
                        flag = true;
                    }
                }
                // Если найдены анаграммы, выводим группу
                if(flag){
                    out.println(anagramGroup);
                }
            }
        }
    }

    // Метод для проверки, являются ли два слова анаграммами
    private static boolean areAnagrams(String word1, String word2) {
        // Если длины строк разные, они не могут быть анаграммами
        if (word1.length() != word2.length()) {
            return false;
        }

        // Создание массива для подсчета символов
        int[] charCount = new int[26];

        for (int i = 0; i < word1.length(); i++) {
            // Увеличиваем счетчик для символа из первого слова
            charCount[word1.charAt(i) - 'a']++;
            // Уменьшаем счетчик для символа из второго слова
            charCount[word2.charAt(i) - 'a']--;
        }
        // Проверка, все ли счетчики равны нулю
        for (int count : charCount) {
            if (count != 0) {
                return false; // Если хотя бы один счетчик не равен нулю, строки не анаграммы
            }
        }
        return true; // Если все счетчики равны нулю, строки являются анаграммами
    }

}
