public class Tree {

  //Da wir Knoten statisch speichern wird diese Klasse nicht genutzt. Ist jedoch zentral,
  // wenn man die Struktur des Programms verstehen will, daher ist habe ich Sie in den Ordner hinzugefügt

  Knoten root; //parent ist bei diesem knoten null

  public Tree() {
    root = new Knoten(new int[][]{{0, 6, 3}, {1, 4, 7}, {2, 5, 8}}, 0);
  }

  public Knoten getRoot() {
    return root;
  }
}
