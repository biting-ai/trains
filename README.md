##description
This little demo can supply services to customers with information about railway stations.
in particular, it includes tree main functions as below
firstly,supply application of distance calculate between several different stops
secondly,supply application  that how many route exists with query condition (max stops count or max distance)
i design this application based on dfs algorithm
thirdly,supply application to calculate the shortest route distance between two stops
i design this application based on Dijkstra algorithm to get the shortest distance between 2 stops

##directory Structure
Main.java                       //main class
NoSuchRouteException.java       //define no such route exception
InputDataException.ava          //define input data exception
RailwayStation.java             //railway Station model
RouteCondition.java             //route condition model
RailwayNet.java                 //main application class
RailwayNetTest.java             //main test class

input.txt                       //txt file include the input station and distance information
                                //we should make the file exist int the resources directory

##quick start
keep the directory src/resources have the file input.txt and
capital letters (A,B,C,D,E) refers to the different station ,
the data will be given in one line in the file , for example,
AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

Run Main.java's main method to start program

##environment
maven3  
java8
junit4.12
tools,intellij idea


