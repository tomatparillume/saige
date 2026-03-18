/*
 * Copyright(c) 2024 Parillume, All rights reserved worldwide
 */
package com.parillume.print.bargraph;

import lombok.Data;

/**
 * Creates individual bar graph images using SurveyMonkey formatting and colors.
 * These bar graphs are intended to be inserted in survey PowerPoints.
 * 
 * @author tmargolis
 * @author tom@parillume.com
 */
@Data
public class BarGraphData {
    
    private String barFileName; 
    private int[] barWidths; // multiple widths generates multiple bars
    private int[] barPercentages = null;
    
    public BarGraphData(String barFileName, int[] barWidths, int[] barPercentages) {
        setBarFileName(barFileName);
        setBarWidths(barWidths);
        setBarPercentages(barPercentages);
    }
    
    public static int getBarHeight(int barWidth) {
        // Standard bar widths are 576 and 960
        return barWidth < 600 ? 
                60: // For bar width 576 
                67;  // For bar width 960
    }
}
