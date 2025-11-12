class Sample
{

  @override
<<<<<<< commits-sample/sample/sample/A/Sample.java
  protected int adjustSeekIndex(int i) {
    return skipOffTokenChannels(i);
||||||| commits-sample/sample/sample/O/Sample.java
  public void reset() {
    super.reset();
    p = skipOffTokenChannels(p);
=======
  public void reset() {
    super.reset();
    p = nextTokenOnChannels(p, channel);
>>>>>>> commits-sample/sample/sample/B/Sample.java
  }

}
