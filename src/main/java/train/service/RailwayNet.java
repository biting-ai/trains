package train.service;

import train.exception.NoSuchRouteException;
import train.pojo.RailwayStation;
import train.pojo.RouteCondition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * the railroad service class
 * 1.supply application distance query between several different stops
 * 2.supply application how many route exists with query condition
 * (max stops count or max distance)
 * 3.supply application to calculate the shortest route distance between two stops
 * <p>
 * created by cj on 2019/3/9
 */
public class RailwayNet {

    /**
     * all railway stations No.
     */
    private int stationNo;

    /**
     * (Dijkstra) a queue has contains visited station index
     */
    private Queue visitedList;

    /**
     * the railway net adjacency matrix
     */
    private List<RailwayStation>[] railwayNet;

    /**
     * constructor to
     * init railway net
     *
     * @param stationNo station No.
     */
    public RailwayNet(int stationNo) {
        this.stationNo = stationNo;
        visitedList = new LinkedList();
        initRailwayNet();
    }


    /**
     * adjacency table init
     */
    @SuppressWarnings("unchecked")
    private void initRailwayNet() {
        railwayNet = new ArrayList[stationNo];
        for (int i = 0; i < stationNo; i++) {
            railwayNet[i] = new ArrayList<>();
        }
    }

    /**
     * uses railway stations
     * to build railway net (build the adjacency table init)
     *
     * @param start start station
     * @param end   end station
     */
    public void addStation(RailwayStation start, RailwayStation end) {
        railwayNet[getPosition(start.getStationName())].add(end);
    }


    /**
     * get distance by given character array type
     * for example, ['A','B','C'] means
     * the sum distance from A to B and distance B to C
     *
     * @param stationNames stations array
     * @return the result distance
     * @throws NoSuchRouteException if not exist route throw exception
     */
    public int getDistance(char[] stationNames) throws NoSuchRouteException {
        int distance = 0;
        int count = stationNames.length;
        for (int i = 0; i < count - 1; i++) {
            boolean existRoute = false;
            List<RailwayStation> railwayStationList = railwayNet[getPosition(stationNames[i])];
            for (RailwayStation railwayStation : railwayStationList) {
                if (railwayStation.getStationName() == stationNames[i + 1]) {
                    distance += railwayStation.getStationDistance();
                    existRoute = true;
                    break;
                }
            }
            if (!existRoute) {
                throw new NoSuchRouteException();
            }
        }
        return distance;
    }


    /**
     * how many route exists with
     * query condition of given stations no. limit
     *
     * @param routeCondition route query condition parameter
     * @return route count
     */
    public int stationsLimitRouteCount(RouteCondition routeCondition) {
        // save all the possible route list
        List list = new ArrayList<>();
        List<RailwayStation> routeList = new ArrayList<>();
        routeList.add(routeCondition.getStart());
        dfsStationsLimitRoute(routeList, routeCondition, list);
        return list.size();
    }


    /**
     * dfs Recursive function
     *
     * @param routeList      recursive route list
     * @param routeCondition route condition
     * @param list           the return route list
     */
    private void dfsStationsLimitRoute(List<RailwayStation> routeList, RouteCondition routeCondition, List list) {
        RailwayStation c = routeCondition.getStart();
        RailwayStation e = routeCondition.getEnd();
        int no = routeCondition.getCondition();
        if (c.getStationName() == e.getStationName()) {
            if (routeList.size() == no + 1) {
                List<RailwayStation> temp = new ArrayList<>();
                for (RailwayStation railwayStation : routeList) {
                    temp.add(railwayStation);
                }
                list.add(temp);
                return;
            }
        }
        List<RailwayStation> railwayStationList = railwayNet[getPosition(c.getStationName())];
        for (RailwayStation railwayStation : railwayStationList) {
            if (routeList.size() < no + 1) {
                routeList.add(railwayStation);
                routeCondition.setStart(railwayStation);
                dfsStationsLimitRoute(routeList, routeCondition, list);
                routeList.remove(routeList.size() - 1);
            }
        }
    }


