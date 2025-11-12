class Bad
{
  void foo() {
    for (int i = 0; i < 100; i++) {
      bar(i);
    }
    for (int i = 1; i < 100; i++) {
      System.out.println(i);
    }
    return;
  }

}
