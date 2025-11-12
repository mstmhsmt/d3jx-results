class Sample
{

  @override
  protected int adjustSeekIndex(int i) {
<<<<<<< commits-sample/sample/sample/A/Sample.java
    return skipOffTokenChannels(i);
||||||| commits-sample/sample/sample/O/Sample.java
    super.reset();
    p = skipOffTokenChannels(p);
=======
    super.reset();
    p = nextTokenOnChannels(p, channel);
>>>>>>> commits-sample/sample/sample/B/Sample.java
  }

}
