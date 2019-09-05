package CTCI.OOP;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Implement an NxN jigsaw puzzle. Design the data structures and explain an algorithm
 * to solve the puzzle. You can assume that you have a fitsWith method which when passed
 * two puzzle edges, returns true if the two edges belong together.
 */
public class JigSawPuzzle {
    /**
     * This is a traditional jigsaw puzzle. The puzzle is grid like, with rows and columns.
     * Each piece is located in a single row and column and has four edges. Each edge comes
     * in one of three types, inner, outer, and flat. A corner piece will have two edges and
     * two other edges, which could be inner or outer.
     *
     * We will need classes to represent puzzle, piece, and edge. Additionally we will want
     * enums for the different shapes (inner, outer, flat) and the orientations(left, top, right, bottom).
     *
     * Puzzle will start off with a list of pieces, when we solve the puzzle, we will fill
     * in an NxN solution matrix of pieces.
     *
     * Piece will have a hash table that maps from an orientation to the appropriate edge.
     * Note that we might rotate the piece at some point, so that the hash table could change.
     * the orientation of the edges will be arbitrarily assigned first.
     *
     * Edge will have just its shape and a pointer back to its parent piece. It will not keep its orientation.
     * @param args
     */
    public static void main(String[] args) {
        for (int size = 1; size < 10; size++) {
            if (!testSize(size)) {
                System.out.println("ERROR: " + size);
            }
        }

    }

    static Edge createRandomEdge(String code) {
        Random random = new Random();
        Shape type = Shape.INNER;
        if (random.nextBoolean()) type = Shape.OUTER;
        return new Edge(type, code);
    }

    static Edge[] createEdges(Piece[][] puzzle, int col, int row) {
        String key = col + ":" + row + ":";

        /* Get left edge */
        Edge left = col == 0 ? new Edge(Shape.FLAT, key + "h|e") :
                                        puzzle[row][col - 1].getEdgeWithOrientation(Orientation.RIGHT).createMatchingEdge();

        /* Get top edge */
        Edge top = row == 0 ? new Edge(Shape.FLAT, key + "v|e") :
                                        puzzle[row - 1][col].getEdgeWithOrientation(Orientation.BOTTOM).createMatchingEdge();

        /* Get right edge */
        Edge right = col == puzzle[row].length - 1 ? new Edge(Shape.FLAT, key + "h|e") :
                                                    createRandomEdge(key + "h");

        /* Get bottom edge */
        Edge bottom = row == puzzle.length - 1 ? new Edge(Shape.FLAT, key + "v|e") :
                                                    createRandomEdge(key + "v");

        Edge[] edges = {left, top, right, bottom};
        return edges;
    }

    static LinkedList<Piece> initPuzzle(int size) {
        // create completed puzzle
        Piece[][] puzzle = new Piece[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Edge[] edges = createEdges(puzzle, col, row);
                puzzle[row][col] = new Piece(edges);
            }
        }

