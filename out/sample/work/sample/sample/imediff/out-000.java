class Sample
{

  @override
  protected int adjustSeekIndex(int i) {
    return skipOffTokenChannels(i);
<<<<<<< commits-sample/sample/sample/A/Sample.java
||||||| commits-sample/sample/sample/O/Sample.java
    p = skipOffTokenChannels(p);
=======
    p = nextTokenOnChannels(p, channel);
>>>>>>> commits-sample/sample/sample/B/Sample.java
  }

}
