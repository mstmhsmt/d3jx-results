class Sample
{

  @override
  protected int adjustSeekIndex(int i) {
    return skipOffTokenChannels(i);
  }<<<<<<< MINE
||||||| BASE
@override
  public void reset() {
    super.reset();
    p = skipOffTokenChannels(p);
  }
=======
@override
  public void reset() {
    super.reset();
    p = nextTokenOnChannels(p, channel);
  }
>>>>>>> YOURS


}