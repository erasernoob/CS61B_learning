package game2048;

import org.w3c.dom.html.HTMLTitleElement;

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
    public boolean moveHelper(int col) {
        int cnt = 0;
        // 完全将情况分离化
        for(int r = 0; r < board.size(); r++) {
            if(board.tile(col, r) != null) {
                // 记录这一列的存在的个数
                cnt++;
            }
        }

        if(cnt == 0) {
            return false; // 没有数字存在，返回false；
        }

        // 1. 当最上面一行不存在时
        if(board.tile(col, 3) == null) {
            // 开始将问题离散化
            if(cnt == 1) {
                for(int r = 0; r < board.size();r++) {
                    if(board.tile(col, r) != null) {
                        Tile t = board.tile(col, r);
                        board.move(col, 3, t);
                        return true;
                    }
                }
            }
            if(cnt == 2) {
                for(int r1 = 0; r1 < board.size(); r1++) {
                    if(board.tile(col, r1) != null) {
                        // default r2 > r1
                        int r2 = r1 + 1;
                        // 找到第二个数字
                        while(r2 < board.size()) {
                            if(board.tile(col, r2) != null) {
                                break;
                            }
                            r2++;
                        }
                        // 1. 相等的情况, 进行合并
                        if(board.tile(col, r2).value() == board.tile(col, r1).value()) {
                            Tile t1 = board.tile(col, r1);
                            Tile t2 = board.tile(col, r2);
                            board.move(col, 3, t2);
                            board.move(col, 3, t1);
                            // 加分
                            score += board.tile(col, 3).value();
                            return true;
                        } else { // 2. 不相等的情况
                            Tile t1 = board.tile(col, r1);
                            Tile t2 = board.tile(col, r2);
                            board.move(col, 3, t2);
                            board.move(col, 2, t1);
                            return true;
                        }

                    }
                }
            } else if (cnt == 3) {
                // 1 & 2
                if(board.tile(col, 1).value() == board.tile(col, 2).value()) {
                    Tile t0 = board.tile(col, 0);
                    Tile t1 = board.tile(col, 1);
                    Tile t2 = board.tile(col, 2);
                    board.move(col, 3, t1);
                    board.move(col, 3, t2);
                    board.move(col, 2, t0);
                    //  合成了多少就加多少分
                    score += board.tile(col, 3).value();
                    return true;
                } else if(board.tile(col, 1).value() == board.tile(col, 0).value()) { // 0 & 1
                    Tile t0 = board.tile(col, 0);
                    Tile t1 = board.tile(col, 1);
                    Tile t2 = board.tile(col, 2);
                    board.move(col, 2, t1);
                    board.move(col, 3, t2);
                    board.move(col, 2, t0);
                    //  合成了多少就加多少分
                    score += board.tile(col, 2).value();
                    return true;
                } else {
                    // 一个都不相等
                    Tile t0 = board.tile(col, 0);
                    Tile t1 = board.tile(col, 1);
                    Tile t2 = board.tile(col, 2);
                    // 每一个都向上提一个位置
                    board.move(col, 2, t1);
                    board.move(col, 3, t2);
                    board.move(col, 1, t0);
                    return true; // 只要是有tile移动了就返回changed == true
                }
            }

        }
        // 2. 当最上面一行存在
        else {
            if(cnt == 1) {
                // 无
                return false;
            } else if(cnt == 2) {
                for(int r = 0; r < board.size(); r++) {
                    if(board.tile(col, r) != null) {
                        if(board.tile(col, r).value() == board.tile(col, 3).value()) {
                            Tile t = board.tile(col,r);
                            board.move(col, r, t);
                            score += board.tile(col, 3).value();
                            return true;
                        } else if (r != 2){
                            Tile t = board.tile(col, r);
                            board.move(col, 2,t);
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            } else if(cnt == 3) {
                for(int r1 = 0; r1 < board.size(); r1++) {
                    if(board.tile(col, r1)  != null) {
                        for(int r2 = r1+1; r2 < board.size(); r2++) {
                            if(board.tile(col, r2) != null) {
                                if(board.tile(col, r2).value() == board.tile(col, 3).value()) {
                                    Tile t1 = board.tile(col, r1);
                                    Tile t2 = board.tile(col, r2);
                                    board.move(col, 3, t2);
                                    board.move(col, 2, t1);
                                    //  合成了多少就加多少分
                                    score += board.tile(col, 3).value();
                                    return true;
                                } else if(board.tile(col, r1).value() == board.tile(col, r2).value()) {
                                    // move method 是否支持将移动到自己本身的位置
                                    Tile t1 = board.tile(col, r1);
                                    Tile t2 = board.tile(col, r2);
                                    if(r2 == 2) {
                                        board.move(col, 2, t1);
                                    } else {
                                        board.move(col, 2, t2);
                                        board.move(col, 2, t1);
                                        //  合成了多少就加多少分
                                        score += board.tile(col, 2).value();
                                    }
                                    return true;
                                } else { // 无相等的
                                    Tile t1 = board.tile(col, r1);
                                    Tile t2 = board.tile(col, r2);
                                    // 还是内个问题 move method  !! r1 的重复没有处理
                                    if (r2 != 2) {
                                        board.move(col, 2, t2);
                                        // r1 只能是 0
                                        board.move(col, 1, t1);
                                        return true;
                                    } else {
                                        if(r1 == 1) {
                                            return false;
                                        } else {
                                            board.move(col, 1, t1);
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if(cnt == 4) {
                Tile t0 = board.tile(col, 0);
                Tile t1 = board.tile(col, 1);
                Tile t2 = board.tile(col, 2);
                if(board.tile(col, 3).value() == board.tile(col, 2).value()) {
                    board.move(col, 3, t2);
                    score += board.tile(col, 3).value();
                    if(board.tile(col, 1).value() == board.tile(col, 0).value()) {
                        board.move(col, 2, t1);
                        board.move(col, 2, t0);
                        score += board.tile(col, 2).value();
                    } else {
                        board.move(col, 2, t1);
                        board.move(col, 1, t0);
                    }
                    return true;
                } else if(board.tile(col, 1).value() == board.tile(col, 2).value()) {
                    board.move(col, 2, t1);
                    board.move(col, 1, t0);
                    score += board.tile(col, 2).value();
                    return true;
                } else if(board.tile(col, 1).value() == board.tile(col, 0).value()) {
                    board.move(col, 1, t0);
                    score += board.tile(col, 1).value();
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
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
