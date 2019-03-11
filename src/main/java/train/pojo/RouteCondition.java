package train.pojo;

/**
 * model uses to get
 * the possible route by condition
 *
 * @author cj
 * @date 2019-03-10
 */
public class RouteCondition {

    /**
     * condition value refer to
     * the given stops count or
     * the maximum distance which
     * to be the query condition
     */
    private int condition;

    /**
     * start station
     */
    private RailwayStation start;

    /**
     * end station
     */
    private RailwayStation end;

    public RouteCondition(int condition, RailwayStation start, RailwayStation end) {
        this.condition = condition;
        this.start = start;
        this.end = end;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public RailwayStation getStart() {
        return start;
    }

    public void setStart(RailwayStation start) {
        this.start = start;
    }

    public RailwayStation getEnd() {
        return end;
    }

    public void setEnd(RailwayStation end) {
        this.end = end;
    }
}
