package train;

import train.exception.InputDataException;
import train.exception.NoSuchRouteException;
import train.pojo.RailwayStation;
import train.pojo.RouteCondition;
import train.service.RailwayNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cj
 * @date 2019-03-10
 */

public class Main {
    /**
     * input file path
     */
    private static final String FILE_NAME = "input.txt";

    /**
     * define final NO_SUCH_ROUTE str
     */
    private static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";

    /**
     * define 1-5 output route
     */
    private static final List<char[]> STATION_LIST = new ArrayList<>(Arrays.asList(new char[]{'A', 'B', 'C'},
            new char[]{'A', 'D'},
            new char[]{'A', 'D', 'C'},
            new char[]{'A', 'E', 'B', 'C', 'D'},
            new char[]{'A', 'E', 'D'}));

    public static void main(String[] args) {
        RailwayNet railwayNet;
        try {
            railwayNet = buildRailway();
        } catch (NumberFormatException | StringIndexOutOfBoundsException | InputDataException e) {
            e.printStackTrace();
            return;
        }
        //output 1-5
        for (int i = 1; i <= 5; i++) {
            try {
                System.out.println("Output #" + i + ": " + railwayNet.getDistance(STATION_LIST.get(i - 1)));
            } catch (NoSuchRouteException e) {
                System.out.println("Output #" + i + ": " + NO_SUCH_ROUTE);
            }
        }

        //output 6
        RailwayStation railwayC = new RailwayStation('C');
        RouteCondition routeCondition2 = new RouteCondition(2, railwayC, railwayC);
        RouteCondition routeCondition3 = new RouteCondition(3, railwayC, railwayC);
        int routeWithCC2 = railwayNet.stationsLimitRouteCount(routeCondition2);
        int routeWithCC3 = railwayNet.stationsLimitRouteCount(routeCondition3);
        int routeWithCCMax3 = routeWithCC2 + routeWithCC3;
        System.out.println("Output #6: " + routeWithCCMax3);

        //output 7
        RailwayStation railwayA = new RailwayStation('A');
        RouteCondition routeCondition4 = new RouteCondition(4, railwayA, railwayC);
        int routeWithAC4 = railwayNet.stationsLimitRouteCount(routeCondition4);
        System.out.println("Output #7: " + routeWithAC4);

        //output 8-9
        int shortestDistanceAC = railwayNet.getShortestDistance(new RailwayStation('A'),
                new RailwayStation('C'));
        int shortestDistanceBB = railwayNet.getShortestDistance(new RailwayStation('B'),
                new RailwayStation('B'));
        System.out.println("Output #8: " + shortestDistanceAC);
        System.out.println("Output #9: " + shortestDistanceBB);

        //output 10
        RouteCondition routeCondition30 = new RouteCondition(30, railwayC, railwayC);
        int routeWithCCLessSureDistance = railwayNet.distanceLimitRouteCount(routeCondition30);
        System.out.println("Output #10: " + routeWithCCLessSureDistance);
    }

    /**
     * static method init
     * call read() to read input data
     * then new RailwayNet()
     *
     * @return RailwayNet object
     */
    private static RailwayNet buildRailway() throws NumberFormatException, StringIndexOutOfBoundsException, InputDataException {
        //init input string from file
        String inputStr = read();
        String[] strArray = inputStr.split(",");
        int stationNo = strArray.length;
        RailwayNet railwayNet = new RailwayNet(stationNo);
        try {
            for (String str : strArray) {
                char s = str.trim().charAt(0);
                char e = str.trim().charAt(1);
                //limit input station just A,B,C,D,E
                boolean flag = s < 65 || s > 69 || e < 65 || e > 69;
                if (flag) {
                    throw new InputDataException("input station error");
                }
                int weight = Integer.valueOf(String.valueOf(str.trim().charAt(2)));
                railwayNet.addStation(new RailwayStation(s),
                        new RailwayStation(weight, e));
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException | InputDataException e) {
            throw e;
        }
        return railwayNet;
    }

    /**
     * read string from txt file
     *
     * @return input data str
     */
    private static String read() {
        InputStream in;
        InputStreamReader isr;
        BufferedReader reader;
        StringBuilder result = new StringBuilder();
        try {
            in = Main.class.getClassLoader().getResourceAsStream(FILE_NAME);
            isr = new InputStreamReader(in);
            reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
