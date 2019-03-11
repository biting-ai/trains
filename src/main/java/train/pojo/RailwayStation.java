package train.pojo;

import java.util.Objects;

/**
 * RailStation model
 *
 * @author cj
 * @date 2019-03-10
 */
public class RailwayStation {

    /**
     * station weight
     * should implicit the start station
     */
    private int stationDistance;

    /**
     * station name
     */
    private char stationName;

    public RailwayStation(int stationDistance, char stationName) {
        this.stationDistance = stationDistance;
        this.stationName = stationName;
    }

    public RailwayStation(char stationName) {
        this.stationDistance = 0;
        this.stationName = stationName;
    }

    public int getStationDistance() {
        return stationDistance;
    }

    public void setStationDistance(int stationDistance) {
        this.stationDistance = stationDistance;
    }

    public char getStationName() {
        return stationName;
    }

    public void setStationName(char stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return String.valueOf(stationName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RailwayStation that = (RailwayStation) o;
        return stationName == that.stationName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationName);
    }
}