    /**
     * how many route exists with
     * query condition of given maximum distance limit
     *
     * @param routeCondition route condition pramater
     * @return route count
     */
    public int distanceLimitRouteCount(RouteCondition routeCondition) {
        RailwayStation s = routeCondition.getStart();
        List list = new ArrayList<>();
        List<RailwayStation> routeList = new ArrayList<>();
        routeList.add(s);
        try {
            dfsDistanceLimitRoute(routeList, routeCondition, list);
        } catch (NoSuchRouteException e) {
            e.printStackTrace();
        }
        return list.size();
    }


    /**
     * dfs Recursive function
     *
     * @param routeList      recursive route list
     * @param routeCondition route condition
     * @param list           the return route list
     * @throws NoSuchRouteException
     */
    private void dfsDistanceLimitRoute(List<RailwayStation> routeList, RouteCondition routeCondition, List list) throws NoSuchRouteException {

        int sureDistance = routeCondition.getCondition();
        RailwayStation c = routeCondition.getStart();
        RailwayStation e = routeCondition.getEnd();


        char[] charArray = new char[routeList.size()];
        for (int i = 0; i < routeList.size(); i++) {
            charArray[i] = routeList.get(i).getStationName();
        }
        int distance = getDistance(charArray);
        if (c.getStationName() == e.getStationName() && routeList.size() != 1) {
            if (distance < sureDistance) {
                List<RailwayStation> temp = new ArrayList<>();
                for (RailwayStation railwayStation : routeList) {
                    temp.add(railwayStation);
                }
                list.add(temp);
//                return;
            }
        }
        List<RailwayStation> railwayStationList = railwayNet[getPosition(c.getStationName())];
        for (RailwayStation railwayStation : railwayStationList) {
            if (distance < sureDistance) {
                routeList.add(railwayStation);
                routeCondition.setStart(railwayStation);
                dfsDistanceLimitRoute(routeList, routeCondition, list);
                routeList.remove(routeList.size() - 1);
            }
        }
    }

    /**
     * Dijkstra to get the shortest distance
     *
     * @param start start station
     * @param end   end station
     * @return short distance
     */
    public int getShortestDistance(RailwayStation start, RailwayStation end) {
        visitedList.clear();
        int i = getPosition(start.getStationName());
        List<RailwayStation> railwayStations = railwayNet[i];
        int[] distance = new int[stationNo];
        for (int i1 = 0; i1 < distance.length; i1++) {
            distance[i1] = -1;
        }
        for (RailwayStation railwayStation : railwayStations) {
            int position = getPosition(railwayStation.getStationName());
            distance[position] = railwayStation.getStationDistance();
        }

        while (visitedList.size() < stationNo) {
            int position = getMinStation(visitedList, distance);
            visitedList.add(position);
            if (position != -1) {
                for (RailwayStation railwayStation : railwayNet[position]) {
                    int k = getPosition(railwayStation.getStationName());
                    if (distance[k] == -1 || railwayStation.getStationDistance() + distance[position] < distance[k]) {
                        distance[k] = railwayStation.getStationDistance() + distance[position];
                    }
                }
            }
        }

        return distance[getPosition(end.getStationName())];
    }

    /**
     * get min index by a visited queue
     * and init distance array
     *
     * @param visitedList a visited queue
     * @param distance    Dijkstra init distance
     * @return the min index
     */
    private int getMinStation(Queue visitedList, int[] distance) {
        int minPostion = -1;
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] != -1 && !visitedList.contains(i)) {
                if (distance[i] < minDistance) {
                    minDistance = distance[i];
                    minPostion = i;
                }
            }
        }
        return minPostion;
    }


    /**
     * ASCII railway character to int index
     *
     * @param name station name
     * @return adjacency index
     */
    private int getPosition(char name) {
        return name - 65;
    }
}
