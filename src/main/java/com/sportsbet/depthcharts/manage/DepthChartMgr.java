package com.sportsbet.depthcharts.manage;

import com.sportsbet.depthcharts.model.Player;
import com.sportsbet.depthcharts.exception.DepthChartException;

import java.util.List;

public interface DepthChartMgr {
    void addPlayerToDepthChart(Player player, String position, Integer positionDepth) throws DepthChartException;

    void addPlayerToDepthChart(Player player, String position) throws DepthChartException;

    void removePlayerFromDepthChart(Player player, String position) throws DepthChartException;

    void printFullDepthChart();

    void printPlayersUnderPlayerInDepthChart(Player player, String position) throws DepthChartException;

    List<Player> getPlayersForPosition(String position);
}
