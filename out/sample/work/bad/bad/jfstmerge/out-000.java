class Bad
{
  void foo() {
<<<<<<< MINE
    for (int i = 0; i < 100; i++) {
      bar(i);
    }
||||||| BASE
    for (int i = 0; i < 100; i++) {
      System.out.println(i);
    }
=======
>>>>>>> YOURS
    for (int i = 1; i < 100; i++) {
      System.out.println(i);
    }
    return;
  }

}