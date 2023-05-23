import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FastCollinearPoints {
    public LineSegment[] segs = null;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Arrays.sort(points);
        List<LineSegment> linesegments = new LinkedList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] slopeordering = points.clone();
            Arrays.sort(slopeordering, p.slopeOrder());
            double lastSlope = Double.NEGATIVE_INFINITY;
            int slopeStartInd = 0;

            for (int j = 1; j < slopeordering.length; j++) {
                Point q = slopeordering[j];
                double currentSlope = p.slopeTo(q);
                boolean lastPoint = j == slopeordering.length - 1;
                if (Double.compare(currentSlope, lastSlope) != 0) {
                    if (j - slopeStartInd >= 3) {
                        if (p.compareTo(slopeordering[slopeStartInd]) <= 0) {
                            LineSegment segment = new LineSegment(p, slopeordering[j - 1]);
                            linesegments.add(segment);
                        }
                    }
                    slopeStartInd = j;
                }
                else if (lastPoint) {
                    if (j - slopeStartInd >= 2) {
                        if (p.compareTo(slopeordering[slopeStartInd]) <= 0) {
                            LineSegment segment = new LineSegment(p, q);
                            linesegments.add(segment);
                        }
                    }
                }
                lastSlope = currentSlope;
            }
        }
        segs = linesegments.toArray(new LineSegment[linesegments.size()]);
    }

    public int numberOfSegments() {
        return segs.length;
    }      // the number of line segments

    public LineSegment[] segments() {
        return segs;
    }            // the line segments

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
