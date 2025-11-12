class Sample
{

  @override
  protected int adjustSeekIndex(int i) {
    return skipOffTokenChannels(i);
  }
  
}