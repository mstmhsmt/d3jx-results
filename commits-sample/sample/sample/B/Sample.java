class Sample
{

  @override
  public void reset() {
    super.reset();
    p = nextTokenOnChannels(p, channel);
  }

}
