package train.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import train.exception.NoSuchRouteException;
import train.pojo.RailwayStation;
import train.pojo.RouteCondition;

/**
 * junit test class
 * use junit4 to test in case
 */
public class RailwayNetTest {

    static RailwayNet railwayNet;

    @Before
    public void setUp() throws Exception {
        railwayNet = new RailwayNet(5);
        railwayNet.addStation(new RailwayStation('A'), new RailwayStation(5, 'B'));
        railwayNet.addStation(new RailwayStation('B'), new RailwayStation(4, 'C'));
        railwayNet.addStation(new RailwayStation('C'), new RailwayStation(8, 'D'));
        railwayNet.addStation(new RailwayStation('D'), new RailwayStation(8, 'C'));
        railwayNet.addStation(new RailwayStation('D'), new RailwayStation(6, 'E'));
        railwayNet.addStation(new RailwayStation('A'), new RailwayStation(5, 'D'));
        railwayNet.addStation(new RailwayStation('C'), new RailwayStation(2, 'E'));
        railwayNet.addStation(new RailwayStation('E'), new RailwayStation(3, 'B'));
        railwayNet.addStation(new RailwayStation('A'), new RailwayStation(7, 'E'));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDistance() throws NoSuchRouteException {
        int ab = railwayNet.getDistance(new char[]{'A', 'B'});
        int abcd = railwayNet.getDistance(new char[]{'A', 'B', 'C', 'D'});
        int abcdc = railwayNet.getDistance(new char[]{'A', 'B', 'C', 'D', 'C'});

        //A->B
        Assert.assertEquals(5, ab);

        //A->B->C->D
        Assert.assertEquals(17, abcd);

        //A->B->C->D->C
        Assert.assertEquals(25, abcdc);

        //A->D->B no this route then throw NoSuchRouteException
        thrown.expect(NoSuchRouteException.class);
        railwayNet.getDistance(new char[]{'A', 'D', 'B'});
    }

    @Test
    public void stationsLimitRouteCount() {
        RailwayStation A = new RailwayStation('A');
        RailwayStation B = new RailwayStation('B');
        RailwayStation C = new RailwayStation('C');
        RailwayStation D = new RailwayStation('D');

        RouteCondition routeConditionCC2 = new RouteCondition(2, C, C);
        RouteCondition routeConditionCC3 = new RouteCondition(3, C, C);
        RouteCondition routeConditionAB1 = new RouteCondition(1, A, B);
        RouteCondition routeConditionAB2 = new RouteCondition(2, A, B);
        RouteCondition routeConditionAB3 = new RouteCondition(3, A, B);
        RouteCondition routeConditionAC1 = new RouteCondition(1, A, C);
        RouteCondition routeConditionAC3 = new RouteCondition(3, A, C);
        RouteCondition routeConditionAD5 = new RouteCondition(5, A, D);


        int cc2 = railwayNet.stationsLimitRouteCount(routeConditionCC2);
        int cc3 = railwayNet.stationsLimitRouteCount(routeConditionCC3);
        int ab1 = railwayNet.stationsLimitRouteCount(routeConditionAB1);
        int ab2 = railwayNet.stationsLimitRouteCount(routeConditionAB2);
        int ab3 = railwayNet.stationsLimitRouteCount(routeConditionAB3);
        int ac1 = railwayNet.stationsLimitRouteCount(routeConditionAC1);
        int ac3 = railwayNet.stationsLimitRouteCount(routeConditionAC3);
        int ad5 = railwayNet.stationsLimitRouteCount(routeConditionAD5);

        //route:C->D->C
        Assert.assertEquals(1, cc2);

        //route:C->E->B->C
        Assert.assertEquals(1, cc3);

        //route:A->B
        Assert.assertEquals(1, ab1);

        //route:A->E->B
        Assert.assertEquals(1, ab2);

        //route:A->D->E->B
        Assert.assertEquals(1, ab3);

        //no non-stop A->C
        Assert.assertEquals(0, ac1);

        //route:A->E->B->C
        Assert.assertEquals(1, ac3);

        //route:A->B->C->D->C->D
        //route:A->D->C->D->C->D
        //route:A->D->E->B->C->D
        Assert.assertEquals(3, ad5);
    }

    @Test
    public void distanceLimitRouteCount() {
        RailwayStation A = new RailwayStation('A');
        RailwayStation B = new RailwayStation('B');
        RailwayStation C = new RailwayStation('C');
        RailwayStation D = new RailwayStation('D');

        //test C->C distance < 30
        RouteCondition routeConditionCC30 = new RouteCondition(30, C, C);

        //test C->C distance < 1
        RouteCondition routeConditionCC1 = new RouteCondition(1, C, C);

        //test B->A distance < 1
        RouteCondition routeConditionBA1 = new RouteCondition(1, B, A);

        //test C->D distance < 9
        RouteCondition routeConditionCD9 = new RouteCondition(9, C, D);

        //test C->D distance < 25
        RouteCondition routeConditionCD25 = new RouteCondition(25, C, D);

        int cc30 = railwayNet.distanceLimitRouteCount(routeConditionCC30);
        int cc1 = railwayNet.distanceLimitRouteCount(routeConditionCC1);
        int ba1 = railwayNet.distanceLimitRouteCount(routeConditionBA1);
        int cd9 = railwayNet.distanceLimitRouteCount(routeConditionCD9);
        int cd25 = railwayNet.distanceLimitRouteCount(routeConditionCD25);
        Assert.assertEquals(7, cc30);
        Assert.assertEquals(0, cc1);
        Assert.assertEquals(0, ba1);
        Assert.assertEquals(1, cd9);
        Assert.assertEquals(3, cd25);
    }

    @Test
    public void getShortestDistance() {

        RailwayStation A = new RailwayStation('A');
        RailwayStation B = new RailwayStation('B');
        RailwayStation C = new RailwayStation('C');
        RailwayStation D = new RailwayStation('D');
        RailwayStation E = new RailwayStation('E');

        //test station to itself
        int aa = railwayNet.getShortestDistance(A, A);
        int bb = railwayNet.getShortestDistance(B, B);
        int cc = railwayNet.getShortestDistance(C, C);
        int dd = railwayNet.getShortestDistance(D, D);
        int ee = railwayNet.getShortestDistance(E, E);

        //test A->C route:ABC
        int ac = railwayNet.getShortestDistance(A, C);

        //test A->D route:AD
        int ad = railwayNet.getShortestDistance(A, D);

        //test D->B route:DEB
        int db = railwayNet.getShortestDistance(D, B);

        Assert.assertEquals(-1, aa);
        Assert.assertEquals(9, bb);
        Assert.assertEquals(9, cc);
        Assert.assertEquals(16, dd);
        Assert.assertEquals(9, ee);

        Assert.assertEquals(9, ac);
        Assert.assertEquals(5, ad);
        Assert.assertEquals(9, db);
    }
}