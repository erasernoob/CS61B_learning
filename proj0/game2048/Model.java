package game2048;

import org.w3c.dom.html.HTMLTitleElement;

import javax.swing.*;
import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    // 封装成我的move函数，同时加上更新分数
    public void myMove(int col, int row, int drow) {
        Tile t = board.tile(col, row);
        if(board.move(col, drow, t)) {
            score += t.value() * 2;
        }
    }

    // 只做这一件事，从某一个起点开始找到从上往下第一个存在tile的索引
    public int findIndex(int[] arr, int start) {
        for(int i = start; i < board.size(); i++) {
            if(arr[i] != 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean myEqual(int r1, int r2, int col) {
        if(board.tile(col, r1) != null && board.tile(col, r2) != null) {
            return board.tile(col, r1).value() == board.tile(col, r2).value();
        }
        return false;
    }

    // 最上面一排不存在数字
    public void noneForHead(int[] arr, int cnt, int col) {
        if(cnt == 1) {
            int r = findIndex(arr, 0);
            myMove(col, r, 3);
        } else if(cnt == 2) {
            int r1 = findIndex(arr, 0);
            int r2 = findIndex(arr, r1+1);
            if(myEqual(r1, r2, col)) {
                myMove(col, r1, 3);
                myMove(col ,r2, 3);
            } else {
                myMove(col, r2, 3);
                myMove(col, r1, 2);
            }
        } else if(cnt == 3) {
            boolean flag = false;
            // 分为三种情况
            // 1 & 2
            int r0, r1, r2;
            r0 = 0; r1 = 1; r2 = 2;
            if(myEqual(r1, r2, col)) {
                myMove(col, r2, 3);
                myMove(col, r1, 3);
                myMove(col, r0, 2);
                flag = true;
            }
            // 1 & 0
            if( !flag && myEqual(r1,r0, col)) {
                myMove(col, r2, 3);
                myMove(col, r1, 2);
                myMove(col, r0, 2);
            } else if(!flag && !myEqual(r1, r2, col)){
                // 三个都不相等
                myMove(col, r2, 3);
                myMove(col, r1, 2);
                myMove(col, r0, 1);
            }
        }
    }
    //最上面的一排存在数字
    public boolean forHead(int[] arr, int cnt, int col) {
        boolean flag = false;
        // cnt == 1 为默认情况不进入分支
        if(cnt == 2) {
            int r1 =  findIndex(arr, 0);
            if(myEqual(r1, 3, col)) {
                myMove(col, r1, 3);
                flag = true;
            } else if (!myEqual(r1, 3, col) && r1 != 2){
                myMove(col, r1, 2);
                flag = true;
            }  // 不需要改变的情况 1. 一个在3一个在2， 2.cnt == 1 的情况 flag 默认为false
        } else if(cnt == 3) {
            int r1 =findIndex(arr, 0);
            int r2 = findIndex(arr, r1+1);
            // 2 & 3
            if(myEqual(r2, 3, col)) {
                myMove(col, r2, 3);
                myMove(col ,r1, 2);
                flag = true;
            } else if(!flag && myEqual(r1, r2, col)) { // 1 & 2
                if(r2 == 2) {
                    myMove(col, r1, 2);
                } else {
                    myMove(col, r2, 2);
                    myMove(col ,r1, 2);
                }
                flag = true;

            } else { // none
                if(r2 == 2) {
                    if(r1 == 1) {
                        flag = false;
                    } else {
                        myMove(col, r1, 1);
                        flag = true;
                    }
                } else {
                    myMove(col, r1, 1);
                    myMove(col, r2, 2);
                    flag = true;
                }
            }
        }else if(cnt == 4) {
            // 3 & 2 | 2 & 1 | 1 & 0 | none   || 还有一个可能同时满足的  第一个和第三个
            if(myEqual(2, 3, col)) {
                // 同时满足的情况k
                if(myEqual(1, 0, col)) {
                    myMove(col, 2,3);
                    myMove(col, 1,2);
                    myMove(col, 0, 2);
                    flag = true;
                } else {
                    myMove(col, 2, 3);
                    myMove(col, 1,2);
                    myMove(col, 0,1);
                    flag = true;
                }
            } else { // situation 1 is not right
                // situation 2
                if(myEqual(2,1,  col)) {
                    myMove(col, 1, 2);
                    myMove(col, 0,1);
                    flag =true;
                }
                if(myEqual(0, 1, col) && !flag) {
                   myMove(col, 0,1);
                   flag = true;
                }
            }

        }
        return flag;
    }
    public boolean arrMove(int[] arr, int cnt, int col) {
        // 分成最上面一行是否存在值两种大情况
        // 默认为true
        boolean flag = true;
        if(arr[3] == 0) {
            noneForHead(arr, cnt, col);
        } else {
            flag = forHead(arr, cnt, col);
        }
        return flag;
    }

    public boolean moveHelper(int col) {
        int cnt = 0;
        int[] arr = new int[4];
        // 完全将情况分离化
        for (int r = 0; r < board.size(); r++) {
            arr[r] = 0;
            if (board.tile(col, r) != null) {
                // 记录这一列的存在的个数
                arr[r] = board.tile(col, r).value();
                cnt++;
            }
        }
        if (cnt == 0) return false;

        boolean flag = false;
        flag = arrMove(arr, cnt, col);
        return flag;
    }
    // 这里的列和行位置互换了并且 列从最左边开始，行从最下面开始
    public boolean tilt(Side side) {
        // first sovle the -up side
        boolean changed;
        changed = false;
        int flag = 0;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        // for each col divided to do
        board.setViewingPerspective(side);
        for(int c = 0; c < board.size(); c++) {
            if(moveHelper(c)) {
                flag++;
            }
        }
        board.setViewingPerspective(Side.NORTH);
        if(flag != 0) {
            changed = true;
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for(int i = 0; i < b.size(); i++) {
            for(int j = 0; j < b.size(); j++) {
                if(b.tile(i, j) == null) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for(int i = 0; i < b.size(); i++) {
            for(int j = 0; j < b.size(); j++) {
                if(b.tile(i, j) != null && b.tile(i, j).value() == MAX_PIECE) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b)) return true;
        else {
            for (int i = 0; i < b.size(); i++) {
                for (int j = 0; j < b.size() - 1; j++) {
                    if (b.tile(i, j).value() == b.tile(i, j + 1).value()) return true;
                }
            }
            for (int i = 0; i < b.size(); i++) {
                for (int j = 0; j < b.size() - 1; j++) {
                    if (b.tile(j, i).value() == b.tile(j + 1, i).value()) return true;
                }
            }
            return false;
        }
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
