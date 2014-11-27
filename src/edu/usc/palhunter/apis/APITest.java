/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usc.palhunter.apis;

import edu.usc.infolab.roadnetwork.GeoPoint;
import edu.usc.infolab.roadnetwork.IGeoPoint;
import edu.usc.palhunter.business.TrajectoryManager;
import java.sql.SQLException;

/**
 *
 * @author Luan
 */
public class APITest {
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        TrajectoryManager trajectoryManager = new TrajectoryManager();
        GeoPoint point = new GeoPoint(11.65,113.54) ;
        trajectoryManager.updateLocation(6, point);
    }
    

}
