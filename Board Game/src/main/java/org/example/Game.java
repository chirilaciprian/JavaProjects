package org.example;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Set;

public class Game {
    private final Set<Line2D> blackLines;
    private final Set<Line2D> redLines;
    private final Set<Line2D> blueLines;


    public Game(Set<Line2D> redLines, Set<Line2D> blueLines, Set<Line2D> blackLines) {
        this.blackLines = blackLines;
        this.redLines = redLines;
        this.blueLines = blueLines;
    }

    public int getWinner() {

        if (hasTriangle(redLines)) {
            return 1;
        } else if (hasTriangle(blueLines)) {
            return 2;
        } else if (redLines.size() + blueLines.size() == blackLines.size()) {
            return 0;
        } else {
            return -1;
        }
    }

    private boolean hasTriangle(Set<Line2D> lines) {
        for (Line2D line1 : lines) {
            for (Line2D line2 : lines) {
                if (line1 == line2) {
                    continue;
                }
                for (Line2D line3 : lines) {
                    if (line1 == line3 || line2 == line3) {
                        continue;
                    }
                    if (formsTriangle(line1, line2, line3)==true) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

        private boolean formsTriangle(Line2D line1, Line2D line2, Line2D line3) {
        Point2D a1 = line1.getP1();
        Point2D a2 = line1.getP2();
        Point2D b1 = line2.getP1();
        Point2D b2 = line2.getP2();
        Point2D p3;
        if (a2.equals(line2.getP1()) || a2.equals(line2.getP2())) {
            p3 = a1.equals(b1) || a1.equals(b2) ? a2 : a1;
        } else if (a1.equals(b1) || a1.equals(b2)) {
            p3 = a2.equals(b1) || a2.equals(b2) ? a1 : a2;
        } else {
            return false;
        }
        return line3.intersectsLine(new Line2D.Double(a1, p3)) && line3.intersectsLine(new Line2D.Double(a2, p3)) && line1.intersectsLine(line2) && line2.intersectsLine(line3) && line3.intersectsLine(line1);
    }

}