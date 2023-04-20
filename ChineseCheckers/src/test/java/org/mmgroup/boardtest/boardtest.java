package org.mmgroup.boardtest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.mmgroup.boardfactory.BoardFactory;
import org.mmgroup.boardfactory.TwoPlayersChineseCheckersFactory;
import org.mmgroup.gamelogic.Board;
import org.mmgroup.gamelogic.GameRules;
import org.mmgroup.gamelogic.JumpMove;
import org.mmgroup.gamelogic.Move;
import org.mmgroup.gamelogic.NormalMove;
import org.mmgroup.gamelogic.OutOfBaseAntiMove;
import org.mmgroup.gamelogic.Vector2;

public class boardtest {
  @Test
  public void BasicTest() {
    Board board = new Board(10,20);
    board.insertPawn(9, 15, 1);
    board.insertPawn(8, 14, 1);
    
    assertTrue(!board.movePawn(8, 14, 9, 15));
    assertTrue(board.getPawn(8,14)!=null);
    
    assertTrue(board.getPawn(9,15)!=null);
    board.movePawn(9, 15, 1, 2);
    
    assertTrue(board.getPawn(9,15)==null);
    assertTrue(board.getPawn(1,2)!=null);
    
    board.removePawn(1, 1);
    assertTrue(board.getPawn(1,1)==null);
  }
  @Test
  public void PossibleNormalMovesTest() {
    Board board = new Board(5,5);
    board.insertPawn(0, 0, 1);
    Move move = new NormalMove();
    ArrayList<Vector2> test = move.generateMoves(board, 0, 0, null);
    ArrayList<Vector2> test2 = move.generateMoves(board, 4, 4, null);
    assertTrue(test.size()==2);
    assertTrue(test2.size()==3);
  }
  @Test
  public void PossibleJumpMovesTest() {
    Board board = new Board(10,20);
    board.insertPawn(9, 15, 1);
    board.insertPawn(5, 15, 1);
    Move move = new JumpMove();
    ArrayList<Vector2> test = move.generateMoves(board, 9, 15, null);
    assertTrue(test.size() == 1);
  }
  @Test
  public void GameRulesTest() {
    Board board = new Board(10,20);
    board.insertPawn(9, 15, 1);
    board.insertPawn(5, 15, 1);
    
    GameRules rules = new GameRules();
    rules.addMoveRuleOption(new NormalMove());
    rules.addMoveRuleOption(new JumpMove());
    rules.addMoveRuleOption(new OutOfBaseAntiMove());

    ArrayList<Vector2> test = rules.getAvailableMovesForPos(board, 9, 15, false);
    assertTrue(test.size()==4);
  }
  @Test
  public void FactoryTest() {
    BoardFactory bf = new TwoPlayersChineseCheckersFactory();
    Board b = bf.buildBoard();
    assertTrue(b.winCondition!=null);
  }
}
