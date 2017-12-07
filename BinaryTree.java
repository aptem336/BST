package lists;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {

    //Лист - корень точка отсчёта всего дерева
    private Node root = null;
    //Строка для вывода результатов обхода в глубину
    private String toPrint = "";
    private int size = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void printInWidth() {
        //Если корень не равен нул, 
        //что является признаком отсутствия дерева (далее не поясняю)
        if (root == null) {
            System.out.println("null");
            //В противном случае (корень не нул => дерево есть)
        } else {
            //начать прохожжение с корня
            BinaryTree.this.print(root);
        }
    }

//<editor-fold defaultstate="collapsed" desc="for print-in-width">
    //Обход в ширину
    private void print(Node node) {
        //Создаём очередь
        Queue<Node> queue = new LinkedList<>();
        //Добавляем в очередь лист, в данном случае корень
        queue.add(node);
        //Пока очередь не пуста
        while (!queue.isEmpty()) {
            //Строка с выводом текущего уровня. Обнуляем
            String printed = "";
            //Извлекаем из очереди верхний элемент при этом удалив его
            Node curr = queue.poll();
            //Если Узел имеет левого или правого потомка, то его стоит печатать
            if (curr.left != null || curr.right != null) {
                //В строку с выводом текущего уровня добавляем значение узла
                printed += curr.value + " ";
                //Если левый потомок есть (не равен нул) добавляем в строку вывода его значение
                if (curr.left != null) {
                    printed += curr.left.value + " ";
                    //Иначе - доавляем в строку вывода символ круга (ASCI-код)    
                } else {
                    printed += (char) 9675 + " ";
                }
                //Аналогично
                if (curr.right != null) {
                    printed += curr.right.value + " ";
                } else {
                    printed += (char) 9675 + " ";
                }
                //Если есть левый потомок, доавляем кго в очередь
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                //Аналогично
                if (curr.right != null) {
                    queue.add(curr.right);
                }
                //Выводом строку с текущим уровнем (узел+дети)
                System.out.println(printed);
            }
        }
    }
//</editor-fold>
    //Вывод вертикальных обходов

    public String print(int type) {
        //Обнуляем строку вывода
        toPrint = "";
        if (root != null) {
            //В зависимости от передаваемого значения выбираем тип обхода
            switch (type) {
                case 0:
                    recPreOrder(root);
                    break;
                case 1:
                    recInOrder(root);
                    break;
                case 2:
                    recPostOrder(root);
                    break;
            }
        }
        //Если строка осталась пустой приравниваем её значение к нул
        if ("".equals(toPrint)) {
            toPrint = "null";
        }
        //Возвращаем строку
        return toPrint;
    }

//<editor-fold defaultstate="collapsed" desc="for prints in deep">
    //Далее рекурсивно вызываемые функции!!!
    void recPreOrder(Node node) {
        if (node != null) {
            //Узел на печать
            toPrint += node.value + " ";
            //Печать левого поддерева
            recPreOrder(node.left);
            //Печать правого поддерева
            recPreOrder(node.right);
        }
    }

    void recInOrder(Node node) {
        if (node != null) {
            //Печать левого поддерева
            recInOrder(node.left);
            //Узел на печать
            toPrint += node.value + " ";
            //Печать правого поддерева
            recInOrder(node.right);
        }
    }

    void recPostOrder(Node node) {
        if (node != null) {
            //Печать левого поддерева
            recPostOrder(node.left);
            //Печать правого поддерева
            recPostOrder(node.right);
            //Узел на печать
            toPrint += node.value + " ";
        }
    }
//</editor-fold>

    //Процедура очистки
    public void clear() {
        //Обнулил корень = обнулить дерево
        root = null;
        //Размер = 0
        size = 0;
    }

    //Процедура добавления листа в дерево
    public void add(int key, Object element) {
        //Вызываем рекурсивную функцию добавления
        root = add(key, element, root);
        //Увеличиваем размер
        size++;
    }

//<editor-fold defaultstate="collapsed" desc="for add">
    //Рекурсивная функция (смысл: идём пока не встретим пустое место - нул)
    private Node add(int key, Object element, Node node) {
        //Если лист равен нул значит рекурсия закончена, мы нашли место для добавления
        if (node == null) {
            //Создаём осит с переданными параментрами
            node = new Node(key, element);
            //Если ключ меньше ключа листа - идём влево
        } else if (key <= node.key) {
            node.left = add(key, element, node.left);
            //Если ключ больше ключа листа - идём вправо
        } else if (key > node.key) {
            node.right = add(key, element, node.right);
        }
        //Возвращаем лист
        return node;
    }
//</editor-fold>

    //Вызов рекурсивной функции получения
    public Object get(int key) {
        return get(root, key).value;
    }

//<editor-fold defaultstate="collapsed" desc="for get">
    //Принцип такой же, как и в предыдущем примере
    //Только здесь идём пока не найдём равенство или нул
    private Node get(Node node, int key) {
        //Если упёрлись в нул, поиск не удался
        if (node == null) {
            //Возвращем нул
            return null;
        }
        //Обратите внимания на знаки сравнения, здесь нет <= или >= 
        //Только строгое сравнения
        //Равенство = найден лист с заданным ключом 
        if (node.key > key) {
            return get(node.left, key);
        } else if (node.key < key) {
            return get(node.right, key);
        } else {
            //Не меньше, не больше => равно
            //Лист найден, посик завершён
            return node;
        }
    }
//</editor-fold>

    //Функция установки
    public void set(int key, Object element) {
        //Найдём лист с заданным ключом
        //И изменим его значения на заданное
        get(root, key).value = element;
    }

    //Функция проверки наличия
    public boolean contains(int key) {
        //Если поиск удался, лист с заданным ключом содержится
        return get(key) != null;
    }

    //Снова же вызов рекурсивной процедуры
    public void remove(int key) {
        //Вызов
        remove(root, key);
        //Уменьшим размер
        size--;
    }

//<editor-fold defaultstate="collapsed" desc="for remove">
    //Принцип похож на прицнип get
    private Node remove(Node node, int key) {
        //Упёрлись в нуд - поиск не удался
        if (node == null) {
            return null;
        }
        //Знаки строгие - ждём равенства
        if (node.key > key) {
            node.left = remove(node.left, key);
        } else if (node.key < key) {
            node.right = remove(node.right, key);
            //Равенство найдено -|-|-    
        } else {
            //Если лист имеет двух потомков (самый сложный случай)
            if (node.left != null && node.right != null) {
                //Сохраним значения удаляемого
                Node memory = node;
                //Ищем минимальный, он же самый левый, для правого
                Node minForRight = minElem(memory.right);
                //Заменим значение и ключ удаляемого на значение и ключ самого левого для правого
                node.key = minForRight.key;
                node.value = minForRight.value;
                //Удалим моинимальный...
                //Проще говоря - переставили
                remove(node.right, minForRight.key);
                //Если есть только левый
            } else if (node.left != null) {
                //Вместо удаляемого вставим его левого потомка
                node = node.left;
                //Если есть только правый...
            } else if (node.right != null) {
                node = node.right;
                //Нет ни одного потомка - просто обнуляем
            } else {
                node = null;
            }
        }
        return node;
    }
//</editor-fold>

    //Нахождение минимального элемента
    private Node minElem(Node node) {
        //Если нет левого - мы нашли самый левый - самый маленький
        if (node.left == null) {
            return node;
            //Иначе
        } else {
            //Вызываем для левого потомка
            return minElem(node.left);
        }
    }

    //Класс элемента дерева
    private static class Node {

        //Ключ
        int key;
        //Значение
        Object value;
        //Левый потомок
        Node left;
        //Правый потомок
        Node right;
        //Конструктор узла, просто получение значений
        Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

    }
}
