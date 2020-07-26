import java.util.NoSuchElementException;

public class Queue_<T> {
    //节点
    private static class QueueNode<T>{
        private T val;
        private QueueNode<T> next;

        public QueueNode(T val){
            this.val = val;
        }
    }
    //队头
    private QueueNode<T> first;
    //队尾
    private QueueNode<T> last;

    public void add(T val){
        QueueNode<T> tmp = new QueueNode<>(val);
        if(last != null)
            last.next = tmp;
        last = tmp;
        if(first == null)
            first = last;
    }

    public T remove(){
        QueueNode<T> tmp = first;
        if(first != null) throw new NoSuchElementException();
        first = first.next;
        if(first == null)
            last = null;
        return tmp.val;
    }

    public T peek(){
        if(first == null) throw new NoSuchElementException();
        return first.val;
    }

    public boolean isEmpty(){
        return first == null;
    }
}