        // shuffle and rotate pieces
        LinkedList<Piece> pieces = new LinkedList<>();
        Random r = new Random();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int rotations = r.nextInt(4);
                Piece piece = puzzle[row][col];
                piece.rotateEdgesBy(rotations);
                int index = pieces.size() == 0 ? 0 : r.nextInt(pieces.size());
                pieces.add(index,piece);
            }
        }
        return pieces;
    }

    static String solnToString(Piece[][] soln) {
        StringBuilder sb = new StringBuilder();
        for (int h = 0; h < soln.length; h++) {
            for (int w = 0; w < soln[h].length; w++) {
                Piece p = soln[h][w];
                if (p == null) sb.append("null");
                else sb.append(p.toStr());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    /* Used for testing. Check if puzzle is solved. */
    public static boolean validate(Piece[][] solution) {
        if (solution == null) return false;
        for (int r = 0; r < solution.length; r++) {
            for (int c = 0; c < solution[r].length; c++) {
                Piece piece = solution[r][c];
                if (piece == null) return false;
                if (c > 0) { /* match left */
                    Piece left = solution[r][c-1];
                    if (!left.getEdgeWithOrientation(Orientation.RIGHT).fitsWith(piece.getEdgeWithOrientation(Orientation.LEFT))) {
                        return false;
                    }
                }
                if (c < solution[r].length - 1) { /* match right */
                    Piece right = solution[r][c+1];
                    if (!right.getEdgeWithOrientation(Orientation.LEFT).fitsWith(piece.getEdgeWithOrientation(Orientation.RIGHT))) {
                        return false;
                    }
                }
                if (r > 0) { /* match top */
                    Piece top = solution[r-1][c];
                    if (!top.getEdgeWithOrientation(Orientation.BOTTOM).fitsWith(piece.getEdgeWithOrientation(Orientation.TOP))) {
                        return false;
                    }
                }
                if (r < solution.length - 1) { /* match bottom */
                    Piece bottom = solution[r+1][c];
                    if (!bottom.getEdgeWithOrientation(Orientation.TOP).fitsWith(piece.getEdgeWithOrientation(Orientation.BOTTOM))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean testSize(int size) {
        LinkedList<Piece> pieces = initPuzzle(size);
        Puzzle puzzle = new Puzzle(size, pieces);
        puzzle.solve();
        Piece[][] solution = puzzle.getCurrentSoln();
        System.out.println(solnToString(solution));
        boolean result = validate(solution);
        System.out.println(result);
        return result;
    }


    enum Shape {
        INNER, OUTER, FLAT;

        Shape getOpposite() {
            switch (this) {
                case INNER: return OUTER;
                case OUTER: return INNER;
                default: return null;
            }
        }
    }

    enum Orientation {
        LEFT, TOP, RIGHT, BOTTOM;

        Orientation getOpposite() {
            switch (this) {
                case LEFT: return RIGHT;
                case RIGHT: return LEFT;
                case TOP: return BOTTOM;
                case BOTTOM: return TOP;
                default: return null;
            }
        }
    }

    static class Piece {
        private final static int NUMBER_OF_EDGES = 4;
        private HashMap<Orientation, Edge> edges = new HashMap<>();

        Piece (Edge[] edgeList) {
            Orientation[] orientations = Orientation.values();
            for (int index = 0; index < edgeList.length; index++) {
                Edge edge = edgeList[index];
                edge.setParentPiece(this);
                edges.put(orientations[index],edge);
            }
        }

        // set this edge in the appropriate orientation, rotating the piece as necessary
        void setEdgeAsOrientation(Edge edge, Orientation orientation) {
            Orientation currentOrientation = getOrientation(edge);
            rotateEdgesBy(orientation.ordinal() - currentOrientation.ordinal());
        }

        // return current orientation of the edge
        Orientation getOrientation(Edge edge) {
            for (Entry<Orientation, Edge> entry : edges.entrySet()) {
                if (entry.getValue() == edge) return entry.getKey();
            }
            return null;
        }

        void rotateEdgesBy(int numberRotations) {
            Orientation[] orientations = Orientation.values();
            HashMap<Orientation, Edge> rotated = new HashMap<>();

            numberRotations = numberRotations % NUMBER_OF_EDGES;
            if (numberRotations < 0) numberRotations += NUMBER_OF_EDGES;

            for (int index = 0; index < orientations.length; index++) {
                Orientation oldOrientation = orientations[(index - numberRotations + NUMBER_OF_EDGES) % NUMBER_OF_EDGES];
                Orientation newOrientation = orientations[index];
                rotated.put(newOrientation, edges.get(oldOrientation));
            }
            edges = rotated;
        }

        // check if this piece is corner piece
        boolean isCorner() {
            Orientation[] orientations = Orientation.values();
            for (int index = 0; index < orientations.length; index++) {
                Shape current = edges.get(orientations[index]).getShape();
                Shape next = edges.get(orientations[(index + 1) % NUMBER_OF_EDGES]).getShape();
                if (current == Shape.FLAT && next == Shape.FLAT) return true;
            }
            return false;
        }

        boolean isBorder() {
            Orientation[] orientations = Orientation.values();
            for (int index = 0; index < orientations.length; index++) {
                if (edges.get(orientations[index]).getShape() == Shape.FLAT) return true;
            }
            return false;
        }

        Edge getEdgeWithOrientation(Orientation orientation) {
            return edges.get(orientation);
        }

        Edge getMatchingEdge(Edge targetEdge) {
            for (Edge e : edges.values()) {
                if (targetEdge.fitsWith(e)) return e;
            }
            return null;
        }

        String toStr() {
            StringBuilder sb = new StringBuilder();
            Orientation[] orientations = Orientation.values();
            for (Orientation o : orientations) {
                sb.append(edges.get(o).toStr() + ",");
            }
            return "[" + sb.toString() + "]";
        }
    }

    static class Edge {
        private Shape shape;
        private String code;        // used to mock how pieces would fit together
        private Piece parentPiece;

        Edge(Shape shape, String code) {
            this.shape = shape;
            this.code = code;
        }

        String getCode() {
            return code;
        }

        Edge createMatchingEdge() {
            if (shape == Shape.FLAT) return null;
            return new Edge(shape.getOpposite(), getCode());
        }

        boolean fitsWith(Edge edge) {
            return edge.getCode().equals(getCode());
        }

        void setParentPiece(Piece parentPiece) {
            this.parentPiece = parentPiece;
        }

        Piece getParentPiece() {
            return parentPiece;
        }

        Shape getShape() {
            return shape;
        }

        String toStr() {
            return code;
        }
    }

    static class Puzzle {
        private LinkedList<Piece> pieces;
        private Piece[][] solution;
        private int size;

        Puzzle(int size, LinkedList<Piece> pieces) {
            this.pieces = pieces;
            this.size = size;
        }

        void groupPieces(LinkedList<Piece> cornerPieces,
                         LinkedList<Piece> boderPieces,
                         LinkedList<Piece> insidePieces) {
            for (Piece p : pieces) {
                if (p.isCorner()) cornerPieces.add(p);
                else if (p.isBorder()) boderPieces.add(p);
                else insidePieces.add(p);
            }
        }

        void orientTopLeftCorner(Piece piece) {
            if (!piece.isCorner()) return;;

            Orientation[] orientations = Orientation.values();

            for (int index = 0; index < orientations.length; index++) {
                Edge current = piece.getEdgeWithOrientation(orientations[index]);
                Edge next = piece.getEdgeWithOrientation(orientations[(index+1) % orientations.length]);
                if (current.getShape() == Shape.FLAT && next.getShape() == Shape.FLAT) {
                    piece.setEdgeAsOrientation(current, Orientation.LEFT);
                    return;
                }
            }
        }

        boolean isBorderIndex(int location) {
            return location == 0 || location == size-1;
        }

        // Given a list of pieces, check if any have an edge that matches this piece.
        // Return the edge.
        Edge getMatchingEdge(Edge targetEdge, LinkedList<Piece> pieces) {
            for (Piece piece : pieces) {
                Edge matchingEdge = piece.getMatchingEdge(targetEdge);
                if (matchingEdge != null) return matchingEdge;
            }
            return null;
        }

        // Put the edge / piece into the solution, turn it appropriately, and remove from list
        void setEdgeInSolution(LinkedList<Piece> pieces, Edge edge, int row, int col, Orientation orientation) {
            Piece piece = edge.getParentPiece();
            piece.setEdgeAsOrientation(edge, orientation);
            pieces.remove(piece);
            solution[row][col] = piece;
        }

        LinkedList<Piece> getPieceListToSearch(LinkedList<Piece> cornerPieces,
                                               LinkedList<Piece> borderPieces,
                                               LinkedList<Piece> insidePieces,
                                               int row,
                                               int col) {
             if (isBorderIndex(row) && isBorderIndex(col)) return cornerPieces;
             else if (isBorderIndex(row) || isBorderIndex(col)) return borderPieces;
             else return insidePieces;
        }

        boolean fitNextEdge(LinkedList<Piece> piecesToSearch, int row, int col) {
            if (row == 0 && col == 0) {
                Piece p = piecesToSearch.remove();
                orientTopLeftCorner(p);
                solution[0][0] = p;
            } else {
                // get the right edge and list to match
                Piece pieceToMatch = col == 0 ? solution[row - 1][0] : solution[row][col - 1];
                Orientation orientationToMatch = col == 0 ? Orientation.BOTTOM : Orientation.RIGHT;
                Edge edgeToMatch = pieceToMatch.getEdgeWithOrientation(orientationToMatch);

                //get matching edge
                Edge edge = getMatchingEdge(edgeToMatch, piecesToSearch);
                if (edge == null) return false;

                Orientation orientation = orientationToMatch.getOpposite();
                setEdgeInSolution(piecesToSearch, edge, row, col, orientation);
            }
            return true;
        }

        boolean solve() {
            LinkedList<Piece> cornerPieces = new LinkedList<>();
            LinkedList<Piece> borderPieces = new LinkedList<>();
            LinkedList<Piece> insidePieces = new LinkedList<>();

            groupPieces(cornerPieces, borderPieces, insidePieces);

            // walk through the puzzle, finding the piece that joins the previous one
            solution = new Piece[size][size];

            for (int row = 0; row < size; row++) {
                for(int col = 0; col < size; col++) {
                    LinkedList<Piece> piecesToSearch = getPieceListToSearch(cornerPieces, borderPieces, insidePieces, row, col);
                    if (!fitNextEdge(piecesToSearch, row, col)) return false;
                }
            }
            return true;
        }

        Piece[][] getCurrentSoln() {
            return solution;
        }


        
    }

    
}
