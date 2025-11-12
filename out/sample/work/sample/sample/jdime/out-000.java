
class Sample {
  @override protected int adjustSeekIndex(int i) {
    return skipOffTokenChannels(i);
  }


<<<<<<< Unknown file: This is a bug in JDime.
=======
  @override public void reset() {
    super.reset();
    p = nextTokenOnChannels(p, channel);
  }
>>>>>>> commits-sample/sample/sample/B/Sample.java
}