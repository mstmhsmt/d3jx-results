class Bad {

    void foo() {
        
<<<<<<< commits-sample/bad/broken/A/broken.java
for (int i = 0; i < 100; i++) {
            bar(i);
        }for (int i = 1; i < 100; i++) {
            System.out.println(i);
        }
=======
for (int i = 1; i < 100; i++) {
            System.out.println(i);
        }return;
>>>>>>> commits-sample/bad/broken/B/broken.java

    }
}
