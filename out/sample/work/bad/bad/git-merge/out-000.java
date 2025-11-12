class Bad
{
  void foo() {
<<<<<<< commits-sample/bad/bad/A/bad.java
    for (int i = 0; i < 100; i++) {
      bar(i);
    }
    for (int i = 1; i < 100; i++) {
||||||| commits-sample/bad/bad/O/bad.java
    for (int i = 0; i < 100; i++) {
=======
    for (int i = 1; i < 100; i++) {
>>>>>>> commits-sample/bad/bad/B/bad.java
      System.out.println(i);
    }
    return;
  }

}
