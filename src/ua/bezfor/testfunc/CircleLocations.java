package ua.bezfor.testfunc;

import java.awt.*;
import java.util.ArrayList;

public class CircleLocations{

        public static ArrayList<Point> getLocations(double centerX, double centerY, double radius, double amountOfTurns){
            ArrayList<Point> locationsList = new ArrayList<>();
            double angleDifferences = 360.0 / amountOfTurns;
            for(int i = 0; i < amountOfTurns; i++){
                Point point = new Point();
                double degreeTurn = Math.toRadians(i * angleDifferences);
                point.setLocation(centerX + (radius * Math.cos(degreeTurn)), centerY + (radius * Math.sin(degreeTurn)));
                if(!containsAlready(locationsList, point))
                    locationsList.add(point);
            }
            return locationsList;
        }

        public static boolean containsAlready(ArrayList<Point> list, Point point){
            for(Point p : list)
                if(p.getX() == point.getX() && p.getY() == point.getY())
                    return true;
            return false;
        }
}
