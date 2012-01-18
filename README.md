To run this program do this:

    mvn package
    java -cp target/timeSkew-1.0-jar-with-dependencies.jar com.mapr.time.AdjustmentDetector

Then in another window do things like this:

    sudo date -s now-10seconds

You should see output like this in the original window:

    delta = 0.000, mean = 0.000, quartiles=(0.000, 0.000, 0.000, 0.001, 0.060)
    Time slip: 10.003 1326927283304 - 1326927255975 vs 3145593300689 - 3128267583032
    delta = 0.060, mean = 0.001, quartiles=(0.000, 0.000, 0.001, 0.001, 10.003)
    delta = 0.000, mean = 0.001, quartiles=(0.000, 0.000, 0.000, 0.001, 10.003)
    Time slip: 10.000 1326927287768 - 1326927283305 vs 3160056772472 - 3145594090767
    delta = 0.060, mean = 0.001, quartiles=(0.000, 0.000, 0.000, 0.001, 10.003)
    delta = 0.000, mean = 0.001, quartiles=(0.000, 0.000, 0.000, 0.001, 10.003)
    delta = 0.000, mean = 0.001, quartiles=(0.000, 0.000, 0.000, 0.001, 10.003)

The time slip messages should appear immediately after every time change.  If you change
the code to use Thread.sleep(200) or equivalent then you will see a delayed notification
for any change that moves time backwards because of how Java times work.

