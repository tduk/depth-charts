package com.sportsbet.depthcharts.manage;


import com.sportsbet.depthcharts.exception.DepthChartException;
import com.sportsbet.depthcharts.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HashMapDepthChartMgrTest {
    private HashMapDepthChartMgr depthChartMgr;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void beforeEach() {
        depthChartMgr = new HashMapDepthChartMgr("WR", "KR");
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void addsPlayerToEmptyPosition() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        assertEquals("player1", depthChartMgr.getPlayersForPosition("WR").get(0).getName());
    }

    @Test
    public void addsPlayerToTheEndOfPosition() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(2, "player2"), "WR");
        assertEquals("player2", depthChartMgr.getPlayersForPosition("WR").get(1).getName());
    }

    @Test
    public void addsPlayerToSpecificDepthInPosition() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(3, "player3"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(2, "player2"), "WR", 1);
        assertEquals("player2", depthChartMgr.getPlayersForPosition("WR").get(1).getName());
    }

    @Test
    public void addPlayerToInvalidPositionThrowsException() {
        var exception = assertThrows(DepthChartException.class, () ->
                depthChartMgr.addPlayerToDepthChart(new Player(1, "player"), "INVALID"));
        assertEquals("Unknown position: INVALID", exception.getMessage());
    }

    @Test
    public void addPlayerToInvalidDepthThrowsException() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(2, "player2"), "WR");

        var exception = assertThrows(DepthChartException.class, () ->
                depthChartMgr.addPlayerToDepthChart(new Player(3, "player3"), "WR", 3));
        assertEquals("Invalid depth 3, has to be less or equal than 2", exception.getMessage());
    }

    @Test
    public void addThrowsExceptionWhenAddingDuplicateToPosition() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");

        var exception = assertThrows(DepthChartException.class, () ->
                depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR"));
        assertEquals("Player 'player1' already exists in position 'WR'", exception.getMessage());
    }

    @Test
    public void removesPlayerFromPosition() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        depthChartMgr.removePlayerFromDepthChart(new Player(1, "player1"), "WR");
        assertEquals(0, depthChartMgr.getPlayersForPosition("WR").size());
    }

    @Test
    public void removeNonExistingPlayerThrowsException() {
        var exception = assertThrows(DepthChartException.class, () -> depthChartMgr.removePlayerFromDepthChart(new Player(1, "player1"), "WR"));
        assertEquals("Player 'player1' does not exist", exception.getMessage());
    }

    @Test
    public void printsFullDepthChart() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(3, "player3"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "KR");
        depthChartMgr.printFullDepthChart();
        assertEquals("KR: [1],\nWR: [1, 3]", outputStreamCaptor.toString().trim());
    }

    @Test
    public void printsPlayersUnderPlayer() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(2, "player2"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(3, "player3"), "WR");
        depthChartMgr.printPlayersUnderPlayerInDepthChart(new Player(1, "player1"), "WR");
        assertEquals("[2, 3]", outputStreamCaptor.toString().trim());
    }

    @Test
    public void printsEmptyIfPlayerIsLast() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(2, "player2"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(3, "player3"), "WR");
        depthChartMgr.printPlayersUnderPlayerInDepthChart(new Player(3, "player3"), "WR");
        assertEquals("[]", outputStreamCaptor.toString().trim());
    }

    @Test
    public void printThrowsExceptionWhenPlayerNotFound() throws DepthChartException {
        depthChartMgr.addPlayerToDepthChart(new Player(1, "player1"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(2, "player2"), "WR");
        depthChartMgr.addPlayerToDepthChart(new Player(3, "player3"), "WR");
        var exception = assertThrows(DepthChartException.class, () -> depthChartMgr.printPlayersUnderPlayerInDepthChart(new Player(4, "player4"), "WR"));
        assertEquals("Player: 'player4' is not found", exception.getMessage());
    }

    @Test
    public void printThrowsExceptionWhenPlayerIsNull() throws DepthChartException {
        var exception = assertThrows(DepthChartException.class, () -> depthChartMgr.printPlayersUnderPlayerInDepthChart(null, "WR"));
        assertEquals("Player cannot be null", exception.getMessage());
    }

    @Test
    public void printThrowsExceptionWhenPositionInvalid() throws DepthChartException {
        var exception = assertThrows(DepthChartException.class, () -> depthChartMgr.printPlayersUnderPlayerInDepthChart(new Player(1, "player1"), "INVALID"));
        assertEquals("Unknown position: INVALID", exception.getMessage());
    }
}
