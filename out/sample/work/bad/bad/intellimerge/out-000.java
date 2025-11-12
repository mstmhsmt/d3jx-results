class Bad
{
   void foo() {
<<<<<<< ours
    for (int i = 0; i < 100; i++) {
      bar(i);
    }
=======
>>>>>>> theirs
    for (int i = 1; i < 100; i++) {
      System.out.println(i);
    }
    return;
  }
  
}