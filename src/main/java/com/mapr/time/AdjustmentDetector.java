package com.mapr.time;

import org.apache.mahout.math.stats.OnlineSummarizer;

import java.util.concurrent.locks.LockSupport;

/**
 * Use nanotime and currentTimeMillis to detect clock changes.
 */
public class AdjustmentDetector {
  private static final long MILLISECONDS = 1000000;

  public static void main(String[] args) throws InterruptedException {
    Integer lock = 0;

    long mt0 = System.currentTimeMillis();
    long nt0 = System.nanoTime();
    long lastSummary = System.nanoTime();
    
    OnlineSummarizer summary = new OnlineSummarizer();
    
    while (true) {
      long mt = System.currentTimeMillis();
      long nt = System.nanoTime();

      double discrepancy = Math.abs((mt - mt0) / 1e3 - (nt - nt0) / 1e9);
      summary.add(discrepancy);
      if (discrepancy > 0.1) {
        System.out.printf("Time slip: %.3f %d - %d vs %d - %d\n", discrepancy, mt, mt0, nt, nt0);
        mt0 = System.currentTimeMillis();
        nt0 = System.nanoTime();
      }

      if ((nt - lastSummary) / 1e9 > 10) {
        System.out.printf("delta = %.3f, mean = %.3f, quartiles=(%.3f, %.3f, %.3f, %.3f, %.3f)\n",
          discrepancy, summary.getMean(),
          summary.getMin(), summary.getQuartile(1), summary.getQuartile(2), summary.getQuartile(3), summary.getMax());
        lastSummary = nt;
      }
      Thread.yield();
    }
  }
}
