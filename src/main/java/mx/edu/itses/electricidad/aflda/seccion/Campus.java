/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.electricidad.aflda.seccion;

import java.util.List;

/**
 *
 * @author ferlo
 */
public class Campus {
    
        public static double calculateCenterX(List<Residencia> residencias) {
        double weightedSumX = 0;
        int totalOfPopulation = 0;

        for (Residencia res : residencias) {
            weightedSumX += res.getCenterX() * res.getPopulation();
            totalOfPopulation += res.getPopulation();
        }

        return totalOfPopulation > 0 ? weightedSumX / totalOfPopulation : 0;
        
        
        
   
    }
   
        
        
        
        
        

    public static double calculateCenterY(List<Residencia> residences) {
        double weightedSumY = 0;
        int totalOfPopulation = 0;

        for (Residencia res : residences) {
            weightedSumY += res.getCenterY() * res.getPopulation();
            totalOfPopulation += res.getPopulation();
        }

        return totalOfPopulation > 0 ? weightedSumY / totalOfPopulation : 0;
    }
    
}
