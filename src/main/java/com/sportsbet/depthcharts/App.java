package com.sportsbet.depthcharts;

import com.sportsbet.depthcharts.exception.DepthChartException;
import com.sportsbet.depthcharts.manage.DepthChartMgr;
import com.sportsbet.depthcharts.manage.HashMapDepthChartMgr;
import com.sportsbet.depthcharts.model.Player;

public class App {
    private static DepthChartMgr nflDepthCharts = new HashMapDepthChartMgr("QB", "WR", "RB", "TE", "K", "P", "KR", "PR");
    private static DepthChartMgr mlbDepthCharts = new HashMapDepthChartMgr("SP", "RP", "C", "1B", "2B", "3B", "SS", "LF", "RF", "CF", "DH");

    public static void main(String[] args) throws DepthChartException {
        nflDepthCharts.addPlayerToDepthChart(new Player(1, "Player1"), "QB");
        nflDepthCharts.addPlayerToDepthChart(new Player(2, "Player2"), "QB", 0);
        nflDepthCharts.addPlayerToDepthChart(new Player(3, "Player3"), "QB");
        nflDepthCharts.addPlayerToDepthChart(new Player(1, "Player1"), "P");
        nflDepthCharts.printFullDepthChart();

        mlbDepthCharts.addPlayerToDepthChart(new Player(1, "Player1"), "SP");
        mlbDepthCharts.addPlayerToDepthChart(new Player(2, "Player2"), "RP", 0);
        mlbDepthCharts.addPlayerToDepthChart(new Player(3, "Player3"), "C");
        mlbDepthCharts.addPlayerToDepthChart(new Player(1, "Player1"), "C");
        mlbDepthCharts.printFullDepthChart();
    }
}
