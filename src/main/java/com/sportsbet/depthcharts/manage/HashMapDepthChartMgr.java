package com.sportsbet.depthcharts.manage;

import com.sportsbet.depthcharts.model.Player;
import com.sportsbet.depthcharts.exception.DepthChartException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HashMapDepthChartMgr implements DepthChartMgr {
    private HashMap<String, List<Player>> chart = new HashMap<>();
    private List<String> availablePositions = new ArrayList<>();

    public HashMapDepthChartMgr(String... availablePositions) {
        this.availablePositions.addAll(List.of(availablePositions));
    }

    @Override
    public void addPlayerToDepthChart(Player player, String position, Integer positionDepth) throws DepthChartException {
        checkPosition(position);

        var players = chart.getOrDefault(position, new ArrayList<>());

        positionDepth = Optional.ofNullable(positionDepth).orElse(players.size());

        if (positionDepth > players.size()) {
            throw new DepthChartException(String.format("Invalid depth %s, has to be less or equal than %s", positionDepth, players.size()));
        }

        if(players.contains(player)){
            throw new DepthChartException(String.format("Player '%s' already exists in position '%s'", player.getName(), position));
        }

        players.add(positionDepth, player);

        chart.putIfAbsent(position, players);
    }

    @Override
    public void addPlayerToDepthChart(Player player, String position) throws DepthChartException {
        addPlayerToDepthChart(player, position, null);
    }

    @Override
    public void removePlayerFromDepthChart(Player player, String position) throws DepthChartException {
        if (player != null) {
            if (!chart.getOrDefault(position, new ArrayList<>()).remove(player)) {
                throw new DepthChartException(String.format("Player '%s' does not exist", player.getName()));
            }
        }
    }

    @Override
    public void printFullDepthChart() {
        var iterator = chart.keySet().iterator();
        for (; ; ) {
            var key = iterator.next();
            System.out.print(String.format("%s: ", key));
            printPlayers(chart.get(key));
            if (!iterator.hasNext()) {
                System.out.println();
                return;
            }
            System.out.println(",");
        }
    }

    private void printPlayers(List<Player> players) {
        System.out.print(players.stream().map(Player::getId).collect(Collectors.toList()));
    }

    @Override
    public void printPlayersUnderPlayerInDepthChart(Player player, String position) throws DepthChartException {
        checkPosition(position);
        checkNotNull(player, "Player cannot be null");

        var players = chart.getOrDefault(position, List.of());
        var depth = players.indexOf(player);
        if (depth == -1) {
            throw new DepthChartException(String.format("Player: '%s' is not found", player.getName()));
        }

        printPlayers(players.subList(depth + 1, players.size()));
    }

    private void checkNotNull(Object player, String msg) throws DepthChartException {
        if (player == null) {
            throw new DepthChartException(msg);
        }
    }

    @Override
    public List<Player> getPlayersForPosition(String position) {
        return chart.getOrDefault(position, List.of());
    }

    private void checkPosition(String position) throws DepthChartException {
        if (!availablePositions.contains(position)) {
            throw new DepthChartException(String.format("Unknown position: %s", position));
        }
    }
}
