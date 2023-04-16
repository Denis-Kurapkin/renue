import java.io.*;
import java.util.*;

public class Main {
    private static final String pathToCsv = "src/main/resources/airports.csv";
    static String s = "";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        HashMap<Integer, String> map;
        HashSet<Integer> interRes;

        String s = "";
        int col = 0;

        try {
            col = Integer.parseInt(args[0]) - 1;
        } catch (NullPointerException e) {
            System.out.println("Ошибка");
        }

        map = ReadCSV(col, pathToCsv);
        while (true) {

            s = reader.readLine();

            if (s.equals("!quit")) {
                break;
            }

            long m = System.currentTimeMillis();

            interRes = FindData(map, s);
            FindInCsv(interRes, col);
            int time = (int) (System.currentTimeMillis() - m);

            System.out.println("Кол-во найденых строк: " + interRes.size() + " Время, затраченное на поиск: " + time + " ms");
        }
        reader.close();
    }

    //читает весь файл и кладёт в HashMap ID аэропорта и его название
    public static HashMap<Integer, String> ReadCSV(int col, String pathToCsv) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(pathToCsv));
        HashMap<Integer, String> map = new HashMap<>();

        String row;
        String[] data;

        while ((row = reader.readLine()) != null) {
            data = row.split(",");
            int index = Integer.parseInt(data[col - 1]);

            map.put(index, data[col]);
        }
        reader.close();
        return map;
    }

    //выводит ID по переданному названию аэропорта
    public static HashSet<Integer> FindData(HashMap<Integer, String> test, String airportName) {
        HashSet<Integer> result = new HashSet<>();

        for (Map.Entry<Integer, String> entry : test.entrySet()) {

            if (entry.getValue().startsWith(airportName, 1)) { //offset необходим тк в файле название начинается с двойных кавычек
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public static void FindInCsv(HashSet<Integer> ids, int col) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathToCsv));
        HashSet<String> result = new HashSet<>();
        String row;
        String[] data;

        while ((row = reader.readLine()) != null) {
            data = row.split(",");
            int index = Integer.parseInt(data[col - 1]);
            if (ids.contains(index)) {
                System.out.println(data[col] + Arrays.toString(data));
            }
        }
    }
}